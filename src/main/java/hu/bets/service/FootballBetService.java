package hu.bets.service;

import hu.bets.web.model.Bet;

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
}
