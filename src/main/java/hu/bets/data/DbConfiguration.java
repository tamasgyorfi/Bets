package hu.bets.data;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

public class DbConfiguration {

    private static final String DB_URI_KEY = "MONGODB_URI";
    private static final String DATABASE_NAME = "heroku_k3d7xpgj";
    private static final String COLLECTION_NAME = "Bets";

    protected static MongoCollection<Document> getMongoClient() {
        String dbUri = System.getenv(DB_URI_KEY);

        MongoClientURI clientURI = new MongoClientURI(dbUri);
        MongoClient client = new MongoClient(clientURI);

        MongoDatabase database = client.getDatabase(DATABASE_NAME);
        return database.getCollection(COLLECTION_NAME);
    }

}
