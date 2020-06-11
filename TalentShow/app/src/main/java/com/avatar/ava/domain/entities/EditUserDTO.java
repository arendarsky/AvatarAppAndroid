package com.avatar.ava.domain.entities;

import com.google.gson.annotations.SerializedName;

public class EditUserDTO {

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("instagramLogin")
    private String instagramLogin;

    public EditUserDTO(String name, String description, String instagramLogin) {
        this.name = name;
        this.description = description;
        this.instagramLogin = instagramLogin;
    }
}
