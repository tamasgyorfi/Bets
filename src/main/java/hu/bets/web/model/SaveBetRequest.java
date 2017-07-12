package hu.bets.web.model;

public class SaveBetRequest {

    private final String userId;

    private final String competitionId;
    private final String matchId;
    private final String homeTeamId;
    private final String awayTeamId;

    private final byte homeTeamGoals;
    private final byte awayTeamGoals;

    private final String token;

    public SaveBetRequest(String userId, String competitionId, String matchId, String homeTeamId, String awayTeamId, byte homeTeamGoals, byte awayTeamGoals, String token) {
        this.userId = userId;
        this.competitionId = competitionId;
        this.matchId = matchId;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
        this.token = token;
    }

    private SaveBetRequest() {
        this.userId = "";
        this.competitionId = "";
        this.matchId = "";
        this.homeTeamId = "";
        this.awayTeamId = "";
        this.homeTeamGoals = -1;
        this.awayTeamGoals = -1;
        this.token = "";
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
