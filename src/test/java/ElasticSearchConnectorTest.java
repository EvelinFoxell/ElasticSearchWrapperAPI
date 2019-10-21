import org.junit.After;
import org.junit.Test;
import services.ElasticSearchConnector;
import services.RestHelper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ElasticSearchConnectorTest {

    private ElasticSearchConnector connector = new ElasticSearchConnector();
    private HttpURLConnection connection;
    private RestHelper restHelper = new RestHelper();

    @After
    public void tearDown() {
        if(connection != null) {
            connection.disconnect();
        }
    }

    @Test
    public void getElasticSearchRESTClient() throws ProtocolException, IOException {
        HttpURLConnection conn = connector.getElasticSearchRESTConnection();

        String result = restHelper.readResponseResultBody(conn);

        assertEquals(200, conn.getResponseCode());
        assertTrue(result.length() > 0);

        conn.disconnect();
    }
}
