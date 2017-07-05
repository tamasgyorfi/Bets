package integration;

import hu.bets.common.util.IdGenerator;
import hu.bets.common.util.servicediscovery.EurekaFacade;
import hu.bets.config.ApplicationConfig;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

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

    @Bean
    @Override
    public EurekaFacade eurekaFacade() {
        return new EurekaFacade() {
            @Override
            public void registerBlockingly(String serviceName) {

            }

            @Override
            public Future<Boolean> registerNonBlockingly(String serviceName) {
                return new FutureTask<Boolean>(() -> true);
            }

            @Override
            public String resolveEndpoint(String name) {
                return "";
            }
        };
    }
}
