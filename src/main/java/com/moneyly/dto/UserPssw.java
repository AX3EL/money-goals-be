package com.moneyly.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPssw {

    private String email, password;

    public UserPssw(){}

    public UserPssw(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
