
package com.avatar.ava.domain.entities.semifinalists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BattleParticipant {

    @SerializedName("semifinalist")
    @Expose
    private Semifinalist semifinalist;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("profilePhoto")
    @Expose
    private String profilePhoto;

    public Semifinalist getSemifinalist() {
        return semifinalist;
    }

    public void setSemifinalist(Semifinalist semifinalist) {
        this.semifinalist = semifinalist;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

}
