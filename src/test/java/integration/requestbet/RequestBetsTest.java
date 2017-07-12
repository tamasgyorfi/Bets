package integration.requestbet;

import com.google.gson.Gson;
import hu.bets.config.MessagingConfig;
import hu.bets.config.WebConfig;
import hu.bets.model.data.Result;
import hu.bets.steps.Given;
import hu.bets.steps.When;
import hu.bets.web.model.BetForIdRequest;
import hu.bets.web.model.BetForIdResponse;
import integration.Constants;
import integration.FakeApplicationConfig;
import integration.FakeDbConfig;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static integration.Constants.*;
import static org.junit.Assert.assertEquals;

public class RequestBetsTest {

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
    public void shouldReturnAllTheMatchesForARequest() throws Exception {
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.getBet("user1", "match1"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.getBet("user1", "match2"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.getBet("user1", "match3"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.getBet("user1", "match4"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.getBet("user1", "match5"));

        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.getBet("user2", "match1"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.getBet("user2", "match2"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.getBet("user2", "match3"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.getBet("user2", "match4"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.getBet("user2", "match5"));

        HttpResponse httpResponse = When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + QUERY_BETS_ENDPOINT, new Gson().toJson(new BetForIdRequest("user2", Arrays.asList("match2", "match3", "match4", "match9"), "token")));
        BetForIdResponse betForIdResponse = new Gson().fromJson(EntityUtils.toString(httpResponse.getEntity()), BetForIdResponse.class);

        assertEquals("", betForIdResponse.getError());
        List<Result> payload = betForIdResponse.getPayload();
        assertEquals(3, payload.size());
        assertEquals(new Result("match2", (byte) 1, (byte) 0), payload.get(0));
        assertEquals(new Result("match3", (byte) 1, (byte) 0), payload.get(1));
        assertEquals(new Result("match4", (byte) 1, (byte) 0), payload.get(2));
    }
}
