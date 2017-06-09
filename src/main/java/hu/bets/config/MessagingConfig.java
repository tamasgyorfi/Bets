package hu.bets.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import hu.bets.common.config.CommonMessagingConfig;
import hu.bets.common.messaging.DefaultMessageListener;
import hu.bets.common.messaging.MessageListener;
import hu.bets.messaging.execution.MessageExecutor;
import hu.bets.messaging.receiver.MessageConsumer;
import hu.bets.messaging.sender.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.concurrent.CompletionService;

import static hu.bets.messaging.MessagingConstants.*;

@Configuration
@Import(CommonMessagingConfig.class)
public class MessagingConfig {

    @Autowired
    private Channel senderChannel;

    @Autowired
    private Channel receiverChannel;

    @Bean
    public MessageListener messageListener(Consumer consumer) {
        return new DefaultMessageListener(receiverChannel, consumer, SCORES_TO_BETS_QUEUE, EXCHANGE_NAME, SCORES_TO_BETS_ROUTE);
    }

    @Bean
    public Consumer consumer(MessageExecutor executor) {
        return new MessageConsumer(receiverChannel, executor);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public MessageSender betAggregateResultSender(CompletionService<List<String>> completionService) {
        return new MessageSender(completionService, senderChannel);
    }
}
