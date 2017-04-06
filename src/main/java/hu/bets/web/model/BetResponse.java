package hu.bets.web.model;

public class BetResponse {

    private String response;

    private BetResponse(String response) {
        this.response = response;
    }

    public static BetResponse success(String id) {
        return new BetResponse(id);
    }

    public static BetResponse failure(String reason) {
        return new BetResponse(reason);
    }

    public String getResponse() {
        return response;
    }
}
