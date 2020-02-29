package com.example.talentshow.domain.entities;

import com.google.gson.annotations.SerializedName;

public class RegisterDTO{

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String mail;

    @SerializedName("password")
    private String password;

    public RegisterDTO(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
    }
}
