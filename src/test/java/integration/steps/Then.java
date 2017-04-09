package integration.steps;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import hu.bets.dbaccess.DataSourceHolder;
import hu.bets.web.model.BetResponse;
import org.bson.Document;

import static org.junit.Assert.*;

public class Then {

    public static void theBetResponseIsOk(BetResponse betResponse) {
        assertFalse(betResponse.getId().isEmpty());
        assertTrue(betResponse.getError().isEmpty());
    }

    public static void theDatasourceContainsBet(String betId,  DataSourceHolder dataSourceHolder) {
        MongoCollection<Document> collection = dataSourceHolder.getCollection();

        assertEquals(1, collection.count());

        BasicDBObject query = new BasicDBObject();
        query.put("betId", betId);

        assertNotNull(collection.find(query).first());
    }
}
