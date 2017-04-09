package integration.steps;

import hu.bets.config.ApplicationConfig;
import hu.bets.config.WebConfig;
import integration.FakeDbConfig;
import org.eclipse.jetty.server.ClassLoaderDump;
import org.eclipse.jetty.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Properties;

import static integration.Constants.HOST;
import static integration.Constants.PORT;

public class Given {

    private static ApplicationContext context;

    public static void theWebServerIsUp() {
        startServer();
    }

    public static ApplicationContext theContextIsInitialized() {
        setEnvironment();
        context = new AnnotationConfigApplicationContext(ApplicationConfig.class, WebConfig.class, FakeDbConfig.class);
        return context;
    }

    public static <T> T aBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    private static void startServer() {
        final Server server = context.getBean(Server.class);

        new Thread(() -> {
            try {
                server.start();
                server.join();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }).start();
    }

    private static void setEnvironment() {
        Properties properties = System.getProperties();
        properties.setProperty("HOST", HOST);
        properties.setProperty("PORT", PORT);
    }
}
