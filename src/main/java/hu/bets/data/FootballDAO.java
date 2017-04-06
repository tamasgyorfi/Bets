package hu.bets.data;

import hu.bets.model.data.UserBet;

/**
 * Handles database calls.
 */
public interface FootballDAO {

    /**
     * Saves the data to the database.
     *
     * @param betId
     * @param bet
     * @return
     */
    String save(String betId, UserBet bet);
}
