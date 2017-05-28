package hu.bets.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import hu.bets.common.config.CommonMessagingConfig;
import hu.bets.common.messaging.DefaultMessageListener;
import hu.bets.common.messaging.MessageListener;
import hu.bets.messaging.execution.MessageExecutor;
import hu.bets.messaging.MessagingConstants;
import hu.bets.messaging.receiver.MessageConsumer;
import hu.bets.messaging.sender.MessageSender;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.concurrent.CompletionService;

import static hu.bets.messaging.MessagingConstants.AGGREGATE_REQUEST_QUEUE_NAME;
import static hu.bets.messaging.MessagingConstants.AGGREGATE_REQUEST_ROUTING_KEY;
import static hu.bets.messaging.MessagingConstants.EXCHANGE_NAME;

@Configuration
@Import(CommonMessagingConfig.class)
public class MessagingConfig {

    @Bean
    public MessageListener messageListener(Channel channel, Consumer consumer) {
        return new DefaultMessageListener(channel, consumer, AGGREGATE_REQUEST_QUEUE_NAME, EXCHANGE_NAME, AGGREGATE_REQUEST_ROUTING_KEY);
    }

    @Bean
    public Consumer consumer(Channel channel, MessageExecutor executor) {
        return new MessageConsumer(channel, executor);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public MessageSender betAggregateResultSender(CompletionService<List<String>> completionService, Channel channel) {
        return new MessageSender(completionService, channel);
    }
}
