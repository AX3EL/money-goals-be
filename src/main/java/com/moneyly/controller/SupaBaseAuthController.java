package com.moneyly.controller;

import com.moneyly.dto.AuthUser;
import com.moneyly.model.ApiResponse;
import com.moneyly.model.User;
import com.moneyly.service.SupabaseAuthService;
import com.moneyly.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class SupaBaseAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SupabaseAuthService supabaseAuthService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthUser authRequest, HttpServletResponse response) {
        try {
            authenticate(authRequest.getEmail(), authRequest.getPassword());

            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
            String token = jwtTokenUtil.generateToken(userDetails);

            User user = this.getUserByMail(userDetails.getUsername());

            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("user", user);
            return ResponseEntity.ok().body(result);


        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Attenzione: credenziali errate"));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Errore durante l'autenticazione", e);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> inserisciUtente(@RequestBody User user){

        boolean doubleUser = checkUserByMail(user);
        boolean doubleUsername = checkUserByUsername(user);
        if(doubleUser){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email gia presente");
        }else if(doubleUsername){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username gia presente");
        }else{
            ApiResponse apiResponse = supabaseAuthService.createUser(user);

            if(apiResponse != null && (apiResponse.getStatusCode() == 200 || apiResponse.getStatusCode() == 201)){
                return ResponseEntity.ok().body(apiResponse);
            }else{
                return ResponseEntity.badRequest().body(apiResponse);
            }
        }
    }
    private boolean checkUserByMail(User user) {
        Map<String, Object> responseMap = supabaseAuthService.getUserByEmail(user.getEmail());
        return responseMap.containsKey("user");
    }

    private boolean checkUserByUsername(User user) {
        Map<String, Object> responseMap = supabaseAuthService.getUserByUsername(user.getUsername());
        return responseMap.containsKey("user");
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private User getUserByMail(String email){
        Map<String, Object> responseMap = supabaseAuthService.getUserByEmail(email);
        return (User) responseMap.get("user");
    }
}