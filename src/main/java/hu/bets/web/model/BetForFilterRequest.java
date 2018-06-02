package hu.bets.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import hu.bets.model.filter.Filter;

import java.util.Collections;
import java.util.List;

public class BetForFilterRequest {

    private List<Filter> filters;
    private String token;

    @JsonCreator
    public BetForFilterRequest(@JsonProperty("filters") List<Filter> filters, @JsonProperty("token") String token) {
        this.filters = filters;
        this.token = token;
    }

    public List<Filter> getFilters() {
        return Collections.unmodifiableList(filters);
    }

    public String getToken() {
        return token;
    }
}
