package hu.bets.messaging.execution;

import com.google.gson.Gson;
import hu.bets.common.util.hash.HashGenerator;
import hu.bets.model.data.BetAggregationResponse;
import hu.bets.model.data.Request;
import hu.bets.model.data.UserBet;
import hu.bets.service.FootballBetService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BetAggregationTask extends Task<List<List<UserBet>>> {

    private static final Logger LOGGER = Logger.getLogger(BetAggregationTask.class);

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
        Gson gson = new Gson();

        LOGGER.info("Incoming request is: " + request);
        return footballBetService.getBetsForMatches(request.getPayload());
    }

    @Override
    public List<String> convertToPayload(List<List<UserBet>> result) {
        List<String> payload = new ArrayList<>(result.size());
        for (List<UserBet> userBet : result) {
            BetAggregationResponse response = new BetAggregationResponse(userBet.size(), userBet, hashGenerator.getHash(userBet));
            payload.add(new Gson().toJson(response));
        }

        LOGGER.info("Returning converted payload: " + payload);
        return payload;
    }
}
