package integration.requestreply;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import hu.bets.config.MessagingConfig;
import hu.bets.config.WebConfig;
import hu.bets.model.data.BetAggregationResponse;
import hu.bets.model.data.UserBet;
import hu.bets.steps.Given;
import hu.bets.steps.Then;
import hu.bets.steps.When;
import hu.bets.util.Json;
import integration.Constants;
import integration.FakeApplicationConfig;
import integration.FakeDbConfig;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static hu.bets.messaging.MessagingConstants.*;
import static integration.Constants.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class MessagingIntegrationTest {

    @Before
    public void setup() throws Exception {
        Given.environmentIsUpAndRunning(FakeApplicationConfig.class,
                WebConfig.class,
                FakeDbConfig.class,
                MessagingConfig.class);
        TimeUnit.SECONDS.sleep(2);

        Given.aDataSource().drop();
    }

    @After
    public void tearDown() throws Exception {
        Given.environmentIsShutDown();
    }

    @Test
    public void aggregationMessageShouldTriggerAggregationReply() throws Exception {

        When.iRepeateAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.POST_JSON, 101);
        When.iSendAMessage("{\"payload\":[\"aa\"],\"type\":\"BETS_REQUEST\"}", null, SCORES_TO_BETS_QUEUE, SCORES_TO_BETS_ROUTE);

        List<byte[]> messages = Then.iExpectOutgoingMessages(BETS_TO_SCORES_QUEUE, BETS_TO_SCORES_ROUTE, 2);

        assertEquals(2, messages.size());
        BetAggregationResponse message1 = new Json().fromJson(new String(messages.get(0)), BetAggregationResponse.class);
        BetAggregationResponse message2 = new Json().fromJson(new String(messages.get(1)), BetAggregationResponse.class);

        assertEquals(100, message1.getNumberOfElements());
        assertEquals(1, message2.getNumberOfElements());
    }

    @Test
    public void acknowledgeMessageShouldMakeDbEntryAcknowledged() throws Exception {
        MongoCollection<Document> dataSource = Given.aDataSource();

        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.POST_JSON);
        When.iSendAMessage("{\"payload\":[\"aa\"],\"type\":\"ACKNOWLEDGE_REQUEST\"}", null, BETS_TO_SCORES_QUEUE, SCORES_TO_BETS_ROUTE);

        TimeUnit.SECONDS.sleep(1);
        UserBet userBet = new Json().fromJson(dataSource.find(new BasicDBObject("betId", "aa")).first().toJson(), UserBet.class);

        assertTrue(userBet.isAcknowledged());
    }
}
