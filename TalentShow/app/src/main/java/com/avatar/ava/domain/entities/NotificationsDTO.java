package com.avatar.ava.domain.entities;

import com.google.gson.annotations.SerializedName;

import org.parceler.ParcelConstructor;

public class NotificationsDTO {

    @SerializedName("date")
    private String date;

    @SerializedName("user")
    private PersonDTO user;

    @ParcelConstructor
    public NotificationsDTO(String date, PersonDTO user) {
        this.date = date;
        this.user = user;
    }

    public PersonDTO getUser() {
        return user;
    }

    public void setUser(PersonDTO user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
