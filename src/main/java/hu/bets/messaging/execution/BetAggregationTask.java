package hu.bets.messaging.execution;

import com.google.gson.Gson;
import hu.bets.model.data.BetAggregationRequest;
import hu.bets.model.data.BetAggregationResponse;
import hu.bets.model.data.UserBet;
import hu.bets.service.FootballBetService;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class BetAggregationTask extends Task<List<List<UserBet>>> {

    private static final Logger LOGGER = Logger.getLogger(BetAggregationTask.class);

    private FootballBetService footballBetService;
    private String message;

    public BetAggregationTask(FootballBetService footballBetService, String message) {
        this.footballBetService = footballBetService;
        this.message = message;
    }

    @Override
    public List<List<UserBet>> doWork() {
        Gson gson = new Gson();

        BetAggregationRequest betAggregationRequest = gson.fromJson(message, BetAggregationRequest.class);

        return footballBetService.getBetsForMatches(betAggregationRequest.getMatchIds());
    }

    @Override
    public List<String> convertToPayload(List<List<UserBet>> result) {
        List<String> payload = new ArrayList<>(result.size());
        for (List<UserBet> userBet : result) {
            BetAggregationResponse response = new BetAggregationResponse(userBet.size(), userBet, getHash(userBet));
            payload.add(new Gson().toJson(response));
        }

        return payload;
    }

    private String getHash(List<UserBet> userBet) {
        try {
            String betsString = userBet.toString();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(betsString.getBytes());
            byte[] digest = md5.digest();

            return new BigInteger(1, digest).toString(16);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.info("No MD5 algorithm found.");
        }

        return "Error hashing bets.";
    }

}
