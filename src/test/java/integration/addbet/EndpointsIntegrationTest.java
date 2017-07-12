package integration.addbet;

import com.mongodb.client.MongoCollection;
import hu.bets.config.MessagingConfig;
import hu.bets.config.WebConfig;
import hu.bets.steps.Given;
import hu.bets.steps.Then;
import hu.bets.steps.When;
import hu.bets.util.Json;
import hu.bets.web.model.SaveBetResponse;
import integration.Constants;
import integration.FakeApplicationConfig;
import integration.FakeDbConfig;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.bson.Document;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static integration.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EndpointsIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EndpointsIntegrationTest.class);

    @BeforeClass
    public static void before() throws Exception {
        Given.environmentIsUpAndRunning(FakeApplicationConfig.class,
                WebConfig.class,
                FakeDbConfig.class,
                MessagingConfig.class);
        TimeUnit.SECONDS.sleep(2);
    }

    @AfterClass
    public static void after() throws Exception {
        Given.environmentIsShutDown();
    }

    @Test
    public void callingTheAddBetWebMethodShouldSaveABetInTheDatabase() throws Exception {
        MongoCollection<Document> collection = Given.aDataSource();

        HttpResponse response = When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.POST_JSON);
        String betResponse = convertResponse(response);

        Then.theBetResponseIsOk(betResponse);
        Then.theDatasourceContainsBet(asBetResponse(betResponse).getId(), collection);
    }

    @Test
    public void callingTheAddBetWebMethodShouldFailWhenSchemaIsInvalid() throws Exception {
        MongoCollection<Document> collection = Given.aDataSource();

        HttpResponse response = When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.INVALID_POST_JSON);

        assertTrue(EntityUtils.toString(response.getEntity()).contains("required key [token] not found"));
    }

    private SaveBetResponse asBetResponse(String betResponse) {
        return new Json().fromJson(betResponse, SaveBetResponse.class);
    }

    @Test
    public void callingTheInfoEndpointReturnsUpAndRunningResponse() throws Exception {
        String expectedResult = "<html><body><h1>Football-bets service up and running</h1></body></html>";
        HttpResponse httpResponse = When.iMakeAGetRequest(PROTOCOL + "://" + HOST + ":" + PORT + INFO_ENDPOINT);

        assertEquals(expectedResult, EntityUtils.toString(httpResponse.getEntity()));
    }

    private String convertResponse(HttpResponse response) throws Exception {
        String entity = EntityUtils.toString(response.getEntity());
        LOGGER.info("Entity = " + entity);
        return entity;
    }

}
