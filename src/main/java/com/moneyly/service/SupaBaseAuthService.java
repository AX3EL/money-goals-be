package com.moneyly.service;
import com.moneyly.util.SupabaseHeadersProvider;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

@Service
public class SupaBaseAuthService {

    private SupabaseHeadersProvider supabaseHeadersProvider;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.api.key}")
    private String supabaseApiKey;

    public Map<String, Object> signUpUser(String email, String password) {

        String url = supabaseUrl + "/auth/v1/signup";

        HttpHeaders headers = supabaseHeadersProvider.getHeaders();

        String bodyJson = String.format("{\"email\":\"%s\", \"password\":\"%s\"}", email, password);

        HttpEntity<String> request = new HttpEntity<>(bodyJson, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            throw new RuntimeException("Errore registrazione utente: " + response.getStatusCode());
        }
    }
}
