package com.avatar.ava.domain.entities;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import org.parceler.ParcelConstructor;

import java.util.Objects;

public class PersonDTO {

    private int pos = 0;

    @SerializedName("name")
    private String name = "";

    @SerializedName("description")
    private String description = "";

    @SerializedName("profilePhoto")
    private String photo = "";

    @SerializedName("video")
    private VideoDTO video;

    @SerializedName("id")
    private int id;


    @ParcelConstructor
    public PersonDTO(String name, String description, String photo, VideoDTO video, int id){
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.video = video;
        this.id = id;
    }

    public PersonDTO(String name, String description, String photo){
        this.name = name;
        this.description = description;
        this.photo = photo;
    }

//    @Override
//    public boolean equals(@Nullable Object obj) {
//        return ((PersonDTO) obj).id == this.id;
//    }

    public PersonDTO(){}

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public VideoDTO getVideo() {
        return video;
    }

    public void setVideo(VideoDTO video) {
        this.video = video;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
