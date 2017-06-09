package hu.bets.messaging.execution;

import hu.bets.messaging.MessageType;
import hu.bets.service.FootballBetService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.CompletionService;

public class MessageExecutor {

    private static final Logger LOGGER = Logger.getLogger(MessageExecutor.class);

    protected CompletionService<List<String>> executorService;
    private FootballBetService footballBetService;

    public MessageExecutor(CompletionService<List<String>> executorService, FootballBetService footballBetService) {
        this.executorService = executorService;
        this.footballBetService = footballBetService;
    }

    public void submit(String message, MessageType messageType) {
        switch (messageType) {
            case UNKNOWN:
                LOGGER.info("Unknown message received: " + message);
                return;
            case ACKNOWLEDGE: {
                LOGGER.info("Acknowledge message received: " + message);
                executorService.submit(new AcknowledgeTask(footballBetService, message));
                break;
            }
            case AGGREGATION_REQUEST: {
                LOGGER.info("Aggregation request message received: " + message);
                executorService.submit(new BetAggregationTask(footballBetService, message));
                break;
            }
        }
    }
}
