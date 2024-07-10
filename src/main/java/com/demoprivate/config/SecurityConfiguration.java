package com.demoprivate.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {//classe di utilità fornita da Spring Security per semplificare la configurazione della sicurezza

    @Autowired
    private UserDetailsService userDetailsService;//interfaccia utilizzata da Spring Security per ottenere le informazioni dell'utente dal database; viene implementata dalla classe personalizzata MyUserDetailsService

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/registrazione").permitAll()
                .antMatchers("/api/v1/login").permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("/api/v1/login")
                    .successHandler((request, response, authentication) -> {
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        response.setContentType("application/json;charset=UTF-8");
                        Map<String, String> data = new HashMap<>();
                        data.put("message", "Login effettuato con successo");
                        data.put("username", authentication.getName());
                        data.put("auth", String.valueOf(authentication.isAuthenticated()));

                        ObjectMapper mapper = new ObjectMapper();
                        mapper.writeValue(response.getWriter(), data);
                    })
                    .failureHandler((request, response, exception) -> {
                        response.setContentType("application/json;charset=UTF-8");
                        response.setStatus(401);
                        response.getWriter().write("{\"message\":\"Error: " + exception.getMessage() + "\"}");
                    });
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }//configurazione dell'autenticazione secondo il metodo userDetailsService che prende in ingresso l 'interfaccia userDetailsService implementata dalla classe MyUserDetailsService e criptazione password al login

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }//criptazione password : metodo che ritorna un'istanza di tipo BCryptPasswordEncoder per codificare le password durante l'autenticazione degli utenti

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH",
                "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type",
                "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}