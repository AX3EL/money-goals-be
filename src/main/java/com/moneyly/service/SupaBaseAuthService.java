package com.moneyly.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneyly.util.SupabaseHeadersProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

@Service
public class SupaBaseAuthService {

    @Autowired
    private SupabaseHeadersProvider supabaseHeadersProvider;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${supabase.url}")
    private String supabaseUrl;

    public Map<String, Object> signUpUser(String email, String password) {
        String url = supabaseUrl + "/auth/v1/signup";

        HttpHeaders headers = supabaseHeadersProvider.getHeaders();
        String bodyJson = String.format("{\"email\":\"%s\", \"password\":\"%s\"}", email, password);

        HttpEntity<String> request = new HttpEntity<>(bodyJson, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
                return response.getBody();
            } else {
                throw new RuntimeException("Errore registrazione utente: " + response.getStatusCode());
            }

        } catch (HttpClientErrorException ex) {
            // Recupera il corpo dell'errore
            String responseBody = ex.getResponseBodyAsString();

            try {
                // Converte il corpo JSON dell'errore in una mappa
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(responseBody, Map.class);
            } catch (Exception jsonEx) {
                throw new RuntimeException("Errore nel parsing del corpo di errore JSON: " + responseBody);
            }

        } catch (Exception ex) {
            throw new RuntimeException("Errore generico nella registrazione: " + ex.getMessage());
        }
    }
}
