package hu.bets.model.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class BetAggregationResponse {

    private int numberOfElements;
    private List<UserBet> userBets;
    private String hash;

    @JsonCreator
    public BetAggregationResponse(@JsonProperty("numberOfElements") int numberOfElements, @JsonProperty("userBets") List<UserBet> userBets, @JsonProperty("hash") String hash) {
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
