package hu.bets.model.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Minimalistic view on a user's guess. It incorporates a match ID and the goals for the two sides.
 */
public class Bet {

    private final String matchId;
    private final String betId;
    private final int homeTeamGoals;
    private final int awayTeamGoals;

    @JsonCreator
    public Bet(@JsonProperty("matchId") String matchId,
               @JsonProperty("betId") String betId,
               @JsonProperty("homeTeamGoals") int homeTeamGoals,
               @JsonProperty("awayTeamGoals") int awayTeamGoals) {
        this.matchId = matchId;
        this.betId = betId;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public String getMatchId() {
        return matchId;
    }

    public String getBetId() {
        return betId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bet bet = (Bet) o;

        if (homeTeamGoals != bet.homeTeamGoals) return false;
        if (awayTeamGoals != bet.awayTeamGoals) return false;
        if (matchId != null ? !matchId.equals(bet.matchId) : bet.matchId != null) return false;
        return betId != null ? betId.equals(bet.betId) : bet.betId == null;
    }

    @Override
    public int hashCode() {
        int result = matchId != null ? matchId.hashCode() : 0;
        result = 31 * result + (betId != null ? betId.hashCode() : 0);
        result = 31 * result + (int) homeTeamGoals;
        result = 31 * result + (int) awayTeamGoals;
        return result;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "matchId='" + matchId + '\'' +
                ", betId='" + betId + '\'' +
                ", homeTeamGoals=" + homeTeamGoals +
                ", awayTeamGoals=" + awayTeamGoals +
                '}';
    }
}
