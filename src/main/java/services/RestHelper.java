package services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.*;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class RestHelper {

    private final Logger logger = LoggerFactory.getLogger(RestHelper.class);

    public String readResponseResultBody(HttpURLConnection connection) {
        String response = "";
        try {
            InputStreamReader responseStream = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(responseStream);
            String output;
            StringBuilder builder = new StringBuilder();
            while ((output = br.readLine()) != null) {
                builder.append(output);
            }
            response = builder.toString();
        } catch (IOException e) {
            logger.error("Could not read input: " + e.getMessage());
        }
        return response;
    }

    public JsonObject buildAPIInstructions(){
        JsonObject instructions = Json.createObjectBuilder()
                .add("welcomeMessage", "Welcome tho the Api!")
                .add("apiVersion", "1.0")
                .add("basePath","/api/v1/")
                .add("resourcePath","/")
                .add("produces", Json.createArrayBuilder().add(MediaType.APPLICATION_JSON).build())
                .add("apis", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                            .add("path", "/searchMatchWithSentiment")
                            .add("operations",Json.createArrayBuilder()
                                    .add(Json.createObjectBuilder()
                                            .add("method", "GET")
                                            .add("summary","Search for documents matching your keywords")
                                            .add("notes","Accepts text as keywords for a search and flags for sorting on sentiment")
                                            .add("encoding", StandardCharsets.UTF_8.name())
                                            .add("parameters", Json.createArrayBuilder()
                                                    .add(Json.createObjectBuilder()
                                                            .add("name", "query")
                                                            .add("description","The search words used to find matching documents")
                                                            .add("required","true")
                                                            .add("paramType","query")
                                                            .build())
                                                    .add(Json.createObjectBuilder()
                                                            .add("name", "sentiment")
                                                            .add("description","Sort results based on sentiments n, p or v.")
                                                            .add("required","false")
                                                            .add("paramType","query")
                                                            .build())
                                                    .build())
                                            .add("responseMessages", Json.createArrayBuilder()
                                                    .add(Json.createObjectBuilder()
                                                            .add("code", 200)
                                                            .add("message", "OK")
                                                            .build())
                                                    .add(Json.createObjectBuilder()
                                                            .add("code", 400)
                                                            .add("message", "Bad request")
                                                            .build())
                                                    .build())
                                            .build())
                                    .build())
                            .build())
                        .build())
                .add("model", Json.createObjectBuilder()
                        .add("searchResult", Json.createObjectBuilder()
                                .add("score", "BigDecimal")
                                .add("id", "String")
                                .add("body","String")
                                .add("title","String")
                                .add("sentiment","String")
                                .add("keyPhrases",Json.createArrayBuilder()
                                        .add("String")
                                        .build())
                                .build())
                        .build())
                .build();
        return instructions;
    }

    public JsonObject transformToJSON(String jsonAsString) {
        JsonReader reader = Json.createReader(new StringReader(jsonAsString));
        JsonObject json = reader.readObject();
        return json;
    }

    public JsonArray cleanResultArrayToReturnRelevantFields(JsonArray rawResultArray) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonArray relevantInfoArray;
        JsonObject rawSearchResult;
        for (JsonValue jsonValue : rawResultArray) {
            rawSearchResult = jsonValue.asJsonObject();
            JsonObject relevantInfoModel = Json.createObjectBuilder()
                    .add("score", rawSearchResult.getJsonNumber("_score").bigDecimalValue())
                    .add("id", rawSearchResult.getString("_id"))
                    .add("body", rawSearchResult.getJsonObject("_source").getString("body"))
                    .add("title", rawSearchResult.getJsonObject("_source").getString("title"))
                    .add("sentiment", rawSearchResult.getJsonObject("_source").getString("sentiment"))
                    .add("keyPhrases", rawSearchResult.getJsonObject("_source").getJsonArray("keyPhrases"))
                    .build();
            builder.add(relevantInfoModel);
        }
        relevantInfoArray = builder.build();
        return relevantInfoArray;
    }
}
