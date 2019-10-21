import model.SearchQuery;
import services.RestHelper;
import services.SearchService;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;

@Path("/api/v1")
public class SearchAPI {

    private RestHelper restHelper = new RestHelper();
    private SearchService searchService = new SearchService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response applicationBase() {
        JsonObject welcomeMessage = restHelper.buildAPIInstructions();
        return Response.ok(welcomeMessage, MediaType.APPLICATION_JSON).encoding(StandardCharsets.UTF_8.name()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/searchMatchWithSentiment")
    public Response searchMatchWithSentiment(@QueryParam("query") String query, @QueryParam("sentiment") String sentiment) {
        // TODO: Validate the input
        SearchQuery searchQuery = new SearchQuery(query, sentiment);
        JsonArray results = searchService.getSearchResults(searchQuery);
        if(results == null) {
            return Response.status(400).encoding(MediaType.APPLICATION_JSON).build();
        }
        return Response.ok(results).encoding(MediaType.APPLICATION_JSON).build();
    }
}
