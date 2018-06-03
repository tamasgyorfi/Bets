package hu.bets.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BetRequest {
    private final String competitionId;
    private final String matchId;
    private final String betId;
    private final String homeTeamId;
    private final String awayTeamId;
    private final int homeTeamGoals;
    private final int awayTeamGoals;
    private final String matchDate;

    public BetRequest(@JsonProperty("competitionId") String competitionId,
                      @JsonProperty("matchId") String matchId,
                      @JsonProperty("matchDate") String matchDate,
                      @JsonProperty("betId") String betId,
                      @JsonProperty("homeTeamId") String homeTeamId,
                      @JsonProperty("awayTeamId") String awayTeamId,
                      @JsonProperty("homeTeamGoals") int homeTeamGoals,
                      @JsonProperty("awayTeamGoals") int awayTeamGoals) {
        this.competitionId = competitionId;
        this.matchId = matchId;
        this.matchDate = matchDate;
        this.betId = betId;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
    }

    public String getCompetitionId() {
        return competitionId;
    }

    public String getMatchId() {
        return matchId;
    }

    public String getBetId() {
        return betId;
    }

    public String getHomeTeamId() {
        return homeTeamId;
    }

    public String getAwayTeamId() {
        return awayTeamId;
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public String getMatchDate() {
        return matchDate;
    }
}
