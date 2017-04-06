package hu.bets.web.api;

import hu.bets.service.BetSaveException;
import hu.bets.service.FootballBetService;
import hu.bets.web.model.Bet;
import hu.bets.web.model.BetResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("bets/football/v1")
public class FootballBetResource {

    private static final Logger LOGGER = Logger.getLogger(FootballBetResource.class);

    @Autowired
    private FootballBetService footballBetService;

    @POST
    @Path("result")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BetResponse postFootballResult(Bet bet) {

        LOGGER.info("Request received: " + bet);

        try {
            String id = footballBetService.saveBet(bet);
            return BetResponse.success(id);
        } catch (BetSaveException e) {
            return BetResponse.failure(e.getMessage());
        }
    }

}
