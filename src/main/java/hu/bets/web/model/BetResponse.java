package hu.bets.web.model;

public class BetResponse {

    private String id;
    private String error;

    private BetResponse(String id, String error) {
        this.id = id;
        this.error = error;
    }

    public static BetResponse success(String id) {
        return new BetResponse(id, "");
    }

    public static BetResponse failure(String reason) {
        return new BetResponse("", reason);
    }

    public String getId() {
        return id;
    }

    public String getError() {
        return error;
    }
}
