package com.moneyly.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String email;
    private String password;
    private String username;

    public User(){}

    public User(String email, String password, String username){
        this.email = email;
        this.password = password;
        this.username = username;
    }

}
