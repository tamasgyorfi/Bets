package hu.bets.service;

import com.google.common.collect.Lists;
import hu.bets.dbaccess.FootballDAO;
import hu.bets.model.data.BetConverter;
import hu.bets.model.data.Result;
import hu.bets.model.data.UserBet;
import hu.bets.web.model.SaveBetRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DefaultFootballBetService implements FootballBetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultFootballBetService.class);
    private static final int BATCH_SIZE = 100;

    private final FootballDAO footballDAO;
    private BetConverter betConverter;

    public DefaultFootballBetService(FootballDAO footballDAO, BetConverter betConverter) {
        this.footballDAO = footballDAO;
        this.betConverter = betConverter;
    }

    @Override
    public String saveBet(SaveBetRequest saveBetRequest) throws BetSaveException {
        return footballDAO.save(betConverter.fromBet(saveBetRequest));
    }

    @Override
    public List<List<UserBet>> getBetsForMatches(List<String> matchIds) {
        List<UserBet> betsForMatches = footballDAO.getBetsForMatches(matchIds);
        LOGGER.info("Bets for " + matchIds + " : " + betsForMatches);
        return Lists.partition(betsForMatches, BATCH_SIZE);
    }

    @Override
    public List<String> acknowledgeAll(List<String> matchIds) {
        List<String> acknowledgedIds = new ArrayList<>(matchIds.size());
        for (String matchId : matchIds) {
            boolean acknowledged = footballDAO.acknowledge(matchId);
            if (acknowledged) {
                acknowledgedIds.add(matchId);
            }
        }

        return acknowledgedIds;
    }

    @Override
    public List<Result> getBetsFor(String userId, List<String> ids) {
        LOGGER.info("Querying the database for userId {} and matchIds {}", userId, ids);
        List<Result> bets = footballDAO.getBetsFor(userId, ids);
        LOGGER.info("Resulting bets are {}", bets);

        return bets;
    }
}
