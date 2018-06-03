package hu.bets.model.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Match {

    private transient String matchId;
    private String matchDate;
    private String competitionId;
    private String homeTeamId;
    private String awayTeamId;

    @JsonCreator
    public Match(@JsonProperty("matchId") String matchId,
                 @JsonProperty("matchDate") String matchDate,
                 @JsonProperty("competitionId") String competitionId,
                 @JsonProperty("homeTeamId") String homeTeamId,
                 @JsonProperty("awayTeamId") String awayTeamId) {
        this.matchId = matchId;
        this.matchDate = matchDate;
        this.competitionId = competitionId;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
    }

    @JsonIgnore
    public String getMatchId() {
        return matchId;
    }

    public String getCompetitionId() {
        return competitionId;
    }

    public String getHomeTeamId() {
        return homeTeamId;
    }

    public String getAwayTeamId() {
        return awayTeamId;
    }

    public String getMatchDate() {
        return matchDate;
    }

    @Override
    public String toString() {
        return "Match{" +
                "matchId='" + matchId + '\'' +
                ", matchDate='" + matchDate + '\'' +
                ", competitionId='" + competitionId + '\'' +
                ", homeTeamId='" + homeTeamId + '\'' +
                ", awayTeamId='" + awayTeamId + '\'' +
                '}';
    }
}
