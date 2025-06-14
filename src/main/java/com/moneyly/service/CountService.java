package com.moneyly.service;

import com.moneyly.model.ApiResponse;
import com.moneyly.model.Conto;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountService {

    @Autowired
    SupabaseHeadersProvider supabaseHeadersProvider;

    @Autowired
    private RestTemplate restTemplate;
    public ApiResponse createConto(Conto conto) {

        String url = supabaseHeadersProvider.getSupabaseUrl() + "/rest/v1/conto";
        HttpHeaders headers = supabaseHeadersProvider.getHeaders();
        headers.add("Prefer", "return=representation");

        // Corpo della richiesta
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("nome_conto", conto.getNomeConto());
        requestBody.put("tipo_conto", conto.getTipoConto());
        requestBody.put("budget_iniziale", conto.getBudgetIniziale());
        requestBody.put("saldo", conto.getSaldo());
        requestBody.put("utente_id", conto.getUtenteId());
        requestBody.put("conto_primario", conto.isPrimario());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return new ApiResponse(response.getStatusCodeValue(), "Conto creato con successo");
            } else {
                return new ApiResponse(response.getStatusCodeValue(), "Errore nella creazione del conto");
            }

        } catch (HttpClientErrorException e) {
            return new ApiResponse(e.getStatusCode().value(), "Errore: " + e.getResponseBodyAsString());
        }
    }

    public Map<String, Object> getCountByIdUtente(UUID idUtente) {
        Map<String, Object> responseMap = new HashMap<>();
        String url = supabaseHeadersProvider.getSupabaseUrl() + "/rest/v1/conto?utente_id=eq." + idUtente;

        HttpHeaders headers = supabaseHeadersProvider.getHeaders();

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Conto[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                Conto[].class
        );

        Conto[] countList = response.getBody();
        if(countList != null){
            ArrayList<Conto> contoArrayList = new ArrayList<>(Arrays.asList(countList));

            if (countList.length > 0) {
                responseMap.put("counts", contoArrayList);
            } else {
                responseMap.put("error", "Non sono presenti conti per l'utente " + idUtente);
            }
        }else{
            responseMap.put("error", "Errore durante la ricerca");
        }

        return responseMap;
    }

    public Map<String, Object> getCountById(UUID idConto) {
        Map<String, Object> responseMap = new HashMap<>();
        String url = supabaseHeadersProvider.getSupabaseUrl() + "/rest/v1/conto?id=eq." + idConto;

        HttpHeaders headers = supabaseHeadersProvider.getHeaders();

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Conto[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                Conto[].class
        );

        Conto[] countList = response.getBody();
        if(countList != null){
            ArrayList<Conto> contoArrayList = new ArrayList<>(Arrays.asList(countList));

            if (countList.length > 0) {
                responseMap.put("count", contoArrayList.get(0));
            } else {
                responseMap.put("error", "Conto non presente");
            }
        }else{
            responseMap.put("error", "Errore durante la ricerca");
        }

        return responseMap;
    }
}
