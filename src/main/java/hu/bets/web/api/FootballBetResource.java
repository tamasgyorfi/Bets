package hu.bets.web.api;

import hu.bets.service.BetSaveException;
import hu.bets.service.FootballBetService;
import hu.bets.web.model.Bet;
import hu.bets.web.model.BetResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("/")
public class FootballBetResource {

    private static final Logger LOGGER = Logger.getLogger(FootballBetResource.class);

    @Autowired
    private FootballBetService footballBetService;

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public String getDescription() {
        return "<html><body><h1>Football-bets service up and running</h1></body></html>";
    }

    @POST
    @Path("bets/football/v1/result")
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
