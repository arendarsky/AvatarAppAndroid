package com.avatar.ava.domain.entities;

import com.google.gson.annotations.SerializedName;

public class VideoDTO {

    @SerializedName("name")
    private String name;

    @SerializedName("isActive")
    private boolean isActive;

    @SerializedName("isApproved")
    private boolean isApproved;

    @SerializedName("startTime")
    private double startTime;

    @SerializedName("endTime")
    private double endTime;

    public VideoDTO(String name, boolean isActive, boolean isApproved, double startTime, double endTime) {
        this.name = name;
        this.isActive = isActive;
        this.isApproved = isApproved;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public VideoDTO(){}


    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
