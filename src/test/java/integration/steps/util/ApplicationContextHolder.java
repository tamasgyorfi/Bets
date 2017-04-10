package integration.steps.util;

import hu.bets.config.ApplicationConfig;
import hu.bets.config.MessagingConfig;
import hu.bets.config.WebConfig;
import integration.FakeDbConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextHolder {
    private static AnnotationConfigApplicationContext context;

    public static void startApplicationContext() {
        context = new AnnotationConfigApplicationContext(ApplicationConfig.class,
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
}
