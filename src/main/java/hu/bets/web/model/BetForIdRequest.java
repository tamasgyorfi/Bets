package hu.bets.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class BetForIdRequest {
    private String userId;
    private List<String> ids;
    private String token;

    @JsonCreator
    public BetForIdRequest(@JsonProperty("userId")String userId, @JsonProperty("ids")List<String> ids, @JsonProperty("token")String token) {
        this.userId = userId;
        this.ids = ids;
        this.token = token;
    }

    public List<String> getIds() {
        return Collections.unmodifiableList(ids);
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }
}
