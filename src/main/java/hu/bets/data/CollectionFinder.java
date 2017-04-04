package hu.bets.data;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class CollectionFinder {

    private static final MongoCollection<Document> COLLECTION = DbConfiguration.getMongoClient();

    public MongoCollection<Document> getCollection() {
        return  COLLECTION;
    }

}
