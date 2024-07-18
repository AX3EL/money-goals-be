package com.demoprivate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
//configurazione MVC per fornire tutte le configurazioni necessarie per gestire le richieste e le risposte HTTP.
public class CorsConfiguration implements WebMvcConfigurer {// l'implementazione di questa interfaccia consente di personalizzare le configurazioni CORS.

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//Specifioc che le regole CORS saranno applicate a tutte le risorse del server e tutti i percorsi dell'applicazione
                .allowedOrigins("http://localhost:4200") //unico URL da cui possono provenire le richieste
                .allowCredentials(true)//le richieste possono includere credenziali (come i cookie) quando vengono inviate al server
                .allowedMethods("GET", "POST", "PUT", "DELETE")//In questo caso, sono consentiti i metodi "GET", "POST", "PUT" e "DELETE"
                .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Authorization", "Origin", "Access-Control-Request-Method",
                        "Access-Control-Request-Headers")//definisce gli header che il server acconsente ad accettare nelle richieste CORS
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");//definisce gli header che il server è disposto a esporre nelle risposte CORS
    }

}