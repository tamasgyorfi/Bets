package hu.bets.web.model;

import java.util.List;

public class SaveBetRequest {

    private final String userId;
    private final List<BetRequest> bets;
    private final String token;

    public SaveBetRequest(String userId, List<BetRequest> bets, String token) {
        this.userId = userId;
        this.bets = bets;
        this.token = token;
    }

    private SaveBetRequest() {
        this.userId = "";
        this.token = "";
        this.bets = null;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public List<BetRequest> getBets() {
        return bets;
    }
}
