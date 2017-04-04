package hu.bets.data;

import com.google.gson.Gson;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.client.MongoCollection;
import hu.bets.model.data.UserBet;

public class FootballDAO {

    private CollectionFinder collectionFinder;

    private FootballDAO(CollectionFinder collectionFinder) {
        this.collectionFinder = collectionFinder;
    }

    public String save(String betId, UserBet bet) {
        MongoCollection collection = collectionFinder.getCollection();

        BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        Gson gson = new Gson();
        String jsonBet = gson.toJson(bet);

        collection.insertOne(jsonBet);

        return bet.getBetId();
    }
}
