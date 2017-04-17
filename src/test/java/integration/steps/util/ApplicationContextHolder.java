package integration.steps.util;

import hu.bets.config.ApplicationConfig;
import hu.bets.config.MessagingConfig;
import hu.bets.config.WebConfig;
import hu.bets.service.IdGenerator;
import hu.bets.service.UuidIdGenerator;
import integration.FakeDbConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class ApplicationContextHolder {
    private static AnnotationConfigApplicationContext context;

    public static void startApplicationContext() {
        context = new AnnotationConfigApplicationContext(
                FakeApplicationConfig.class,
                WebConfig.class,
                FakeDbConfig.class,
                MessagingConfig.class);
    }

    public static void stopApplicationContext() {
        context.stop();
    }

    public static <T> T getBean(Class<T> classType) {
        return context.getBean(classType);
    }

    static class FakeApplicationConfig extends ApplicationConfig {
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
}
