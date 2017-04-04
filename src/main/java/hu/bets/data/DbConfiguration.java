package hu.bets.data;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DbConfiguration {

    private static final String DB_URI_KEY = "MONGODB_URI";
    private static final String DATABASE_NAME = "Football";
    private static final String COLLECTION_NAME = "Bets";

    protected static MongoCollection<Document> getMongoClient() {
        String dbUri = System.getenv(DB_URI_KEY);

        MongoClient client = new MongoClient(dbUri);
        MongoDatabase database = client.getDatabase(DATABASE_NAME);
        return database.getCollection(COLLECTION_NAME);
    }

}
