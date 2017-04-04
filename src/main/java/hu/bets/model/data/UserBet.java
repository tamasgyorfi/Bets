package hu.bets.model.data;

import hu.bets.service.IdService;

import java.time.LocalDateTime;

public class UserBet {

    private  String userId;

    private String competitionId;
    private String matchId;
    private String homeTeamId;
    private String awayTeamId;

    private byte homeTeamGoals;
    private byte awayTeamGoals;

    private LocalDateTime eventReceived;
    private String betId;

    public UserBet(String userId, String competitionId, String matchId, String homeTeamId, String awayTeamId, byte homeTeamGoals, byte awayTeamGoals) {
        this.userId = userId;
        this.competitionId = competitionId;
        this.matchId = matchId;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
        this.eventReceived = LocalDateTime.now();
        this.betId = new IdService().generateBetId(userId);
    }


    public String getUserId() {
        return userId;
    }

    public String getCompetitionId() {
        return competitionId;
    }

    public String getMatchId() {
        return matchId;
    }

    public String getHomeTeamId() {
        return homeTeamId;
    }

    public String getAwayTeamId() {
        return awayTeamId;
    }

    public byte getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public byte getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public LocalDateTime getEventReceived() {
        return eventReceived;
    }

    public String getBetId() {
        return betId;
    }
}
