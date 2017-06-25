package integration;

import hu.bets.common.util.IdGenerator;
import hu.bets.config.ApplicationConfig;
import org.springframework.context.annotation.Bean;

public class FakeApplicationConfig extends ApplicationConfig {
    @Bean
    @Override
    public IdGenerator idGenerator() {
        return new IdGenerator() {
            @Override
            public String generateBetId(String userId) {
                return "aa";
            }
        };
    }
}
