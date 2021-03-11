package com.avatar.ava.domain.repository;

import com.avatar.ava.domain.entities.semifinalists.BattleDTO;
import com.avatar.ava.domain.entities.semifinalists.BattleVoteResponse;

import java.util.ArrayList;

import io.reactivex.Single;

public interface ISemifinalistsRepository {
    Single<ArrayList<BattleDTO>> getBattles();
    Single<BattleVoteResponse> vote(int battleId, int semifinalistId);
}
