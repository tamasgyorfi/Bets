package hu.bets.data;

import com.google.gson.Gson;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.util.JSON;
import hu.bets.model.data.UserBet;
import org.bson.Document;

public class FootballDAO {

    private DataSourceHolder dataSourceHolder;

    public FootballDAO(DataSourceHolder dataSourceHolder) {
        this.dataSourceHolder = dataSourceHolder;
    }

    public String save(String betId, UserBet bet) {
        MongoCollection collection = dataSourceHolder.getCollection();

        BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        Gson gson = new Gson();
        String jsonBet = gson.toJson(bet);


        collection.insertOne(Document.parse(jsonBet));

        return bet.getBetId();
    }
}
