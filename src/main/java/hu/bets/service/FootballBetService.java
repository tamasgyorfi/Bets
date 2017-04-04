package hu.bets.service;

import hu.bets.data.FootballDAO;
import hu.bets.model.data.BetConverter;
import hu.bets.web.model.Bet;

public class FootballBetService {

    private final IdService idService;
    private final FootballDAO footballDAO;
    private final ModelConverterService modelConverterService;

    public FootballBetService(IdService idService, FootballDAO footballDAO, ModelConverterService modelConverterService) {
        this.idService = idService;
        this.footballDAO = footballDAO;
        this.modelConverterService = modelConverterService;
    }

    public String saveBet(Bet bet) throws BetSaveException {
        String betId = idService.generateBetId(bet.getUserId());
        footballDAO.save(betId, new BetConverter().fromBet(bet));

        return betId;
    }
}
