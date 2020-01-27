package com.example.talentshow.data.api;

import com.example.talentshow.domain.entities.ConfirmationDTO;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AuthAPI {

    @GET("api/auth/send")
    Completable sendCode(@Query("email") String name);

    @GET("api/auth/confirm")
    Single<ConfirmationDTO> confirmEmail(@Query("email") String email,
                                         @Query("confirmCode") String confirmCode);
}