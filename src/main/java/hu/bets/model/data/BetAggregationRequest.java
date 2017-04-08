package hu.bets.model.data;

import java.util.ArrayList;
import java.util.List;

public class BetAggregationRequest {

    private List<String> matchIds = new ArrayList<>(50);

    public List<String> getMatchIds() {
        return matchIds;
    }

    private void setMatchIds(List<String> matchIds) {
        this.matchIds = matchIds;
    }
}
