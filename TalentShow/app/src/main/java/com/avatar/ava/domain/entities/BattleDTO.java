
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
    @SerializedName("totalVotesNumber")
    @Expose
    private Integer totalVotesNumber;
    @SerializedName("winnersNumber")
    @Expose
    private Integer winnerNumber;
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

    public Integer getTotalVotesNumber() {
        return totalVotesNumber;
    }

    public void setTotalVotesNumber(Integer votesNumber) {
        this.totalVotesNumber = votesNumber;
    }

    public List<BattleParticipant> getBattleParticipants() {
        return battleParticipants;
    }

    public void setBattleParticipants(List<BattleParticipant> battleParticipants) {
        this.battleParticipants = battleParticipants;
    }

    public Integer getWinnerNumber() {
        return winnerNumber;
    }

    public void setWinnerNumber(Integer winnerNumber) {
        this.winnerNumber = winnerNumber;
    }
}
