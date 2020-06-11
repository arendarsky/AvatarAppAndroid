package com.avatar.ava.domain.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PublicProfileDTO {

    @SerializedName("name")
    private String name = "";

    @SerializedName("description")
    private String description = "";

    @SerializedName("profilePhoto")
    private String photo = "";

    @SerializedName("videos")
    private ArrayList<VideoDTO> videos;

    @SerializedName("id")
    private int id;

    @SerializedName("instagramLogin")
    private String instagramLogin;


    public PublicProfileDTO(String name, String description, String photo, ArrayList<VideoDTO> videos, int id, String instagramLogin) {
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.videos = videos;
        this.id = id;
        this.instagramLogin = instagramLogin;
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

    public ArrayList<VideoDTO> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<VideoDTO> videos) {
        this.videos = videos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstagramLogin() {
        return instagramLogin;
    }

    public void setInstagramLogin(String instagramLogin) {
        this.instagramLogin = instagramLogin;
    }
}
