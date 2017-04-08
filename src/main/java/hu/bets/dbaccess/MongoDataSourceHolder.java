package hu.bets.dbaccess;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MongoDataSourceHolder implements DataSourceHolder{

    private static final MongoCollection<Document> COLLECTION = DbConfiguration.getMongoClient();

    @Override
    public MongoCollection<Document> getCollection() {
        return  COLLECTION;
    }

}
