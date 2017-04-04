package hu.bets.web.model;

public class Bet {

    private final String userId;

    private final String competitionId;
    private final String matchId;
    private final String homeTeamId;
    private final String awayTeamId;

    private final byte homeTeamGoals;
    private final byte awayTeamGoals;

    public Bet(String userId, String competitionId, String matchId, String homeTeamId, String awayTeamId, byte homeTeamGoals, byte awayTeamGoals) {
        this.userId = userId;
        this.competitionId = competitionId;
        this.matchId = matchId;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
    }

    private Bet() {
        this.userId = "";
        this.competitionId = "";
        this.matchId = "";
        this.homeTeamId = "";
        this.awayTeamId = "";
        this.homeTeamGoals = -1;
        this.awayTeamGoals = -1;
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
}
