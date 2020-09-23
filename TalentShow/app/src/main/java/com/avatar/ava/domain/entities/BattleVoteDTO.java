package com.avatar.ava.domain.entities;

public class BattleVoteDTO {

    private int battleId;
    private int semifinalistId;

    public BattleVoteDTO(int battleId, int semifinalistId) {
        this.battleId = battleId;
        this.semifinalistId = semifinalistId;
    }

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
    }

    public int getSemifinalistId() {
        return semifinalistId;
    }

    public void setSemifinalistId(int semifinalistId) {
        this.semifinalistId = semifinalistId;
    }
}
