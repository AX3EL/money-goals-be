package com.moneyly.dto;

public class AuthResponse {
    private final String token;
    private String msg;

    public AuthResponse(String msg , String token) {
        this.msg = msg;
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
