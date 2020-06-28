package com.avatar.ava.domain.entities;

import com.google.gson.annotations.SerializedName;

public class RegisterDTO{

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String mail;

    @SerializedName("password")
    private String password;

    @SerializedName("ConsentToGeneralEmail")
    private Boolean consentToGeneralEmail;

    public RegisterDTO(String name, String mail, String password, Boolean consentToGeneralEmail) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.consentToGeneralEmail = consentToGeneralEmail;
    }
}
