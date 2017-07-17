package integration.requestbet;

import hu.bets.config.MessagingConfig;
import hu.bets.config.WebConfig;
import hu.bets.model.data.Bet;
import hu.bets.steps.Given;
import hu.bets.steps.When;
import hu.bets.util.Json;
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

        HttpResponse httpResponse = When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + QUERY_BETS_ENDPOINT, new Json().toJson(new BetForIdRequest("user2", Arrays.asList("match2", "match3", "match4", "match9"), "token")));
        BetForIdResponse betForIdResponse = new Json().fromJson(EntityUtils.toString(httpResponse.getEntity()), BetForIdResponse.class);

        assertEquals("", betForIdResponse.getError());
        List<Bet> payload = betForIdResponse.getPayload();
        assertEquals(3, payload.size());
        assertEquals(new Bet("match2", "aa", (byte) 1, (byte) 0), payload.get(0));
        assertEquals(new Bet("match3", "aa", (byte) 1, (byte) 0), payload.get(1));
        assertEquals(new Bet("match4", "aa", (byte) 1, (byte) 0), payload.get(2));
    }
}
