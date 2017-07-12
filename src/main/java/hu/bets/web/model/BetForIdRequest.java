package hu.bets.web.model;

import java.util.Collections;
import java.util.List;

public class BetForIdRequest {
    private String userId;
    private List<String> ids;
    private String token;

    public BetForIdRequest(String userId, List<String> ids, String token) {
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
