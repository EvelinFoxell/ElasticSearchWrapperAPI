package services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class ElasticSearchConnector {

    private static final String ELASTICSEARCH_URL = ResourceBundle.getBundle("Application").getString("elasticsearch.restUrl");
    private Logger logger = LoggerFactory.getLogger(ElasticSearchConnector.class);

    public HttpURLConnection getElasticSearchRESTConnection() {
        try {
            URL url = new URL("http://" + ELASTICSEARCH_URL + "/documents/_search");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            return conn;
        }catch (IOException exception) {
            logger.error("Could not connect to REST API: " + exception.getMessage());
            return null;
        }
    }
}
