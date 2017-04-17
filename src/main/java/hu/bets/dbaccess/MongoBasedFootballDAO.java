package hu.bets.dbaccess;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import hu.bets.model.data.UserBet;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MongoBasedFootballDAO implements FootballDAO {

    private DataSourceHolder dataSourceHolder;

    public MongoBasedFootballDAO(DataSourceHolder dataSourceHolder) {
        this.dataSourceHolder = dataSourceHolder;
    }

    @Override
    public String save(UserBet bet) {
        MongoCollection collection = dataSourceHolder.getCollection();

        Gson gson = new Gson();
        String jsonBet = gson.toJson(bet);

        collection.insertOne(Document.parse(jsonBet));

        return bet.getBetId();
    }

    @Override
    public List<UserBet> getBetsForMatches(List<String> matchIds) {

        Bson matchIdQuery = Filters.in("matchId", matchIds);
        FindIterable<Document> documents = dataSourceHolder.getCollection().find(matchIdQuery);
        List<UserBet> bets = new ArrayList<>();

        documents.forEach((Consumer<Document>) document-> bets.add(new Gson().fromJson(document.toJson(), UserBet.class)));

        return bets;
    }

    @Override
    public boolean acknowledge(String betId) {
        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.append("$set", new BasicDBObject().append("acknowledged", true));

        BasicDBObject searchQuery = new BasicDBObject("betId", betId);

        UpdateResult result = dataSourceHolder.getCollection().updateOne(searchQuery, updateQuery);
        return result.wasAcknowledged();
    }
}
