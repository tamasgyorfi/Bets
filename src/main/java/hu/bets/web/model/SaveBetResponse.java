package hu.bets.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class SaveBetResponse {

    private List<String> urls;
    private String error;

    @JsonCreator
    private SaveBetResponse(@JsonProperty("urls") List<String> urls, @JsonProperty("error") String error) {
        this.urls = urls;
        this.error = error;
    }

    public static SaveBetResponse success(List<String> urls) {
        return new SaveBetResponse(urls, "");
    }

    public static SaveBetResponse failure(String reason) {
        return new SaveBetResponse(Collections.emptyList(), reason);
    }

    public List<String> getUrls() {
        return urls;
    }

    public String getError() {
        return error;
    }
}
