package integration.requestbet;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import hu.bets.config.MessagingConfig;
import hu.bets.config.WebConfig;
import hu.bets.model.data.Bet;
import hu.bets.model.data.UserBet;
import hu.bets.model.filter.Field;
import hu.bets.model.filter.MultiEqualsFilter;
import hu.bets.model.filter.RangeFilter;
import hu.bets.steps.Given;
import hu.bets.steps.When;
import hu.bets.steps.util.ApplicationContextHolder;
import hu.bets.util.Json;
import hu.bets.web.model.BetForFilterRequest;
import hu.bets.web.model.BetForIdRequest;
import hu.bets.web.model.BetForIdResponse;
import integration.Constants;
import integration.FakeApplicationConfig;
import integration.FakeDbConfig;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.bson.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
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

    @After
    public void clearDatabase() {
        MongoCollection mongoCollection = ApplicationContextHolder.getBean(MongoCollection.class);
        mongoCollection.drop();
    }

    @Test
    public void shouldUpdateABet() throws Exception {
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user1"), Constants.getBet("match1"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user1"), Constants.getBet("match1", "different-bet-id", 2, 6));

        MongoCollection coll = ApplicationContextHolder.getBean(MongoCollection.class);
        Document res = (Document) coll.find(Filters.and(Filters.eq("betId", "aa"),
                Filters.eq("userId", "user1"),
                Filters.eq("matchId", "match1")
        )).first();

        UserBet userBet = new Json().fromJson(res.toJson(), UserBet.class);
        assertEquals(2, userBet.getBet().getHomeTeamGoals());
        assertEquals(6, userBet.getBet().getAwayTeamGoals());
        assertEquals("aa", userBet.getBet().getBetId());
    }

    @Test
    public void shouldReturnAllTheMatchesForARequestUsingOneFilter() throws Exception {
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user1"), Constants.getBet("match1"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user1"), Constants.getBet("match2"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user1"), Constants.getBet("match3"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user1"), Constants.getBet("match4"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user1"), Constants.getBet("match5"));

        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user2"), Constants.getBet("match1"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user2"), Constants.getBet("match2"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user2"), Constants.getBet("match3"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user2"), Constants.getBet("match4"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user2"), Constants.getBet("match5"));

        HttpResponse httpResponse = When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(FILTER_QUERY_ENDPOINT, "user1"), new Json().toJson(new BetForFilterRequest(Collections.emptyList(), "token")));
        BetForIdResponse betForIdResponse = new Json().fromJson(EntityUtils.toString(httpResponse.getEntity()), BetForIdResponse.class);

        assertEquals("", betForIdResponse.getError());
        List<Bet> payload = betForIdResponse.getPayload();
        assertEquals(5, payload.size());
        assertEquals(new Bet("match1", "aa", (byte) 1, (byte) 0), payload.get(0));
        assertEquals(new Bet("match2", "aa", (byte) 1, (byte) 0), payload.get(1));
        assertEquals(new Bet("match3", "aa", (byte) 1, (byte) 0), payload.get(2));
        assertEquals(new Bet("match4", "aa", (byte) 1, (byte) 0), payload.get(3));
        assertEquals(new Bet("match5", "aa", (byte) 1, (byte) 0), payload.get(4));

    }

    @Test
    public void shouldReturnAllTheMatchesForARequestByFilterQuery() throws Exception {
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user1"), Constants.getBet("match1"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user1"), Constants.getBet("match2"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user1"), Constants.getBet("match3"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user1"), Constants.getBet("match4"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user1"), Constants.getBet("match5"));

        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user2"), Constants.getBet("match1"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user2"), Constants.getBet("match2"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user2"), Constants.getBet("match3"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user2"), Constants.getBet("match4"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user2"), Constants.getBet("match5"));

        HttpResponse httpResponse = When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(FILTER_QUERY_ENDPOINT, "user1"), new Json().toJson(
                new BetForFilterRequest(Collections.singletonList(
                        new MultiEqualsFilter(Field.MATCH_ID, Arrays.asList("match2", "match3", "match4", "match9"))
                ), "token")));
        BetForIdResponse betForIdResponse = new Json().fromJson(EntityUtils.toString(httpResponse.getEntity()), BetForIdResponse.class);

        assertEquals("", betForIdResponse.getError());
        List<Bet> payload = betForIdResponse.getPayload();
        assertEquals(3, payload.size());
        assertEquals(new Bet("match2", "aa", (byte) 1, (byte) 0), payload.get(0));
        assertEquals(new Bet("match3", "aa", (byte) 1, (byte) 0), payload.get(1));
        assertEquals(new Bet("match4", "aa", (byte) 1, (byte) 0), payload.get(2));
    }

    @Test
    public void shouldReturnMatchesByDateRangeFilter() throws Exception {
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user1"), Constants.getBet( "match1", "2018/01/25 15:00:00"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user1"), Constants.getBet( "match2", "2018/01/26 15:00:00"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user1"), Constants.getBet( "match3", "2018/01/27 15:00:00"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user1"), Constants.getBet("match4", "2018/01/28 15:00:00"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user1"), Constants.getBet("match5", "2018/01/29 15:00:00"));

        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user2"), Constants.getBet("match1", "2018/01/30 15:00:00"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user2"), Constants.getBet("match2", "2018/01/31 15:00:00"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user2"), Constants.getBet("match3", "2018/02/01 15:00:00"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user2"), Constants.getBet("match4", "2018/02/02 15:00:00"));
        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(ADD_BET_ENDPOINT, "user2"), Constants.getBet("match5", "2018/02/03 15:00:00"));

        HttpResponse httpResponse = When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + String.format(FILTER_QUERY_ENDPOINT, "user1"), new Json().toJson(new BetForFilterRequest(Collections.singletonList(new RangeFilter(Field.MATCH_DATE, "2018/01/27", "2018/02/01")), "token")));

        BetForIdResponse betForIdResponse = new Json().fromJson(EntityUtils.toString(httpResponse.getEntity()), BetForIdResponse.class);

        assertEquals("", betForIdResponse.getError());
        List<Bet> payload = betForIdResponse.getPayload();
        assertEquals(3, payload.size());
        assertEquals(new Bet("match3", "aa", (byte) 1, (byte) 0), payload.get(0));
        assertEquals(new Bet("match4", "aa", (byte) 1, (byte) 0), payload.get(1));
        assertEquals(new Bet("match5", "aa", (byte) 1, (byte) 0), payload.get(2));
    }
}
