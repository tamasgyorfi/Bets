package integration;

import com.github.fakemongo.Fongo;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakeDbConfig {

    private static class CollectionHolder {

        private static final MongoCollection<Document> COLLECTION = new Fongo("mongo server 1").getDatabase("heroku_k3d7xpgj").getCollection("Bets");

        public static MongoCollection<Document> getCollection() {
            return COLLECTION;
        }
    }

    @Bean
    public MongoCollection<Document> collection() {
        return CollectionHolder.getCollection();
    }
}
