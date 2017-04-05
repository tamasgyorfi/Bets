package hu.bets;

import hu.bets.data.DataSourceHolder;
import hu.bets.data.FootballDAO;
import hu.bets.service.FootballBetService;
import hu.bets.service.IdService;
import hu.bets.service.ModelConverterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
