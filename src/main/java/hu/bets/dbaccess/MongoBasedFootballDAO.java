package hu.bets.dbaccess;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import hu.bets.dbaccess.filter.FilterHandler;
import hu.bets.model.data.Bet;
import hu.bets.model.data.UserBet;
import hu.bets.model.filter.Filter;
import hu.bets.util.Json;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class MongoBasedFootballDAO implements FootballDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoBasedFootballDAO.class);

    private MongoCollection<Document> collection;
    private FilterHandler filterHandler;

    public MongoBasedFootballDAO(MongoCollection<Document> collection, FilterHandler filterHandler) {
        this.collection = collection;
        this.filterHandler = filterHandler;
    }

    @Override
    public String save(UserBet bet) {
        Optional<Document> betFromDb = getBetFromDb(bet);
        return betFromDb.map(document -> update(bet, document)).orElseGet(() -> create(bet));
    }

    private String create(UserBet bet) {
        String jsonBet = new Json().toJson(bet);

        collection.insertOne(Document.parse(jsonBet));
        return bet.getBet().getBetId();
    }

    @Override
    public List<UserBet> getBetsForMatches(List<String> matchIds) {

        Bson matchIdQuery = Filters.in("matchId", matchIds);
        FindIterable<Document> documents = collection.find(matchIdQuery);
        List<UserBet> bets = new ArrayList<>();

        documents.forEach((Consumer<Document>) document -> bets.add(new Json().fromJson(document.toJson(), UserBet.class)));

        return bets;
    }

    @Override
    public boolean acknowledge(String betId) {
        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.append("$set", new BasicDBObject().append("acknowledged", true));

        BasicDBObject searchQuery = new BasicDBObject("betId", betId);

        UpdateResult result = collection.updateOne(searchQuery, updateQuery);
        return result.wasAcknowledged();
    }

    @Override
    public List<Bet> getBetsFor(String userId, List<String> ids) {

        Bson selectQuery = Filters.and(Filters.in("matchId", ids),
                Filters.eq("userId", userId));
        FindIterable<Document> documents = collection.find(selectQuery);
        List<Bet> bets = new ArrayList<>();

        documents.forEach((Consumer<Document>) document -> {
            UserBet userBet = new Json().fromJson(document.toJson(), UserBet.class);
            bets.add(userBet.getBet());
        });

        return bets;

    }

    @Override
    public List<Bet> getBetsForFilter(List<Filter> filters) {

        String query = filterHandler.processAll(filters);

        LOGGER.info("Executing query: {}", query);
        Document document = Document.parse(query);

        FindIterable<Document> documents = collection.find(document);
        List<Bet> bets = new ArrayList<>();

        documents.forEach((Consumer<Document>) doc -> {
            UserBet userBet = new Json().fromJson(doc.toJson(), UserBet.class);
            bets.add(userBet.getBet());
        });

        LOGGER.info("Found the following bets matching the query {} : {}", query, bets);
        return bets;
    }

    private String update(UserBet bet, Document oldBet) {
        BasicDBObject newObject = new BasicDBObject("$set", new BasicDBObject()
                .append("homeTeamGoals", bet.getBet().getHomeTeamGoals())
                .append("awayTeamGoals", bet.getBet().getAwayTeamGoals()));
        Bson betQuery = getUpdateFilterQuery(bet);

        collection.updateOne(betQuery, newObject);
        return bet.getBet().getBetId();
    }

    private Optional<Document> getBetFromDb(UserBet bet) {
        Bson query = getUpdateFilterQuery(bet);
        return Optional.ofNullable(collection.find(query).first());
    }

    private Bson getUpdateFilterQuery(UserBet bet) {
        return Filters.and(
                Filters.eq("userId", bet.getUserId()),
                Filters.eq("matchId", bet.getMatch().getMatchId()));

    }
}
