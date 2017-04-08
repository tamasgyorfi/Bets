package hu.bets.aggregation;

import hu.bets.model.data.UserBet;
import hu.bets.service.FootballBetService;

import java.util.List;
import java.util.concurrent.CompletionService;

public class BetAggregationExecutor {

    private CompletionService<List<List<UserBet>>> executorService;
    private FootballBetService footballBetService;

    public BetAggregationExecutor(CompletionService<List<List<UserBet>>> executorService, FootballBetService footballBetService) {
        this.executorService = executorService;
        this.footballBetService = footballBetService;
    }

    public void submit(String message) {
        executorService.submit(new BetAggregationTask(footballBetService, message));
    }
}
