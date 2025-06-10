package com.moneyly.service;

import com.moneyly.model.ApiResponse;
import com.moneyly.model.User;
import com.moneyly.util.SupabaseHeadersProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class SupabaseAuthService {

    @Autowired
    SupabaseHeadersProvider supabaseHeadersProvider;

    @Autowired
    private RestTemplate restTemplate;

    public ApiResponse createUser(User user) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String url = supabaseHeadersProvider.getSupabaseUrl() + "/rest/v1/utente";

        HttpHeaders headers = supabaseHeadersProvider.getHeaders();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", user.getEmail());
        requestBody.put("password", user.getPassword());
        requestBody.put("username", user.getUsername());

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return new ApiResponse(response.getStatusCodeValue(), "Utente creato con successo");
            } else {
                return new ApiResponse(response.getStatusCodeValue(), "Errore nella creazione dell'utente");
            }

        } catch (HttpClientErrorException e) {
            return new ApiResponse(e.getStatusCode().value(), "Errore: " + e.getResponseBodyAsString());
        }
    }

    public Map<String, Object> getUserByEmail(String email) {
        Map<String, Object> responseMap = new HashMap<>();
        String url = supabaseHeadersProvider.getSupabaseUrl() + "/rest/v1/utente?email=eq." + email;

        HttpHeaders headers = supabaseHeadersProvider.getHeaders();

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<User[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                User[].class
        );

        User[] users = response.getBody();

        if (users != null && users.length > 0) {
            responseMap.put("user", users[0]);
        } else {
            responseMap.put("error", "Utente " + email + " non trovato");
        }
        return responseMap;
    }

    public Map<String, Object> getUserByUsername(String username) {
        Map<String, Object> responseMap = new HashMap<>();
        String url = supabaseHeadersProvider.getSupabaseUrl() + "/rest/v1/utente?username=eq." + username;

        HttpHeaders headers = supabaseHeadersProvider.getHeaders();

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<User[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                User[].class
        );

        User[] users = response.getBody();

        if (users != null && users.length > 0) {
            responseMap.put("user", users[0]);
        } else {
            responseMap.put("error", "Utente " + username + " non trovato");
        }
        return responseMap;
    }

}
