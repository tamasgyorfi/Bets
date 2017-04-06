package hu.bets;

import hu.bets.data.DataSourceHolder;
import hu.bets.data.FootballDAO;
import hu.bets.service.FootballBetService;
import hu.bets.service.IdService;
import hu.bets.service.ModelConverterService;
import hu.bets.web.api.FootballBetResource;
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

    @Bean
    public IdService idService() {
        return new IdService();
    }

    @Bean
    public DataSourceHolder dataSourceHolder() {
        return new DataSourceHolder();
    }

    @Bean
    public FootballDAO footballDAO(DataSourceHolder dataSourceHolder) {
        return new FootballDAO(dataSourceHolder);
    }

    @Bean
    public ModelConverterService modelConverterService() {
        return new ModelConverterService();
    }

    @Bean
    public FootballBetService betService(IdService idService, FootballDAO footballDAO, ModelConverterService modelConverterService) {
        return new FootballBetService(idService, footballDAO, modelConverterService);
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
