package hu.bets.messaging.execution;

import hu.bets.model.data.Request;
import hu.bets.service.FootballBetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class AcknowledgeTask extends Task<Void> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcknowledgeTask.class);

    private FootballBetService footballBetService;
    private Request request;

    public AcknowledgeTask(FootballBetService footballBetService, Request request) {
        this.footballBetService = footballBetService;
        this.request = request;
    }

    @Override
    public Void doWork() {
        List<String> betIds = request.getPayload();
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
