package hu.bets.dbaccess;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public interface DataSourceHolder {
    MongoCollection<Document> getCollection();
}
