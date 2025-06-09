package com.moneyly.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//Specifioc che le regole CORS saranno applicate a tutte le risorse del server e tutti i percorsi dell'applicazione
                .allowedOrigins("*") //unico URL da cui possono provenire le richieste
                .allowCredentials(false)//le richieste possono includere credenziali (come i cookie) quando vengono inviate al server
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")//In questo caso, sono consentiti i metodi "GET", "POST", "PUT" e "DELETE"
                .allowedHeaders("*");//definisce gli header che il server acconsente ad accettare nelle richieste CORS
    }

}