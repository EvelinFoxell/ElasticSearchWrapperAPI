import org.elasticsearch.client.Client;
import org.junit.Test;
import services.ElasticSearchConnector;

import static org.junit.Assert.*;

public class ElasticSearchConnectorTest {

    ElasticSearchConnector connector = new ElasticSearchConnector();

    @Test
    public void getElasticSearchClient() {
        Client client = connector.getElasticSearchClient();

        assertNotNull(client);
    }
}
