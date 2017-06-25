package hu.bets.model.data;

import hu.bets.common.util.IdGenerator;
import hu.bets.web.model.Bet;

public class BetConverter {

    private IdGenerator idGenerator;

    public BetConverter(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public UserBet fromBet(Bet bet) {
        return new UserBet(bet.getUserId(),
                bet.getCompetitionId(),
                bet.getMatchId(),
                bet.getHomeTeamId(),
                bet.getAwayTeamId(),
                bet.getHomeTeamGoals(),
                bet.getAwayTeamGoals(),
                idGenerator.generateBetId(bet.getUserId())
        );
    }
}
