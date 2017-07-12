package hu.bets.messaging.receiver;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import hu.bets.messaging.execution.MessageExecutor;
import hu.bets.model.data.Request;
import hu.bets.util.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MessageConsumer extends DefaultConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    private MessageExecutor messageExecutor;

    public MessageConsumer(Channel channel, MessageExecutor messageExecutor) {
        super(channel);
        this.messageExecutor = messageExecutor;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        LOGGER.info("Received message: " + message);

        try {
            Request request = new Json().fromJson(message, Request.class);
            messageExecutor.submit(request);
        } catch (Exception e) {
            LOGGER.error("Unable to submit request. ", e);
        }
    }
}
