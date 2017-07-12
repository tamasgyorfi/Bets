package hu.bets.web.api;

import com.google.gson.Gson;
import hu.bets.common.util.schema.InvalidScemaException;
import hu.bets.common.util.schema.SchemaValidator;
import hu.bets.model.data.Result;
import hu.bets.service.BetSaveException;
import hu.bets.service.FootballBetService;
import hu.bets.web.model.SaveBetRequest;
import hu.bets.web.model.BetForIdRequest;
import hu.bets.web.model.BetForIdResponse;
import hu.bets.web.model.SaveBetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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

        LOGGER.info("Save bet request received: " + input);
        try {
            SaveBetRequest saveBetRequest = validateAndParse(input);
            String id = footballBetService.saveBet(saveBetRequest);
            return GSON.toJson(SaveBetResponse.success(id));
        } catch (BetSaveException | InvalidScemaException e) {
            return GSON.toJson(SaveBetResponse.failure(e.getMessage()));
        }
    }

    @POST
    @Path("userBets")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserBets(String input) {
        LOGGER.info("Read bets request received: " + input);
        try {
            BetForIdRequest betForIdRequest = GSON.fromJson(input, BetForIdRequest.class);
            List<Result> results = footballBetService.getBetsFor(betForIdRequest.getUserId(), betForIdRequest.getIds());
            return GSON.toJson(BetForIdResponse.success(results, "token-to-be-filled"));
        } catch (Exception e) {
            return GSON.toJson(BetForIdResponse.failure(e.getMessage(), "token-to-be-filled"));
        }
    }

    private SaveBetRequest validateAndParse(String input) {
        LOGGER.info("Validating incoming payload.");
        schemaValidator.validatePayload(input, "bet.request.schema.json");
        return GSON.fromJson(input, SaveBetRequest.class);
    }
}
