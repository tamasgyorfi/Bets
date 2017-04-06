package hu.bets.service;

import hu.bets.data.FootballDAO;
import hu.bets.model.data.BetConverter;
import hu.bets.web.model.Bet;

public class DefaultFootballBetService implements FootballBetService{

    private final IdGenerator idGenerator;
    private final FootballDAO footballDAO;

    public DefaultFootballBetService(IdGenerator idGenerator, FootballDAO footballDAO) {
        this.idGenerator = idGenerator;
        this.footballDAO = footballDAO;
    }

    public String saveBet(Bet bet) throws BetSaveException {
        String betId = idGenerator.generateBetId(bet.getUserId());
        footballDAO.save(betId, new BetConverter().fromBet(bet));

        return betId;
    }
}
