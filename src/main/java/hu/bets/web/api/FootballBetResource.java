package hu.bets.web.api;

import com.google.gson.Gson;
import hu.bets.common.util.schema.InvalidScemaException;
import hu.bets.common.util.schema.SchemaValidator;
import hu.bets.service.BetSaveException;
import hu.bets.service.FootballBetService;
import hu.bets.web.model.Bet;
import hu.bets.web.model.BetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("/bets/football/v1")
public class FootballBetResource {

    private static final Gson GSON = new Gson();
    private static final Logger LOGGER = LoggerFactory.getLogger(FootballBetResource.class);

    @Autowired
    private FootballBetService footballBetService;
    @Autowired
    private SchemaValidator schemaValidator;

    @GET
    @Path("info")
    @Produces(MediaType.TEXT_HTML)
    public String getDescription() {
        return "<html><body><h1>Football-bets service up and running</h1></body></html>";
    }

    @POST
    @Path("bet")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String postFootballResult(String input) {

        LOGGER.info("Request received: " + input);
        try {
            Bet bet = validateAndParse(input);
            String id = footballBetService.saveBet(bet);
            return GSON.toJson(BetResponse.success(id));
        } catch (BetSaveException | InvalidScemaException e) {
            return GSON.toJson(BetResponse.failure(e.getMessage()));
        }
    }

    private Bet validateAndParse(String input) {
        LOGGER.info("Validating incoming payload.");
        schemaValidator.validatePayload(input, "bet.request.schema.json");
        return GSON.fromJson(input, Bet.class);
    }
}
