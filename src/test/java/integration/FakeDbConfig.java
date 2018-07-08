package integration;

import com.github.fakemongo.Fongo;
import com.mongodb.client.MongoCollection;
import hu.bets.dbaccess.filter.*;
import hu.bets.model.processor.*;
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
    public BetsDbFieldTranslator dbFieldTranslator() {
        return new BetsDbFieldTranslator();
    }

    @Bean
    public EqualsFilterProcessor equalsFilterProcessor(BetsDbFieldTranslator betsDbFieldTranslator){
        return new EqualsFilterProcessor(betsDbFieldTranslator);
    }

    @Bean
    public MultiEqualsFilterProcessor multiEqualsFilterProcessor(BetsDbFieldTranslator betsDbFieldTranslator){
        return new MultiEqualsFilterProcessor(betsDbFieldTranslator);
    }

    @Bean
    public RangeFilterProcessor rangeFilterProcessor(BetsDbFieldTranslator betsDbFieldTranslator){
        return new RangeFilterProcessor(betsDbFieldTranslator);
    }

}
