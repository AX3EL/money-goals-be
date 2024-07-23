package com.demoprivate.controller;

import com.demoprivate.dto.AuthUser;
import com.demoprivate.model.User;
import com.demoprivate.service.UserService;
import com.demoprivate.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthUser authRequest, HttpServletResponse response) {
        try {
            authenticate(authRequest.getEmail(), authRequest.getPassword());

            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
            String token = jwtTokenUtil.generateToken(userDetails);

            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true); // Imposta il cookie come HttpOnly
            jwtCookie.setSecure(false); // Imposta true in produzione
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(7 * 24 * 60 * 60); // Scade in una settimana
            response.addCookie(jwtCookie);
            String userProg = userService.getUserByEmail(userDetails.getUsername()).getProgressivo();

            /*User user = userService.getUserByEmail(userDetails.getUsername());
            if(user.getProgressivo() == null){
                userService.updateProgressivo(user.getEmail(), "prog");
            }
            String newProg = userService.getUserByEmail(userDetails.getUsername()).getProgressivo();*/

            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("user", userDetails.getUsername());
            result.put("prog", userProg);
            return ResponseEntity.ok().body(result);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Attenzione: credenziali errate"));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Errore durante l'autenticazione", e);
        }
    }

    @PutMapping("/logout/{email}")
    public ResponseEntity<?> logout(@PathVariable String email) {
        /*userService.updateProgressivo(email, null);*/
        return ResponseEntity.ok().body(Collections.singletonMap("success", "Logout effettuato con successo"));
    }

    @PutMapping("/add-username/{email}/{username}")
    public ResponseEntity<?> updateUsername(@PathVariable String email,@PathVariable String username) {

        User user = userService.getUserByEmail(email);
        if(user != null){
            userService.updateUsername(email,username);
            User uptatedUser = userService.getUserByEmail(email);

            Map<String , String> res = new HashMap<>();
            res.put("success", "Username aggiornato");
            res.put("username" , uptatedUser.getUsername());
            return ResponseEntity.ok().body(res);
        }else{
            return ResponseEntity.ok().body(Collections.singletonMap("error", "Attenzione: utente non trovato"));
        }
    }

    @GetMapping("/cercaUtente/{email}")
    public ResponseEntity<Object> cercaUtente(@PathVariable("email")String email){
        User user = userService.getUserByEmail(email);
        Map<String, String> userMap = new HashMap<>();

        if (user != null) {
            userMap.put("name", user.getNome());
            userMap.put("userName", user.getUsername());
            userMap.put("prog", user.getProgressivo());
            return ResponseEntity.ok().body(userMap);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato");
        }
    }

    @PostMapping("/registrazione")
    public ResponseEntity<Object> inserisciUtente(@RequestBody User user){

        boolean doubleUser = checkUser(user);
        if(doubleUser){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Utente gia presente");
        }else{
            Map<String, User> userMap = new HashMap<>();
            User newUser = userService.addUser(user);
            userMap.put("newUser", newUser);

            if(newUser != null){
                return ResponseEntity.ok().body(userMap);
            }else{
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    private boolean checkUser(User user) {
        return userService.getUserByEmail(user.getEmail()) != null;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}