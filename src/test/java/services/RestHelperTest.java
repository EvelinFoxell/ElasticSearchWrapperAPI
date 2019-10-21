package services;

import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

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

    @Test
    public void cleanResultArrayToReturnRelevantFields() {
        JsonObject testObject = helper.transformToJSON("{\"_index\":\"documents\",\"_type\":\"document\",\"_id\":\"AWBHYz_j2C_5w8EI6V3E\",\"_score\":1.0,\"_source\":{\"keyPhrases\":[\"Automaat Nordic\",\"Volvo\"],\"body\":\"This is a test \\nHenlo!\",\"title\":\"Volvo V60 T3 152pk Automaat Nordic+\",\"sentiment\":\"p\"}}");
        JsonArray testArray = Json.createArrayBuilder().add(testObject).add(testObject).build();
        JsonArray relevantResultFields = helper.cleanResultArrayToReturnRelevantFields(testArray);
        assertNotNull(relevantResultFields);
        assertEquals("The number of elements in array must remain the same!", testArray.size(), relevantResultFields.size());
        assertEquals("Relevant field body must exist!", "This is a test \nHenlo!", relevantResultFields.get(0).asJsonObject().getString("body"));
    }
}
