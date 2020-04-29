package com.avatar.ava.domain.entities;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import org.parceler.ParcelConstructor;

import java.util.ArrayList;

public class ProfileDTO {
    @SerializedName("likesNumber")
    private int likesNumber;

    @SerializedName("videos")
    private ArrayList<VideoDTO> videos;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("profilePhoto")
    private String photo;

    @ParcelConstructor
    public ProfileDTO(int likesNumber, ArrayList<VideoDTO> videos, int id,  String name, String description, String photo) {
        this.likesNumber = likesNumber;
        this.videos = videos;
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
    }

    public int getLikesNumber() {
        return likesNumber;
    }

    public void setLikesNumber(int likesNumber) {
        this.likesNumber = likesNumber;
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

    public void addLoadingVideo(Uri uri){
        VideoDTO tmp = new VideoDTO();
        tmp.setName(uri.toString());
        tmp.setStartTime(-1);
        this.videos.add(tmp);
    }
}
