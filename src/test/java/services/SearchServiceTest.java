package services;

import model.SearchQuery;
import org.junit.Before;
import org.junit.Test;

import javax.json.JsonArray;

import static org.junit.Assert.assertNotNull;

public class SearchServiceTest {

    private SearchService service = new SearchService();
    private SearchQuery query = new SearchQuery();

    @Before
    public void setUp() throws Exception {
        query.setSortFlag("n");
        query.setUserSearchInput("volvo");
    }

    @Test
    public void getSearchResults() {
        JsonArray resultList = service.getSearchResults(query);
        assertNotNull(resultList);
    }
}
