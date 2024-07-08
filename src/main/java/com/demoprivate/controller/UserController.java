package com.demoprivate.controller;

import com.demoprivate.model.User;
import com.demoprivate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserService userService;

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
