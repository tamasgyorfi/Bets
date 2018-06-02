package hu.bets.web.api;

import hu.bets.common.util.schema.InvalidScemaException;
import hu.bets.common.util.schema.SchemaValidator;
import hu.bets.model.data.Bet;
import hu.bets.service.BetSaveException;
import hu.bets.service.FootballBetService;
import hu.bets.util.Json;
import hu.bets.web.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/bets/football/v1")
public class FootballBetResource {

    private static final Json JSON = new Json();
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

        LOGGER.info("Save bet request received: " + input);
        try {
            SaveBetRequest saveBetRequest = validateAndParse(input);
            footballBetService.saveBet(saveBetRequest);
            return JSON.toJson(SaveBetResponse.success(saveBetRequest.getUserId()));
        } catch (BetSaveException | InvalidScemaException e) {
            return JSON.toJson(SaveBetResponse.failure(e.getMessage()));
        }
    }

    @POST
    @Path("userBets")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserBets(String input) {
        LOGGER.info("Read bets request received: " + input);
        try {
            BetForIdRequest betForIdRequest = JSON.fromJson(input, BetForIdRequest.class);
            List<Bet> bets = footballBetService.getBetsFor(betForIdRequest.getUserId(), betForIdRequest.getIds());
            return JSON.toJson(BetForIdResponse.success(bets, "token-to-be-filled"));
        } catch (Exception e) {
            return JSON.toJson(BetForIdResponse.failure(e.getMessage(), "token-to-be-filled"));
        }
    }

    @POST
    @Path("user-bets")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilteredUserBets(String input) {
        LOGGER.info("Read bets request received: " + input);
        try {
            BetForFilterRequest betForIdRequest = JSON.fromJson(input, BetForFilterRequest.class);
            List<Bet> bets = footballBetService.getBetsForFilter(betForIdRequest.getFilters());
            return Response.ok(BetForIdResponse.success(bets, "token-to-be-filled")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(BetForIdResponse.failure(e.getMessage(), "token-to-be-filled")).build();
        }
    }


    private SaveBetRequest validateAndParse(String input) {
        LOGGER.info("Validating incoming payload.");
        schemaValidator.validatePayload(input, "bet.request.schema.json");
        return JSON.fromJson(input, SaveBetRequest.class);
    }
}
