package hu.bets.config;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import hu.bets.common.config.CommonMongoConfig;
import hu.bets.dbaccess.filter.*;
import hu.bets.model.processor.*;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

@Configuration
@Import(CommonMongoConfig.class)
public class DatabaseConfig {

    @Bean
    @Qualifier("mongoDBName")
    public String mongoDbName() {
        return "heroku_k3d7xpgj";
    }

    @Bean
    public MongoCollection<Document> betsCollection(MongoDatabase mongoDatabase) {
        return mongoDatabase.getCollection("Bets");
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
