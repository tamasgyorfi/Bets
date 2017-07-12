package hu.bets.dbaccess;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import hu.bets.model.data.UserBet;
import hu.bets.model.data.Result;
import hu.bets.util.Json;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MongoBasedFootballDAO implements FootballDAO {

    private MongoCollection<Document> collection;

    public MongoBasedFootballDAO(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    @Override
    public String save(UserBet bet) {

        Json json = new Json();
        String jsonBet = json.toJson(bet);

        collection.insertOne(Document.parse(jsonBet));

        return bet.getBetId();
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
    public List<Result> getBetsFor(String userId, List<String> ids) {

        Bson selectQuery = Filters.and(Filters.in("matchId", ids),
                Filters.eq("userId", userId));
        FindIterable<Document> documents = collection.find(selectQuery);
        List<Result> bets = new ArrayList<>();

        documents.forEach((Consumer<Document>) document -> {
            UserBet userBet = new Json().fromJson(document.toJson(), UserBet.class);
            bets.add(new Result(userBet.getMatchId(), userBet.getHomeTeamGoals(), userBet.getAwayTeamGoals()));
        });

        return bets;

    }
}
