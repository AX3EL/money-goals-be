package com.moneyly.util;

import lombok.Getter;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class SupabaseHeadersProvider {

    @Value("${supabase.api.key}")
    private String supabaseApiKey;

    @Value("${supabase.url}")
    @Getter
    private String supabaseUrl;

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.set("apikey", supabaseApiKey);
        headers.set("Authorization", "Bearer " + supabaseApiKey);
        headers.set("Connection", "close");
        return headers;
    }
}
