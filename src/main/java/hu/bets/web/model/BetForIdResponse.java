package hu.bets.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import hu.bets.model.data.Result;

import java.util.Collections;
import java.util.List;

public class BetForIdResponse {

    private String error;
    private List<Result> payload = Collections.emptyList();
    private String token;

    @JsonCreator
    private BetForIdResponse(@JsonProperty("error") String error, @JsonProperty("payload") List<Result> payload, @JsonProperty("token") String token) {
        this.error = error;
        this.payload = payload;
    }

    public static BetForIdResponse success(List<Result> payload, String token) {
        return new BetForIdResponse("", payload, token);
    }

    public static BetForIdResponse failure(String reason, String token) {
        return new BetForIdResponse(reason, null, token);
    }

    public String getError() {
        return error;
    }

    public List<Result> getPayload() {
        return payload;
    }
}
