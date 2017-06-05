package hu.bets.config;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import hu.bets.common.config.CommonMongoConfig;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CommonMongoConfig.class)
public class DatabaseConfig {

    @Bean
    @Qualifier("mongoDBName")
    public String mongoDbName() {
        return "heroku_k3d7xpgj";
    }

    @Bean
    public MongoCollection<Document> scoresCollection(MongoDatabase mongoDatabase) {
        return mongoDatabase.getCollection("heroku_k3d7xpgj");
    }
}
