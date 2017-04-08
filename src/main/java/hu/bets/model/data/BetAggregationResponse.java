package hu.bets.model.data;

import java.util.Collections;
import java.util.List;

public class BetAggregationResponse {

    private int numberOfElements;
    private List<UserBet> userBets;
    private String hash;

    public BetAggregationResponse(int numberOfElements, List<UserBet> userBets, String hash) {
        this.numberOfElements = numberOfElements;
        this.userBets = userBets;
        this.hash = hash;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public List<UserBet> getUserBets() {
        return Collections.unmodifiableList(userBets);
    }

    public String getHash() {
        return hash;
    }
}
