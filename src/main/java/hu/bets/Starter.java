package hu.bets;

import hu.bets.common.messaging.MessageListener;
import hu.bets.config.ApplicationConfig;
import hu.bets.config.DatabaseConfig;
import hu.bets.config.MessagingConfig;
import hu.bets.config.WebConfig;
import hu.bets.servicediscovery.EurekaFacade;
import hu.bets.services.Services;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Starter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Starter.class);

    private ApplicationContext context = new AnnotationConfigApplicationContext(
            ApplicationConfig.class,
            MessagingConfig.class,
            WebConfig.class,
            DatabaseConfig.class);

    public static void main(String[] args) {
        Starter starter = new Starter();

        starter.registerForDiscovery(starter.context.getBean(EurekaFacade.class));
        addShutDownHook(starter.context);

        starter.startMessaging(starter.context.getBean(MessageListener.class));
        starter.startServer(starter.context.getBean(Server.class));
    }

    private static void addShutDownHook(ApplicationContext context) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> context.getBean(EurekaFacade.class).unregister()));
    }

    private void registerForDiscovery(EurekaFacade eurekaFacade) {
        eurekaFacade.registerNonBlockingly(Services.BETS.getServiceName());
    }

    private void startMessaging(MessageListener messageListener) {
        try {
            messageListener.receive();
        } catch (Exception e) {
            LOGGER.error("Unable to start messaging. ", e);
        }
    }

    private void startServer(Server server) {
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            LOGGER.error("Unable to start the embedded server.", e);
        }
    }
}