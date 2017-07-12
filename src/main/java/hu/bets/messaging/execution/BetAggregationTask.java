package hu.bets.messaging.execution;

import hu.bets.common.util.hash.HashGenerator;
import hu.bets.model.data.BetAggregationResponse;
import hu.bets.model.data.Request;
import hu.bets.model.data.UserBet;
import hu.bets.service.FootballBetService;
import hu.bets.util.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BetAggregationTask extends Task<List<List<UserBet>>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BetAggregationTask.class);
    private static final Json JSON = new Json();

    private FootballBetService footballBetService;
    private HashGenerator hashGenerator;
    private Request request;

    public BetAggregationTask(FootballBetService footballBetService, HashGenerator hashGenerator, Request request) {
        this.footballBetService = footballBetService;
        this.hashGenerator = hashGenerator;
        this.request = request;
    }

    @Override
    public List<List<UserBet>> doWork() {
        LOGGER.info("Incoming request is: " + request);
        return footballBetService.getBetsForMatches(request.getPayload());
    }

    @Override
    public List<String> convertToPayload(List<List<UserBet>> result) {
        List<String> payload = new ArrayList<>(result.size());
        for (List<UserBet> userBet : result) {
            BetAggregationResponse response = new BetAggregationResponse(userBet.size(), userBet, hashGenerator.getHash(userBet));
            payload.add(JSON.toJson(response));
        }

        LOGGER.info("Returning converted payload: " + payload);
        return payload;
    }
}
