package integration.addbet;

import com.google.gson.Gson;
import hu.bets.dbaccess.DataSourceHolder;
import hu.bets.web.model.BetResponse;
import integration.Constants;
import integration.steps.Given;
import integration.steps.Then;
import integration.steps.When;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import static integration.Constants.*;

public class EndpointsIntegrationTest {

    @Test
    public void callingTheAddBetWebMethodShouldSaveABetInTheDatabase() throws Exception {
        Given.theContextIsInitialized();
        Given.theWebServerIsUp();
        DataSourceHolder dataSourceHolder = Given.aBean(DataSourceHolder.class);

        CloseableHttpResponse response = When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ENDPOINT, Constants.POST_JSON);
        BetResponse betResponse = convertResponse(response);

        Then.theBetResponseIsOk(betResponse);
        Then.theDatasourceContainsBet(betResponse.getId(), dataSourceHolder);
    }

    private BetResponse convertResponse(HttpResponse response) throws Exception {
        return new Gson().fromJson(EntityUtils.toString(response.getEntity()), BetResponse.class);
    }

}
