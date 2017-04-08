package hu.bets.aggregation;

import com.google.gson.Gson;
import hu.bets.model.data.BetAggregationRequest;
import hu.bets.model.data.UserBet;
import hu.bets.service.FootballBetService;

import java.util.List;
import java.util.concurrent.Callable;

public class BetAggregationTask implements Callable<List<List<UserBet>>> {

    private FootballBetService footballBetService;
    private String message;

    public BetAggregationTask(FootballBetService footballBetService, String message) {
        this.footballBetService = footballBetService;
        this.message = message;
    }

    @Override
    public List<List<UserBet>> call() {
        Gson gson = new Gson();

        BetAggregationRequest betAggregationRequest = gson.fromJson(message, BetAggregationRequest.class);
        return footballBetService.getBetsForMatches(betAggregationRequest.getMatchIds());
    }
}
