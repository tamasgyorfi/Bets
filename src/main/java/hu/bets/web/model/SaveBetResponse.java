package hu.bets.web.model;

public class SaveBetResponse {

    private String id;
    private String error;

    private SaveBetResponse(String id, String error) {
        this.id = id;
        this.error = error;
    }

    public static SaveBetResponse success(String id) {
        return new SaveBetResponse(id, "");
    }

    public static SaveBetResponse failure(String reason) {
        return new SaveBetResponse("", reason);
    }

    public String getId() {
        return id;
    }

    public String getError() {
        return error;
    }
}
