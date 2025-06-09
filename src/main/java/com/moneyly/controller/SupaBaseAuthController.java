package com.moneyly.controller;

import com.moneyly.dto.AuthUser;
import com.moneyly.service.SupaBaseAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class SupaBaseAuthController {

    @Autowired
    private SupaBaseAuthService supabaseAuthService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody AuthUser request) {
        try {
            Map<String, Object> response = supabaseAuthService.signUpUser(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

}
