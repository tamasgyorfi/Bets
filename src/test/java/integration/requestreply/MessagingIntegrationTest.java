package integration.requestreply;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import hu.bets.config.MessagingConfig;
import hu.bets.config.WebConfig;
import hu.bets.model.data.BetAggregationResponse;
import hu.bets.model.data.UserBet;
import hu.bets.steps.Given;
import hu.bets.steps.Then;
import hu.bets.steps.When;
import integration.Constants;
import integration.FakeApplicationConfig;
import integration.FakeDbConfig;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static hu.bets.messaging.MessageType.ACKNOWLEDGE;
import static hu.bets.messaging.MessageType.AGGREGATION_REQUEST;
import static hu.bets.messaging.MessagingConstants.AGGREGATE_REPLY_QUEUE_NAME;
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
    }

    @After
    public void tearDown() throws Exception {
        Given.environmentIsShutDown();
    }

    @Test
    public void aggregationMessageShouldTriggerAgrregationReply() throws Exception {
        Map<String, Object> header = new HashMap<>();
        header.put("MESSAGE_TYPE", AGGREGATION_REQUEST.name());

        When.iRepeateAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.POST_JSON, 101);
        When.iSendAMessage("{matchIds:[aa]}", header);

        List<byte[]> messages = Then.iExpectOutgoingMessages(AGGREGATE_REPLY_QUEUE_NAME, 1);

        assertEquals(2, messages.size());
        BetAggregationResponse message1 = new Gson().fromJson(new String(messages.get(0)), BetAggregationResponse.class);
        BetAggregationResponse message2 = new Gson().fromJson(new String(messages.get(1)), BetAggregationResponse.class);

        assertEquals(100, message1.getNumberOfElements());
        assertEquals(1, message2.getNumberOfElements());

        Given.environmentIsShutDown();
    }

    @Test
    public void acknowledgeMessageShouldMakeDbEntryAcknowledged() throws Exception {
        MongoCollection<Document> dataSource = Given.aDataSource();
        Map<String, Object> header = new HashMap<>();
        header.put("MESSAGE_TYPE", ACKNOWLEDGE.name());

        When.iMakeAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.POST_JSON);
        When.iSendAMessage("{betIds:[aa]}", header);

        TimeUnit.SECONDS.sleep(1);
        UserBet userBet = new Gson().fromJson(dataSource.find(new BasicDBObject("betId", "aa")).first().toJson(), UserBet.class);

        Given.environmentIsShutDown();
        assertTrue(userBet.isAcknowledged());
    }
}
