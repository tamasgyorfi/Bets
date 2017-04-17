package hu.bets.service;

import hu.bets.model.data.UserBet;
import hu.bets.web.model.Bet;

import java.util.List;

/**
 * Handles communication from and to the WEB API.
 */
public interface FootballBetService {

    /**
     * Saves a bet to the persistent storage.
     *
     * @param bet
     * @return the ID of the new;y saved bet
     * @throws BetSaveException
     */
    String saveBet(Bet bet) throws BetSaveException;

    /**
     * Finds the bets for the matches referenced by betAggregationRequest
     *
     * @param matchIds
     * @return all bets related to the match IDs supplied, in batches
     */
    List<List<UserBet>> getBetsForMatches(List<String> matchIds);

    /**
     * Acknowledges all the matches as processed
     *
     * @param matchIds
     * @return the user bets which were successfully acknowledged
     */
    List<String> acknowledgeAll(List<String> matchIds);
}
