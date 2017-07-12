package hu.bets.web.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import hu.bets.model.data.Bet;
import hu.bets.model.data.Match;

public class SaveBetRequest {

    private final String userId;

    @JsonUnwrapped
    private final Match match;
    @JsonUnwrapped
    private final Bet bet;

    private final String token;

    public SaveBetRequest(String userId, Match match, Bet bet, String token) {
        this.userId = userId;
        this.match = match;
        this.bet = bet;
        this.token = token;
    }

    private SaveBetRequest() {
        this.userId = "";
        this.match = null;
        this.bet = null;
        this.token = "";
    }

    public String getUserId() {
        return userId;
    }

    public Match getMatch() {
        return match;
    }

    public Bet getBet() {
        return bet;
    }

    public String getToken() {
        return token;
    }
}
