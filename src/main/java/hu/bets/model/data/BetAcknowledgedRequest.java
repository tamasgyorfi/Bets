package hu.bets.model.data;

import java.util.ArrayList;
import java.util.List;

public class BetAcknowledgedRequest {
    private List<String> betIds = new ArrayList<>(50);

    public List<String> getBetIds() {
        return betIds;
    }

    private void setBetIds(List<String> betIds) {
        this.betIds = betIds;
    }

}
