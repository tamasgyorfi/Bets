package integration.steps;

import com.mongodb.client.MongoCollection;
import com.rabbitmq.client.Channel;
import hu.bets.common.messaging.MessageListener;
import integration.steps.util.ApplicationContextHolder;
import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;
import org.eclipse.jetty.server.Server;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static hu.bets.messaging.MessagingConstants.AGGREGATE_REPLY_QUEUE_NAME;
import static hu.bets.messaging.MessagingConstants.AGGREGATE_REQUEST_QUEUE_NAME;
import static integration.Constants.HOST;
import static integration.Constants.PORT;

public class Given {

    private static final Broker BROKER = new Broker();
    private static Server webServer;


    public static MongoCollection aDataSource() {
        return ApplicationContextHolder.getBean(MongoCollection.class);
    }

    public static void environmentIsUpAndRunning() throws Exception {
        setupBroker();
        setEnvironment();
        ApplicationContextHolder.startApplicationContext();
        setupQueues();
        startListening();
        startServer();
    }

    public static void environmentIsShutDown() throws Exception {
        ApplicationContextHolder.getBean(MongoCollection.class).drop();
        ApplicationContextHolder.stopApplicationContext();

        BROKER.shutdown();
        webServer.stop();

        TimeUnit.SECONDS.sleep(1);
    }

    private static void startListening() throws Exception {
        MessageListener messageListener = ApplicationContextHolder.getBean(MessageListener.class);

        messageListener.receive();
    }

    private static void setupQueues() throws Exception {
        Channel channel = ApplicationContextHolder.getBean(Channel.class);

        channel.queueDeclare(AGGREGATE_REQUEST_QUEUE_NAME, true, false, false, null);
        channel.queueDeclare(AGGREGATE_REPLY_QUEUE_NAME, true, false, false, null);
    }

    private static void setupBroker() {

        String config = Thread.currentThread().getContextClassLoader().getResource("amqp_config.json").getPath();
        String keystore = Thread.currentThread().getContextClassLoader().getResource("clientkeystore").getPath();

        final BrokerOptions brokerOptions = new BrokerOptions();
        brokerOptions.setConfigProperty("qpid.amqp_port", "11000");
        brokerOptions.setConfigProperty("store.uri", keystore);
        brokerOptions.setInitialConfigurationLocation(config);

        try {
            BROKER.startup(brokerOptions);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static void startServer() {
        webServer = ApplicationContextHolder.getBean(Server.class);
        new Thread(() -> {
            try {
                webServer.start();
                webServer.join();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }).start();
    }

    private static void setEnvironment() {
        Properties properties = System.getProperties();
        properties.setProperty("HOST", HOST);
        properties.setProperty("PORT", PORT);
        properties.setProperty("CLOUDAMQP_URL", "amqp://guest:guest@localhost:11000");
    }
}
