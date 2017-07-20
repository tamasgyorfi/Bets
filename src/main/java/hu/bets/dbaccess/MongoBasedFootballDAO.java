package hu.bets.dbaccess;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import hu.bets.model.data.Bet;
import hu.bets.model.data.UserBet;
import hu.bets.util.Json;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class MongoBasedFootballDAO implements FootballDAO {

    private MongoCollection<Document> collection;

    public MongoBasedFootballDAO(MongoCollection<Document> collection) {
        this.collection = collection;
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
