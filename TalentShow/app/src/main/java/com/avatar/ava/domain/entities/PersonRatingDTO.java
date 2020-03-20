package com.avatar.ava.domain.entities;

import com.google.gson.annotations.SerializedName;

import org.parceler.ParcelConstructor;

public class PersonRatingDTO {
    @SerializedName("likesNumber")
    private int likesNumber;

    @SerializedName("video")
    private VideoDTO video;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("profilePhoto")
    private String photo;

    @ParcelConstructor
    public PersonRatingDTO(int likesNumber, VideoDTO video, String name, String description, String photo) {
        this.likesNumber = likesNumber;
        this.video = video;
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


    public VideoDTO getVideo() {
        return video;
    }

    public void setVideo(VideoDTO video) {
        this.video = video;
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
}
