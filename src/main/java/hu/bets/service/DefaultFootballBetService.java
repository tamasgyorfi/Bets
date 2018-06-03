package hu.bets.service;

import com.google.common.collect.Lists;
import hu.bets.dbaccess.FootballDAO;
import hu.bets.model.data.Bet;
import hu.bets.model.data.BetConverter;
import hu.bets.model.data.UserBet;
import hu.bets.model.filter.EqualsFilter;
import hu.bets.model.filter.Field;
import hu.bets.model.filter.Filter;
import hu.bets.web.model.BetRequest;
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
    public void saveBet(SaveBetRequest saveBetRequest) throws BetSaveException {
        for (BetRequest betRequest : saveBetRequest.getBets()) {
            footballDAO.save(betConverter.createNewUserBet(saveBetRequest.getUserId(), betRequest));
        }
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
    public List<Bet> getBetsFor(String userId, List<String> ids) {
        LOGGER.info("Querying the database for userId {} and matchIds {}", userId, ids);
        List<Bet> bets = footballDAO.getBetsFor(userId, ids);
        LOGGER.info("Resulting bets are {}", bets);

        return bets;
    }

    @Override
    public List<Bet> getBetsForFilter(String userId, List<Filter> filters) {
        LOGGER.info("Querying the database for filters {} and user {}", filters, userId);
        List<Filter> allFilters = Lists.newArrayList(filters);
        allFilters.add(new EqualsFilter(Field.USER_ID, userId));

        if (allFilters.isEmpty()) {
            throw new IllegalArgumentException("Empty filter clauses are not permitted.");
        }
        List<Bet> bets = footballDAO.getBetsForFilter(allFilters);
        LOGGER.info("Resulting bets are {}", bets);


        return bets;
    }
}
