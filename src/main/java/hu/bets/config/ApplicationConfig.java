package hu.bets.config;

import com.mongodb.client.MongoCollection;
import hu.bets.common.util.EnvironmentVarResolver;
import hu.bets.common.util.IdGenerator;
import hu.bets.common.util.UuidIdGenerator;
import hu.bets.common.util.hash.MD5HashGenerator;
import hu.bets.common.util.schema.SchemaValidator;
import hu.bets.dbaccess.FootballDAO;
import hu.bets.dbaccess.MongoBasedFootballDAO;
import hu.bets.messaging.execution.MessageExecutor;
import hu.bets.model.data.BetConverter;
import hu.bets.service.DefaultFootballBetService;
import hu.bets.service.FootballBetService;
import hu.bets.servicediscovery.EurekaFacade;
import hu.bets.servicediscovery.EurekaFacadeImpl;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

@Configuration
public class ApplicationConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

    @Bean
    public UuidIdGenerator idService() {
        return new UuidIdGenerator();
    }

    @Bean
    public FootballDAO footballDAO(MongoCollection<Document> collection) {
        return new MongoBasedFootballDAO(collection);
    }

    @Bean
    public IdGenerator idGenerator() {
        return new UuidIdGenerator();
    }

    @Bean
    public BetConverter betConverter(IdGenerator idGenerator) {
        return new BetConverter(idGenerator);
    }

    @Bean
    public FootballBetService betService(FootballDAO footballDAO, BetConverter betConverter) {
        return new DefaultFootballBetService(footballDAO, betConverter);
    }

    @Bean
    public CompletionService<List<String>> completionService() {
        Executor executor = Executors.newFixedThreadPool(10);
        return new ExecutorCompletionService<>(executor);
    }

    @Bean
    public MessageExecutor betAggregationExecutor(FootballBetService footballBetService, CompletionService<List<String>> completionService) {
        return new MessageExecutor(completionService, footballBetService, new MD5HashGenerator());
    }

    @Bean
    public SchemaValidator schemaValidator() {
        return new SchemaValidator();
    }

    @Bean
    public EurekaFacade eurekaFacade() {
        return new EurekaFacadeImpl(new EnvironmentVarResolver().getEnvVar("EUREKA_URL"));
    }
}
