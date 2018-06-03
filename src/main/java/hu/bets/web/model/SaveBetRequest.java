package hu.bets.web.model;

import java.util.List;

public class SaveBetRequest {

    private final List<BetRequest> bets;
    private final String token;

    public SaveBetRequest(List<BetRequest> bets, String token) {
        this.bets = bets;
        this.token = token;
    }

    private SaveBetRequest() {
        this.token = "";
        this.bets = null;
    }

    public String getToken() {
        return token;
    }

    public List<BetRequest> getBets() {
        return bets;
    }
}
