package integration;

import hu.bets.config.ApplicationConfig;
import hu.bets.service.IdGenerator;
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
