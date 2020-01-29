package com.example.talentshow.domain.entities;

import com.google.gson.annotations.SerializedName;

public class ConfirmationDTO {

    @SerializedName("session_guide")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}