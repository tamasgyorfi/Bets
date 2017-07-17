package hu.bets.model.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBet {

    private String userId;

    @JsonUnwrapped
    private Match match;
    @JsonUnwrapped
    private Bet bet;

    private LocalDateTime eventReceived;
    private boolean acknowledged;

    @JsonCreator
    public UserBet(@JsonProperty("userId") String userId,
                   @JsonProperty("match") Match match,
                   @JsonProperty("bet") Bet bet) {
        this.userId = userId;
        this.match = match;
        this.bet = bet;
        this.eventReceived = LocalDateTime.now();
    }

    public String getUserId() {
        return userId;
    }

    public Match getMatch() {
        return match;
    }

    public Bet getBet() {
        return bet;
    }

    public LocalDateTime getEventReceived() {
        return eventReceived;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    @Override
    public String toString() {
        return "UserBet{" +
                "userId='" + userId + '\'' +
                ", match='" + match + '\'' +
                ", bet='" + bet + '\'' +
                ", eventReceived=" + eventReceived +
                ", acknowledged=" + acknowledged +
                '}';
    }
}
