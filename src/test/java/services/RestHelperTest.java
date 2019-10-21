package services;

import org.junit.Test;

import javax.json.JsonObject;

import static org.junit.Assert.*;

public class RestHelperTest {

    RestHelper helper = new RestHelper();
    @Test
    public void transformToJSON() {
        String JsonTestString = "{ \"message\": \"Test\"}";
        JsonObject result = helper.transformToJSON(JsonTestString);
        assertEquals("Json object does not match expected result.", "Test", result.getString("message"));
    }

    @Test
    public void buildAPIInstructions() {
        JsonObject message = helper.buildAPIInstructions();
        assertNotNull(message);
        assertEquals("Welcome message must exsit!", "Welcome tho the Api!", message.getString("welcomeMessage"));
    }
}
