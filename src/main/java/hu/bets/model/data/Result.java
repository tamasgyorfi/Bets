package hu.bets.model.data;

public class Result {

    private final String matchId;
    private final byte homeTeamGoals;
    private final byte awayTeamGoals;

    public Result(String matchId, byte homeTeamGoals, byte awayTeamGoals) {
        this.matchId = matchId;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
    }

    public byte getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public byte getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public String getMatchId() {
        return matchId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result = (Result) o;

        if (homeTeamGoals != result.homeTeamGoals) return false;
        if (awayTeamGoals != result.awayTeamGoals) return false;
        return matchId != null ? matchId.equals(result.matchId) : result.matchId == null;
    }

    @Override
    public int hashCode() {
        int result = matchId != null ? matchId.hashCode() : 0;
        result = 31 * result + (int) homeTeamGoals;
        result = 31 * result + (int) awayTeamGoals;
        return result;
    }
}
