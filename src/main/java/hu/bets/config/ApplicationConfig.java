package hu.bets.config;

import hu.bets.data.DataSourceHolder;
import hu.bets.data.FootballDAO;
import hu.bets.data.MongoBasedFootballDAO;
import hu.bets.service.DefaultFootballBetService;
import hu.bets.service.FootballBetService;
import hu.bets.service.IdGenerator;
import hu.bets.web.api.FootballBetResource;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
public class ApplicationConfig {

    private static final String WEB_SERVER_HOST = "HOST";
    private static final String WEB_SERVICE_PORT = "PORT";

    private static final Logger LOGGER = Logger.getLogger(ApplicationConfig.class);

    @Bean
    public IdGenerator idService() {
        return new IdGenerator();
    }

    @Bean
    public DataSourceHolder dataSourceHolder() {
        return new DataSourceHolder();
    }

    @Bean
    public FootballDAO footballDAO(DataSourceHolder dataSourceHolder) {
        return new MongoBasedFootballDAO(dataSourceHolder);
    }

    @Bean
    public FootballBetService betService(IdGenerator idService, FootballDAO footballDAO) {
        return new DefaultFootballBetService(idService, footballDAO);
    }

    @Bean
    public FootballBetResource footballBetResource() {
        return new FootballBetResource();
    }

    @Bean
    public Server server(ServletContextHandler servletContextHandler) {

        Server server = new Server(new InetSocketAddress(getHost(), Integer.parseInt(System.getenv("PORT"))));
        server.setHandler(servletContextHandler);

        return server;
    }

    private String getHost() {
        String host = System.getenv("HOST");
        return host == null ? "0.0.0.0" : host;
    }

    @Bean
    public ResourceConfig resourceConfig(FootballBetResource footballResource) {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(footballResource);
        resourceConfig.register(JacksonFeature.class);

        return resourceConfig;
    }

    @Bean
    public ServletContextHandler servletContextHandler(ServletContainer servletContainer) {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ServletHolder sh = new ServletHolder(servletContainer);

        context.setContextPath("/");
        context.addServlet(sh, "/*");

        return context;
    }

    @Bean
    public ServletContainer servletContainer(ResourceConfig resourceConfig) {
        return new ServletContainer(resourceConfig);
    }
}
