package hu.bets.config;

import hu.bets.dbaccess.DataSourceHolder;
import hu.bets.dbaccess.MongoDataSourceHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    @Bean
    public DataSourceHolder dataSourceHolder() {
        return new MongoDataSourceHolder();
    }
}
