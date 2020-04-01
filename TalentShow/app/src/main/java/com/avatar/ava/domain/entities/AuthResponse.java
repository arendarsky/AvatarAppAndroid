package com.avatar.ava.domain.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("confirmationRequired")
    @Expose
    private boolean confirmationRequired;

    public AuthResponse(String token, boolean confirmationRequired) {
        this.token = token;
        this.confirmationRequired = confirmationRequired;
    }

    public String getToken() {
        return token;
    }

    public boolean isConfirmationRequired() {
        return confirmationRequired;
    }

    public void setConfirmationRequired(boolean confirmationRequired) {
        this.confirmationRequired = confirmationRequired;
    }
}
