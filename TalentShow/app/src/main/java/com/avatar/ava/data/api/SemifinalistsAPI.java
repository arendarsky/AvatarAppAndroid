package com.avatar.ava.data.api;

import com.avatar.ava.domain.entities.BattleDTO;
import com.avatar.ava.domain.entities.BattleVoteDTO;
import com.avatar.ava.domain.entities.BattleVoteResponse;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SemifinalistsAPI {

    @GET("/api/semifinal/battles/get")
    Single<ArrayList<BattleDTO>> getBattles(@Header("Authorization") String token);

    @POST("/api/semifinal/vote")
    Single<BattleVoteResponse> vote(@Header("Authorization") String token, @Body BattleVoteDTO battleVoteDTO);
}
