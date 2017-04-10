package integration.requestreply;

import com.google.gson.Gson;
import hu.bets.model.data.BetAggregationResponse;
import integration.Constants;
import integration.steps.Given;
import integration.steps.Then;
import integration.steps.When;
import org.junit.Test;

import java.util.List;

import static hu.bets.messaging.MessagingConstants.AGGREGATE_REPLY_QUEUE_NAME;
import static integration.Constants.*;
import static org.junit.Assert.assertEquals;

public class MessagingIntegrationTest {

    @Test
    public void aggregationMessageShouldTriggerAgrregationReply() throws Exception {
        Given.environmentIsUpAndRunning();

        When.iRepeateAPostRequest(PROTOCOL + "://" + HOST + ":" + PORT + ADD_BET_ENDPOINT, Constants.POST_JSON, 101);
        When.iSendAMessage("{matchIds:[aa]}");

        List<byte[]> messages = Then.iExpectOutgoingMessages(AGGREGATE_REPLY_QUEUE_NAME, 1);

        assertEquals(2, messages.size());
        BetAggregationResponse message1 = new Gson().fromJson(new String(messages.get(0)), BetAggregationResponse.class);
        BetAggregationResponse message2 = new Gson().fromJson(new String(messages.get(1)), BetAggregationResponse.class);

        assertEquals(100, message1.getNumberOfElements());
        assertEquals(1, message2.getNumberOfElements());

        Given.environmentIsShutDown();
    }
}
