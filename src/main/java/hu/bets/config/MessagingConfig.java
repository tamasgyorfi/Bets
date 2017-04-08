package hu.bets.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import hu.bets.aggregation.BetAggregationExecutor;
import hu.bets.messaging.sender.BetAggregateResultSender;
import hu.bets.messaging.receiver.BetAggregationRequestListener;
import hu.bets.messaging.receiver.MessageConsumer;
import hu.bets.messaging.MessagingConstants;
import hu.bets.model.data.UserBet;
import hu.bets.util.EnvironmentVarResolver;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.TimeoutException;

@Configuration
public class MessagingConfig {

    private static final String MESSAGING_URI = "CLOUDAMQP_URL";
    private static final Logger LOGGER = Logger.getLogger(MessagingConfig.class);

    @Bean
    public BetAggregationRequestListener betAggregationRequestListener(Channel channel, Consumer consumer) {
        return new BetAggregationRequestListener(channel, consumer, MessagingConstants.AGGREGATE_REQUEST_QUEUE_NAME);
    }

    @Bean
    public Channel channel(Connection connection) {
        try {
            return connection.createChannel();
        } catch (IOException e) {
            LOGGER.error(e);
        }

        return null;
    }

    @Bean
    public Connection connection(ConnectionFactory connectionFactory) {
        try {
            return connectionFactory.newConnection();
        } catch (TimeoutException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();

        try {
            factory.setUri(EnvironmentVarResolver.getEnvVar(MESSAGING_URI, ""));
        } catch (URISyntaxException | NoSuchAlgorithmException | KeyManagementException e) {
            LOGGER.error("Unable to set up messaging.", e);
        }

        return factory;
    }

    @Bean
    public Consumer consumer(Channel channel, BetAggregationExecutor executor) {
        return new MessageConsumer(channel, executor);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public BetAggregateResultSender betAggregateResultSender(CompletionService<List<List<UserBet>>> completionService, Channel channel) {
        return new BetAggregateResultSender(completionService, channel);
    }
}
