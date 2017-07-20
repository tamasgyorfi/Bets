package hu.bets.model.data;

import hu.bets.common.util.IdGenerator;
import hu.bets.web.model.BetRequest;

public class BetConverter {

    private IdGenerator idGenerator;

    public BetConverter(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public UserBet createNewUserBet(String userId, BetRequest saveBetRequest) {
        return new UserBet(userId,
                new Match(saveBetRequest.getMatchId(), saveBetRequest.getCompetitionId(), saveBetRequest.getHomeTeamId(), saveBetRequest.getAwayTeamId()),
                new Bet(saveBetRequest.getMatchId(), idGenerator.generateBetId(userId), saveBetRequest.getHomeTeamGoals(), saveBetRequest.getAwayTeamGoals()));
    }
}
