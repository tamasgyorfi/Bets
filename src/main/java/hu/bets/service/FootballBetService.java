package hu.bets.service;

import hu.bets.model.data.Bet;
import hu.bets.model.data.UserBet;
import hu.bets.model.filter.Filter;
import hu.bets.web.model.SaveBetRequest;

import java.util.List;

/**
 * Handles communication from and to the WEB API.
 */
public interface FootballBetService {

    /**
     * Saves a number of bets to the persistent storage.
     *
     * @param saveBetRequest
     * @return the ID of the new;y saved bet
     * @throws BetSaveException
     */
    void saveBet(SaveBetRequest saveBetRequest) throws BetSaveException;

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

    /**
     * Retrieves userBets for a number of IDs and returns a map of the resulting values.
     *
     * @param userId the user for which we need to extract bets
     * @param ids    match ids
     * @return user bets corresponding to the ids received as param
     */
    List<Bet> getBetsFor(String userId, List<String> ids);

    /**
     * Retrieves userBets for a number of filters.
     *
     *
     * @param userId the user's id
     * @param filters the filters to apply for the data query
     * @return user bets satisfying the filter conditions (all filters are connected by AND semantic)
     */
    List<Bet> getBetsForFilter(String userId, List<Filter> filters);
}
