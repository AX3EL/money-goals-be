package com.moneyly.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneyly.model.ApiResponse;
import com.moneyly.model.Conto;
import com.moneyly.model.Goal;
import com.moneyly.util.SupabaseHeadersProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Service
public class GoalService {

    @Autowired
    SupabaseHeadersProvider supabaseHeadersProvider;

    @Autowired
    private RestTemplate restTemplate;

    public ApiResponse createGoalAndUpdateConto(Goal goal, UUID contoId) {
        String urlObiettivo = supabaseHeadersProvider.getSupabaseUrl() + "/rest/v1/obiettivo";
        HttpHeaders headers = supabaseHeadersProvider.getHeaders();
        headers.add("Prefer", "return=representation"); // 🔽 Importante per ricevere l'ID

        // Corpo della richiesta obiettivo
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("obiettivo", goal.getObiettivo());
        requestBody.put("descrizione", goal.getDescrizione());
        requestBody.put("data_inizio", goal.getDataInizio());
        requestBody.put("data_fine", goal.getDataFine());
        requestBody.put("data_creazione", LocalDate.now());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            // Creazione obiettivo
            ResponseEntity<String> response = restTemplate.postForEntity(urlObiettivo, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                // Parsare l'ID dell'obiettivo appena creato
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseJson = objectMapper.readTree(response.getBody());
                UUID newObiettivoId = UUID.fromString(responseJson.get(0).get("id").asText());
                // Ora aggiorni il conto con questo nuovo ID
                String urlConto = supabaseHeadersProvider.getSupabaseUrl() + "/rest/v1/conto?id=eq." + contoId;

                Map<String, Object> updateBody = new HashMap<>();
                updateBody.put("obiettivo_id", newObiettivoId);

                HttpEntity<Map<String, Object>> updateRequest = new HttpEntity<>(updateBody, headers);
                restTemplate.exchange(urlConto, HttpMethod.PATCH, updateRequest, String.class);

                return new ApiResponse(200, "Obiettivo creato con successo");
            } else {
                return new ApiResponse(response.getStatusCodeValue(), "Errore nella creazione dell'obiettivo");
            }

        } catch (HttpClientErrorException e) {
            return new ApiResponse(e.getStatusCode().value(), "Errore: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            return new ApiResponse(500, "Errore generico: " + e.getMessage());
        }
    }

    public Map<String, Object> getGoalList() {
        Map<String, Object> responseMap = new HashMap<>();
        String url = supabaseHeadersProvider.getSupabaseUrl() + "/rest/v1/obiettivo";

        HttpHeaders headers = supabaseHeadersProvider.getHeaders();

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Goal[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                Goal[].class
        );

        Goal[] goalList = response.getBody();
        if(goalList != null){
            ArrayList<Goal> contoArrayList = new ArrayList<>(Arrays.asList(goalList));

            if (goalList.length > 0) {
                responseMap.put("goal", contoArrayList);
            } else {
                responseMap.put("error", "Conto non presente");
            }
        }else{
            responseMap.put("error", "Errore durante la ricerca");
        }

        return responseMap;
    }
}
