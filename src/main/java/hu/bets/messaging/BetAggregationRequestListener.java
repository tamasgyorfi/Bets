package hu.bets.messaging;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static hu.bets.messaging.MessagingConstants.EXCHANGE_NAME;

public class BetAggregationRequestListener {

    private final Channel channel;
    private final Consumer consumer;

    public BetAggregationRequestListener(Channel channel, Consumer consumer) {
        this.channel = channel;
        this.consumer = consumer;
    }

    public void receive(String queueName) throws IOException, TimeoutException {
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        channel.basicConsume(queueName, true, consumer);
    }
}
