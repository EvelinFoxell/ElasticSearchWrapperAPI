package services;

import model.SearchQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class SearchService {

    private Logger logger = LoggerFactory.getLogger(SearchService.class);
    private ElasticSearchConnector connector = new ElasticSearchConnector();
    private RestHelper restHelper = new RestHelper();

    public JsonArray getSearchResults(SearchQuery searchQuery) {
        String queryResult;
        JsonObject jsonResponse = null;
        try {
            byte[] queryAsBytes = makeKeyPhrasesQueryBody(searchQuery);
            HttpURLConnection connection = connector.getElasticSearchRESTConnection();
            writeToHttpConnection(connection, queryAsBytes);
            queryResult = restHelper.readResponseResultBody(connection);
            jsonResponse = restHelper.transformToJSON(queryResult);
        } catch (IOException e) {
            logger.error("Could not write to connection: " + e.getMessage());
            return null;
        }
        JsonArray resultArray = jsonResponse.getJsonObject("hits").getJsonArray("hits");
        return resultArray;
    }


    public void writeToHttpConnection(HttpURLConnection connection, byte[] query) throws IOException {
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(query);
        outputStream.flush();
        outputStream.close();
    }

    public byte[] makeKeyPhrasesQueryBody(SearchQuery searchQuery) {
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"must\": {\n" +
                "        \"multi_match\" : {\n" +
                "          \"query\": \""+ searchQuery.getUserSearchInput() +"\",\n" +
                "          \"fields\": [ \"title\", \"body\", \"keyPhrases\" ]\n" +
                "        }\n" +
                "      },\n" +
                "      \"filter\": {\n" +
                "        \"term\": {\n" +
                "          \"sentiment\": \"" + searchQuery.getSortFlag() + "\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        byte[] bytes = new byte[0];
        try {
            bytes = query.getBytes(StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            logger.error("Could not convert query string to bytes: " + e.getMessage());
        }
        return bytes;
    }
}
