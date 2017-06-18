package hu.bets.messaging.execution;

import hu.bets.common.util.hash.HashGenerator;
import hu.bets.model.data.Request;
import hu.bets.service.FootballBetService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.CompletionService;

public class MessageExecutor {

    private static final Logger LOGGER = Logger.getLogger(MessageExecutor.class);

    protected CompletionService<List<String>> executorService;
    private FootballBetService footballBetService;
    private HashGenerator hashGenerator;

    public MessageExecutor(CompletionService<List<String>> executorService, FootballBetService footballBetService, HashGenerator hashGenerator) {
        this.executorService = executorService;
        this.footballBetService = footballBetService;
        this.hashGenerator = hashGenerator;
    }

    public void submit(Request request) {
        switch (request.getType()) {
            case ACKNOWLEDGE_REQUEST: {
                LOGGER.info("Acknowledge message received: " + request);
                executorService.submit(new AcknowledgeTask(footballBetService, request));
                break;
            }
            case BETS_REQUEST: {
                LOGGER.info("Aggregation request message received: " + request);
                executorService.submit(new BetAggregationTask(footballBetService, hashGenerator, request));
                break;
            }
            default:
                LOGGER.info("Unknown message received: " + request);
                return;
        }
    }
}
