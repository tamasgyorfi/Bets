package hu.bets.config;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import hu.bets.common.config.CommonMongoConfig;
import hu.bets.dbaccess.filter.*;
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
