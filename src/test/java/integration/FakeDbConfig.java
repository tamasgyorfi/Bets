package integration;

import com.github.fakemongo.Fongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import hu.bets.dbaccess.DataSourceHolder;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakeDbConfig {

    private static final FakeDataSourceHolder FAKE_DATA_SOURCE_HOLDER = new FakeDataSourceHolder();

    @Bean
    public DataSourceHolder dataSourceHolder() {
        return FAKE_DATA_SOURCE_HOLDER;
    }

    static class FakeDataSourceHolder implements DataSourceHolder {

        private final Fongo fongo = new Fongo("mongo server 1");

        @Override
        public MongoCollection<Document> getCollection() {
            MongoDatabase database = fongo.getDatabase("heroku_k3d7xpgj");
            return database.getCollection("Bets");
        }
    }

}
