package hu.bets.service;

import com.google.common.collect.Lists;
import hu.bets.dbaccess.FootballDAO;
import hu.bets.model.data.BetConverter;
import hu.bets.model.data.UserBet;
import hu.bets.web.model.Bet;

import java.util.ArrayList;
import java.util.List;

public class DefaultFootballBetService implements FootballBetService {

    private static final int BATCH_SIZE = 100;

    private final FootballDAO footballDAO;
    private BetConverter betConverter;

    public DefaultFootballBetService(FootballDAO footballDAO, BetConverter betConverter) {
        this.footballDAO = footballDAO;
        this.betConverter = betConverter;
    }

    @Override
    public String saveBet(Bet bet) throws BetSaveException {
        return footballDAO.save(betConverter.fromBet(bet));
    }

    @Override
    public List<List<UserBet>> getBetsForMatches(List<String> matchIds) {
        List<UserBet> betsForMatches = footballDAO.getBetsForMatches(matchIds);
        return Lists.partition(betsForMatches, BATCH_SIZE);
    }

    @Override
    public List<String> acknowledgeAll(List<String> matchIds) {
        List<String> acknowledgedIds = new ArrayList<>(matchIds.size());
        for (String matchId: matchIds) {
            boolean acknowledged = footballDAO.acknowledge(matchId);
            if (acknowledged) {
                acknowledgedIds.add(matchId);
            }
        }

        return  acknowledgedIds;
    }
}
