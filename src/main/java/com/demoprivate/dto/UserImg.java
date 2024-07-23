package com.demoprivate.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserImg {

    private String email , img;

    public UserImg(){}

    public UserImg(String email, String img){
        this.email = email;
        this.img = img;
    }


}
