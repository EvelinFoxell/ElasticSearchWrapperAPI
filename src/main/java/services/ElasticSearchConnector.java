package services;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.ResourceBundle;

public class ElasticSearchConnector {

    public static final String ELASTICSEARCH_URL = ResourceBundle.getBundle("Application").getString("elasticsearch.url");

    public Client getElasticSearchClient() {
        try {
            Client client = new PreBuiltTransportClient(Settings.builder()
                    .put("client.transport.sniff", true).put("cluster.name", "documents")
                    .build())
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
            return client;
        } catch(Exception connectionException) {
            // FIXME Implement proper logging
            System.out.println("Could not create a client to connect to ElasticSearch: " + connectionException.getMessage());
            return null;
        }
    }
}
