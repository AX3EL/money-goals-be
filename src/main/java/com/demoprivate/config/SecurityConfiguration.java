package com.demoprivate.config;

import com.demoprivate.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {//classe di utilità fornita da Spring Security per semplificare la configurazione della sicurezza

    @Autowired
    private MyUserDetailService userDetailsService;//interfaccia utilizzata da Spring Security per ottenere le informazioni dell'utente dal database; viene implementata dalla classe personalizzata MyUserDetailsService

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests().antMatchers("/api/v1/registrazione").permitAll()//autorizzazione senza autenticazione per il path register
                .and()
                .authorizeRequests().antMatchers("/exit").permitAll()//come per il register anche per il path exit
                .anyRequest().authenticated()//tutte le altre richieste , per l'accesso , devono essere autenticate
                .and()
                .formLogin().permitAll()
                .loginPage("/api/v1//log-in")//form login di spring security viene sovrascritta dal path /log
                .loginProcessingUrl("/login")//l'URL a cui inviare i dati del form di login per l'elaborazione dell'autenticazione.
                .and().csrf().disable();//disabilito CSRF per semplificare la gestione delle richieste da parte del frontend al backend, 		 evitando la necessità di includere token CSRF corretti in ogni richiesta.
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }//configurazione dell'autenticazione secondo il metodo userDetailsService che prende in ingresso l 'interfaccia userDetailsService implementata dalla classe MyUserDetailsService e criptazione password al login

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }//criptazione password : metodo che ritorna un'istanza di tipo BCryptPasswordEncoder per codificare le password durante l'autenticazione degli utenti

}