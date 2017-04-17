package hu.bets.messaging.execution;

import hu.bets.messaging.MessageType;
import hu.bets.service.FootballBetService;

import java.util.List;
import java.util.concurrent.CompletionService;

public class MessageExecutor {

    protected CompletionService<List<String>> executorService;
    private FootballBetService footballBetService;

    public MessageExecutor(CompletionService<List<String>> executorService, FootballBetService footballBetService) {
        this.executorService = executorService;
        this.footballBetService = footballBetService;
    }

    public void submit(String message, MessageType messageType) {
        switch (messageType) {
            case UNKNOWN:
                return;
            case ACKNOWLEDGE: {
                executorService.submit(new AcknowledgeTask(footballBetService, message));
                break;
            }
            case AGGREGATION_REQUEST: {
                executorService.submit(new BetAggregationTask(footballBetService, message));
                break;
            }
        }
    }
}
