package hu.bets.config;

import hu.bets.common.config.CommonMongoConfig;
import hu.bets.common.config.model.MongoDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CommonMongoConfig.class)
public class DatabaseConfig {

    @Bean
    public MongoDetails mongoDetails() {
        return new MongoDetails("heroku_k3d7xpgj", "Bets");
    }
}
