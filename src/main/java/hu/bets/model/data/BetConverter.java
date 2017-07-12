package hu.bets.model.data;

import hu.bets.common.util.IdGenerator;
import hu.bets.web.model.SaveBetRequest;

public class BetConverter {

    private IdGenerator idGenerator;

    public BetConverter(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public UserBet fromBet(SaveBetRequest saveBetRequest) {
        return new UserBet(saveBetRequest.getUserId(),
                saveBetRequest.getCompetitionId(),
                saveBetRequest.getMatchId(),
                saveBetRequest.getHomeTeamId(),
                saveBetRequest.getAwayTeamId(),
                saveBetRequest.getHomeTeamGoals(),
                saveBetRequest.getAwayTeamGoals(),
                idGenerator.generateBetId(saveBetRequest.getUserId())
        );
    }
}
