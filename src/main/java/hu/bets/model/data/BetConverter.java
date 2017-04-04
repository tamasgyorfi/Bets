package hu.bets.model.data;

import hu.bets.web.model.Bet;

public class BetConverter {

    public UserBet fromBet(Bet bet) {
        return new UserBet(bet.getUserId(),
                bet.getCompetitionId(),
                bet.getMatchId(),
                bet.getHomeTeamId(),
                bet.getAwayTeamId(),
                bet.getHomeTeamGoals(),
                bet.getAwayTeamGoals()
        );
    }
}
