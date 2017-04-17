package hu.bets.messaging.execution;

import com.google.gson.Gson;
import hu.bets.model.data.BetAcknowledgedRequest;
import hu.bets.service.FootballBetService;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class AcknowledgeTask extends Task<Void> {

    private static final Logger LOGGER = Logger.getLogger(AcknowledgeTask.class);

    private FootballBetService footballBetService;
    private String message;

    public AcknowledgeTask(FootballBetService footballBetService, String message) {
        this.footballBetService = footballBetService;
        this.message = message;
    }

    @Override
    public Void doWork() {
        List<String> betIds = new Gson().fromJson(message, BetAcknowledgedRequest.class).getBetIds();
        List<String> acknowledgedIds = footballBetService.acknowledgeAll(betIds);

        if (betIds.size() != acknowledgedIds.size()) {
            betIds.retainAll(acknowledgedIds);
            LOGGER.error("Failed to acknowledge the following IDs: " + betIds);
        }

        // Safe to return null, we don't need this.
        return null;
    }

    @Override
    public List<String> convertToPayload(Void empty) {
        return Collections.emptyList();
    }
}
