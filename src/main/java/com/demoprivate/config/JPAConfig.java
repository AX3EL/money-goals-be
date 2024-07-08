package com.demoprivate.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.demoprivate.repository")
@EntityScan("com.demoprivate.model")
@EnableTransactionManagement
//Con EnableTransactionManagement spring crea automaticamente dei proxy
// per i bean che contengono metodi annotati con @Transactional.
// Questi proxy intercettano le chiamate ai metodi per gestire l'inizio, il commit e il rollback delle transazioni.
public class JPAConfig {

    @Bean
    JpaTransactionManager jpaTransactionManager(){
        return new JpaTransactionManager();
    }

}
