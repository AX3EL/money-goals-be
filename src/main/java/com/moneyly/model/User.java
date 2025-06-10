package com.moneyly.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class User {

    private UUID id;
    private String email;
    private String password;
    private String username;

    public User(){}

    public User(UUID id, String email, String password, String username){
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }

}
