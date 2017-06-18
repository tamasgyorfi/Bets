package hu.bets.messaging.receiver;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import hu.bets.messaging.execution.MessageExecutor;
import hu.bets.model.data.Request;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MessageConsumer extends DefaultConsumer {

    private static final Logger LOGGER = Logger.getLogger(MessageConsumer.class);

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
            Request request = new Gson().fromJson(message, Request.class);
            messageExecutor.submit(request);
        } catch (Exception e) {
            LOGGER.error("Unable to submit request. ", e);
        }
    }
}
