package hu.bets.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SaveBetResponse {

    private String id;
    private String error;

    @JsonCreator
    private SaveBetResponse(@JsonProperty("id") String id, @JsonProperty("error") String error) {
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
