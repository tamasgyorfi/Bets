package hu.bets;

import hu.bets.config.ApplicationConfig;
import hu.bets.config.MessagingConfig;
import hu.bets.messaging.BetAggregationRequestListener;
import hu.bets.messaging.MessagingConstants;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Starter {

    private static final Logger LOGGER = Logger.getLogger(Starter.class);

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class, MessagingConfig.class);
        Server server = context.getBean(Server.class);
        BetAggregationRequestListener betAggregationRequestListener = context.getBean(BetAggregationRequestListener.class);


        Starter starter = new Starter();
        starter.startMessaging(betAggregationRequestListener);
        starter.startServer(server);
    }

    private void startMessaging(BetAggregationRequestListener betAggregationRequestListener) {
        try {
            betAggregationRequestListener.receive(MessagingConstants.AGGREGATE_REQUEST_QUEUE_NAME);
        } catch (IOException | TimeoutException e) {
            LOGGER.error(e);
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