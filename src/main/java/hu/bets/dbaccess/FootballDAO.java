package hu.bets.dbaccess;

import hu.bets.model.data.UserBet;

import java.util.List;

/**
 * Handles database calls.
 */
public interface FootballDAO {

    /**
     * Saves the data to the database.
     *
     * @param bet
     * @return the id for this UserBet
     */
    String save(UserBet bet);

    /**
     * Gets all bets for the specific match IDs
     *
     * @param matchIds
     * @return all user bets related to any of the maches specified in matchIDs
     */
    List<UserBet> getBetsForMatches(List<String> matchIds);

    /**
     * Acknowledges a previously non-acknowledged game.
     *
     * @param betId
     */
    boolean acknowledge(String betId);
}
