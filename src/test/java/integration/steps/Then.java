package integration.steps;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import hu.bets.web.model.BetResponse;
import integration.steps.util.ApplicationContextHolder;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static hu.bets.messaging.MessagingConstants.AGGREGATE_RESPONSE_ROUTING_KEY;
import static hu.bets.messaging.MessagingConstants.EXCHANGE_NAME;
import static org.junit.Assert.*;

public class Then {

    public static void theBetResponseIsOk(BetResponse betResponse) {
        assertFalse(betResponse.getId().isEmpty());
        assertTrue(betResponse.getError().isEmpty());
    }

    public static void theDatasourceContainsBet(String betId,  MongoCollection<Document> collection) {
        assertEquals(1, collection.count());

        BasicDBObject query = new BasicDBObject();
        query.put("betId", betId);

        assertNotNull(collection.find(query).first());
    }

    public static List<byte[]> iExpectOutgoingMessages(String queueName, long timeoutSeconds) throws Exception {
        Channel channel = ApplicationContextHolder.getBean(Channel.class);
        TestConsumer consumer = new TestConsumer(channel);

        channel.queueBind(queueName, EXCHANGE_NAME, AGGREGATE_RESPONSE_ROUTING_KEY);
        channel.basicConsume(queueName, true, consumer);

        TimeUnit.SECONDS.sleep(timeoutSeconds);

        return consumer.getMessages();
    }

    static class TestConsumer extends DefaultConsumer {

        private List<byte[]> messages = new ArrayList<>();

        public TestConsumer(Channel channel) {
            super(channel);
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope,
                                   AMQP.BasicProperties properties, byte[] body) throws IOException {
            messages.add(body);
        }

        public List<byte[]> getMessages() {
            return Collections.unmodifiableList(messages);
        }
    }
}
