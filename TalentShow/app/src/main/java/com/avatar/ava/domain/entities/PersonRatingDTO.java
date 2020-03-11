package com.avatar.ava.domain.entities;

import com.google.gson.annotations.SerializedName;

import org.parceler.ParcelConstructor;

public class PersonRatingDTO {
    @SerializedName("likesNumber")
    private int likesNumber;

    @SerializedName("user")
    private PersonDTO personDTO;

    @ParcelConstructor
    public PersonRatingDTO(int likesNumber, PersonDTO personDTO) {
        this.likesNumber = likesNumber;
        this.personDTO = personDTO;
    }

    public int getLikesNumber() {
        return likesNumber;
    }

    public void setLikesNumber(int likesNumber) {
        this.likesNumber = likesNumber;
    }

    public PersonDTO getPersonDTO() {
        return personDTO;
    }

    public void setPersonDTO(PersonDTO personDTO) {
        this.personDTO = personDTO;
    }
}
