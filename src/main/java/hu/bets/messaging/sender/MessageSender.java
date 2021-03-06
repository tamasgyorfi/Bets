package hu.bets.messaging.sender;

import com.rabbitmq.client.Channel;
import hu.bets.messaging.MessagingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Future;

public class MessageSender {

    private static final int NR_OF_RETRIES = 3;
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);

    private volatile boolean shouldContinue = true;

    private CompletionService<List<String>> completionService;
    private Channel channel;

    public MessageSender(CompletionService<List<String>> completionService, Channel channel) {
        this.completionService = completionService;
        this.channel = channel;
    }

    private void run() {
        while (shouldContinue) {
            Future<List<String>> result = completionService.poll();
            try {
                if (result != null) {
                    send(result.get());
                }
            } catch (InterruptedException e) {
                LOGGER.info("Message listener thread shutting down.");
                shouldContinue = false;
            } catch (Exception e) {
                LOGGER.error("Unable to send User Bets.", e);
            }
        }
    }

    public void start() {
        new Thread(this::run).start();
        LOGGER.info("Message sender thread started successfully.");
    }

    public void stop() {
        this.shouldContinue = false;
    }

    private void send(List<String> payloadBatch) {
        for (String payload : payloadBatch) {
            sendBatch(payload);
            LOGGER.info("Batch sent: " + payload);
        }
    }

    private void sendBatch(String payload) {
        for (int i = 0; i < NR_OF_RETRIES; i++) {
            try {
                channel.basicPublish(MessagingConstants.EXCHANGE_NAME, MessagingConstants.BETS_TO_SCORES_ROUTE, null, payload.getBytes());
                break;
            } catch (IOException e) {
                LOGGER.error("Unable to send batch: " + payload, e);
            }
        }
    }
}