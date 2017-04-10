package integration.addbet;

import com.google.gson.Gson;
import hu.bets.dbaccess.DataSourceHolder;
import hu.bets.web.model.BetResponse;
import integration.Constants;
import integration.steps.Given;
import integration.steps.Then;
import integration.steps.When;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static integration.Constants.*;
import static org.junit.Assert.assertEquals;

public class EndpointsIntegrationTest {

    @BeforeClass
    public static void before() throws Exception {
        Given.environmentIsUpAndRunning();
    }

    @AfterClass
    public static void after() throws Exception {
        Given.environmentIsShutDown();
    }

    @Test
    public void callingTheAddBetWebMethodShouldSaveABetInTheDatabase() throws Exception {
        DataSourceHolder dataSourceHolder = Given.aDataSource();

        HttpResponse response = When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.POST_JSON);
        BetResponse betResponse = convertResponse(response);

        Then.theBetResponseIsOk(betResponse);
        Then.theDatasourceContainsBet(betResponse.getId(), dataSourceHolder);
    }

    @Test
    public void callingTheInfoEndpointReturnsUpAndRunningResponse() throws Exception {
        String expectedResult = "<html><body><h1>Football-bets service up and running</h1></body></html>";
        HttpResponse httpResponse = When.iMakeAGetRequest(PROTOCOL + "://" + HOST + ":" + PORT + "/");

        assertEquals(expectedResult, EntityUtils.toString(httpResponse.getEntity()));
    }

    private BetResponse convertResponse(HttpResponse response) throws Exception {
        return new Gson().fromJson(EntityUtils.toString(response.getEntity()), BetResponse.class);
    }

}
