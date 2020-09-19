package com.avatar.ava.domain.repository;

import com.avatar.ava.domain.entities.BattleDTO;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ISemifinalistsRepository {
    Single<ArrayList<BattleDTO>> getBattles();
    Completable vote(String battleId, String semifinalistId);
    Completable cancelVote(String battleId, String semifinalistId);
}
