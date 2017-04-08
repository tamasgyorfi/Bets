package hu.bets.messaging.receiver;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import hu.bets.aggregation.BetAggregationExecutor;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MessageConsumer extends DefaultConsumer {

    private static Logger LOGGER = Logger.getLogger(MessageConsumer.class);
    private BetAggregationExecutor betAggregationExecutor;

    public MessageConsumer(Channel channel, BetAggregationExecutor betAggregationExecutor) {
        super(channel);
        this.betAggregationExecutor = betAggregationExecutor;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        LOGGER.info("Received message: " + message);

        betAggregationExecutor.submit(message);
    }
}
