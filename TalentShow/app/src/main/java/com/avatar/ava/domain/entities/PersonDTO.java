package com.avatar.ava.domain.entities;

import org.parceler.ParcelConstructor;

public class PersonDTO {

    private int pos = 0;

    private String video = "";

    private String name = "";

    private String description = "";

    private String ava = "";

    @ParcelConstructor
    public PersonDTO(int pos, String video, String name, String description, String ava){
        this.pos = pos;
        this.video = video;
        this.name = name;
        this.description = description;
        this.ava = ava;
    }

    public PersonDTO(){}


    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
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

    public String getAva() {
        return ava;
    }

    public void setAva(String ava) {
        this.ava = ava;
    }
}
