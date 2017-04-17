package hu.bets.messaging.receiver;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import hu.bets.messaging.MessageType;
import hu.bets.messaging.execution.MessageExecutor;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;

import static hu.bets.messaging.MessageType.UNKNOWN;

public class MessageConsumer extends DefaultConsumer {

    private static final String MESSAGE_TYPE_KEY = "MESSAGE_TYPE";
    private static Logger LOGGER = Logger.getLogger(MessageConsumer.class);

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

        messageExecutor.submit(message, getMessageType(properties));
    }

    private MessageType getMessageType(AMQP.BasicProperties properties) {
        Map<String, Object> headers = properties.getHeaders();
        if (headers != null) {
            String messageType = headers.get(MESSAGE_TYPE_KEY).toString();
            try {
                return MessageType.valueOf(messageType);
            } catch (Exception e) {
            }
        }
        return UNKNOWN;
    }
}
