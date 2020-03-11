package com.avatar.ava.domain.entities;

import com.google.gson.annotations.SerializedName;

import org.parceler.ParcelConstructor;

import java.util.ArrayList;
import java.util.List;

public class PersonDTO {

    @SerializedName("name")
    private String name = "";

    @SerializedName("description")
    private String surname = "";

    @SerializedName("profilePhoto")
    private String photo = "";

    @SerializedName("videos")
    private List<VideoDTO> videos = new ArrayList<>();

    private VideoDTO usedVideo;

    @ParcelConstructor
    public PersonDTO(String name, String surname, String photo, List<VideoDTO> videos){
        this.name = name;
        this.surname = surname;
        this.photo = photo;
        this.videos = videos;
    }

    public PersonDTO(String name, String surname, String photo){
        this.name = name;
        this.surname = surname;
        this.photo = photo;
    }


    public PersonDTO(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public VideoDTO getVideoForCasting(){
        VideoDTO videoForCasting = new VideoDTO();
        for (int i = 0; i < this.videos.size(); ++i){
            if (this.videos.get(i).isActive()){
                videoForCasting = this.videos.get(i);
                break;
            }
        }
        return videoForCasting;
    }

    public void prepareInfo(){
        VideoDTO videoForCasting = new VideoDTO();
        for (int i = 0; i < this.videos.size(); ++i){
            if (this.videos.get(i).isActive()){
                this.usedVideo = this.videos.get(i);
                break;
            }
        }
    }

    public VideoDTO getUsedVideo() {
        return usedVideo;
    }

    public void setUsedVideo(VideoDTO usedVideo) {
        this.usedVideo = usedVideo;
    }
}
