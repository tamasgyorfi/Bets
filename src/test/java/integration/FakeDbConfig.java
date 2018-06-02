package integration;

import com.github.fakemongo.Fongo;
import com.mongodb.client.MongoCollection;
import hu.bets.dbaccess.filter.*;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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

    @Bean
    public FilterHandler filterHandler(List<FilterProcessor> processorList) {
        return new FilterHandler(processorList);
    }

    @Bean
    public DbFieldTranslator dbFieldTranslator() {
        return new DbFieldTranslator();
    }

    @Bean
    public EqualsFilterProcessor equalsFilterProcessor(DbFieldTranslator dbFieldTranslator){
        return new EqualsFilterProcessor(dbFieldTranslator);
    }

    @Bean
    public MultiEqualsFilterProcessor multiEqualsFilterProcessor(DbFieldTranslator dbFieldTranslator){
        return new MultiEqualsFilterProcessor(dbFieldTranslator);
    }

    @Bean
    public RangeFilterProcessor rangeFilterProcessor(DbFieldTranslator dbFieldTranslator){
        return new RangeFilterProcessor(dbFieldTranslator);
    }

}
