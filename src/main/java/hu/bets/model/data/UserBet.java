package hu.bets.model.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBet {

    private String userId;

    private String competitionId;
    private String matchId;
    private String homeTeamId;
    private String awayTeamId;

    private byte homeTeamGoals;
    private byte awayTeamGoals;

    private LocalDateTime eventReceived;
    private String betId;
    private boolean acknowledged;

    @JsonCreator
    public UserBet(@JsonProperty("userId") String userId,
                   @JsonProperty("competitionId")String competitionId,
                   @JsonProperty("matchId")String matchId,
                   @JsonProperty("homeTeamId")String homeTeamId,
                   @JsonProperty("awayTeamId")String awayTeamId,
                   @JsonProperty("homeTeamGoals")byte homeTeamGoals,
                   @JsonProperty("awayTeamGoals")byte awayTeamGoals,
                   @JsonProperty("betId")String betId) {
        this.userId = userId;
        this.competitionId = competitionId;
        this.matchId = matchId;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
        this.betId = betId;
        this.eventReceived = LocalDateTime.now();
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

    public boolean isAcknowledged() {
        return acknowledged;
    }

    @Override
    public String toString() {
        return "UserBet{" +
                "userId='" + userId + '\'' +
                ", competitionId='" + competitionId + '\'' +
                ", matchId='" + matchId + '\'' +
                ", homeTeamId='" + homeTeamId + '\'' +
                ", awayTeamId='" + awayTeamId + '\'' +
                ", homeTeamGoals=" + homeTeamGoals +
                ", awayTeamGoals=" + awayTeamGoals +
                ", eventReceived=" + eventReceived +
                ", betId='" + betId + '\'' +
                ", acknowledged=" + acknowledged +
                '}';
    }
}
