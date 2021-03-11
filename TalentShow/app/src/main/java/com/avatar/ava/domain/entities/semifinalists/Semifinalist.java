
package com.avatar.ava.domain.entities.semifinalists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Semifinalist {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("videoName")
    @Expose
    private String videoName;
    @SerializedName("votesNumber")
    @Expose
    private Integer votesNumber;
    @SerializedName("isLikedByUser")
    @Expose
    private Boolean isLikedByUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public Integer getVotesNumber() {
        return votesNumber;
    }

    public void setVotesNumber(Integer votesNumber) {
        this.votesNumber = votesNumber;
    }

    public Boolean getIsLikedByUser() {
        return isLikedByUser;
    }

    public void setIsLikedByUser(Boolean isLikedByUser) {
        this.isLikedByUser = isLikedByUser;
    }

}
