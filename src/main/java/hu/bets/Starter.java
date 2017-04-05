package hu.bets;

import hu.bets.web.api.FootballBetResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.InetSocketAddress;

public class Starter {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Starter server = new Starter();
        server.startServer();
    }

    private void startServer() {

        Server server = new Server(new InetSocketAddress(getHostName(), Integer.parseInt(System.getenv("PORT"))));
        server.setHandler(createContextHandler());

        try {
            server.start();
            server.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    private String getHostName() {
        String host = System.getenv("HOST");
        return host == null ? "0.0.0.0" : host;
    }

    private ResourceConfig createResourceConfig() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages(FootballBetResource.class.getPackage().getName());
        resourceConfig.register(JacksonFeature.class);

        return resourceConfig;
    }

    private ServletContextHandler createContextHandler() {

        ServletContainer servletContainer = new ServletContainer(createResourceConfig());
        ServletHolder sh = new ServletHolder(servletContainer);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(sh, "/*");

        return context;
    }
}
