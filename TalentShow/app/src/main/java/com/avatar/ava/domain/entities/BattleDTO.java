
package com.avatar.ava.domain.entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BattleDTO {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("votesNumber")
    @Expose
    private Integer votesNumber;
    @SerializedName("battleParticipants")
    @Expose
    private List<BattleParticipant> battleParticipants = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getVotesNumber() {
        return votesNumber;
    }

    public void setVotesNumber(Integer votesNumber) {
        this.votesNumber = votesNumber;
    }

    public List<BattleParticipant> getBattleParticipants() {
        return battleParticipants;
    }

    public void setBattleParticipants(List<BattleParticipant> battleParticipants) {
        this.battleParticipants = battleParticipants;
    }

}
