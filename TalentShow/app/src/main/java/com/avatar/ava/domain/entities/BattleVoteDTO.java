package com.avatar.ava.domain.entities;

public class BattleVoteDTO {

    private String battleId;
    private String semifinalistId;

    public BattleVoteDTO(String battleId, String semifinalistId) {
        this.battleId = battleId;
        this.semifinalistId = semifinalistId;
    }

    public String getBattleId() {
        return battleId;
    }

    public void setBattleId(String battleId) {
        this.battleId = battleId;
    }

    public String getSemifinalistId() {
        return semifinalistId;
    }

    public void setSemifinalistId(String semifinalistId) {
        this.semifinalistId = semifinalistId;
    }
}
