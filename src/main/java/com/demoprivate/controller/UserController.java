package com.demoprivate.controller;

import com.demoprivate.model.User;
import com.demoprivate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/cercaUtente/{email}")
    public ResponseEntity<Object> cercaUtente(@PathVariable("email")String email){
        User user = userService.getUserByEmail(email);
        Map<String, String> userMap = new HashMap<>();

        if (user != null) {
            userMap.put("name", user.getNome());
            userMap.put("userName", user.getUsername());
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
}
