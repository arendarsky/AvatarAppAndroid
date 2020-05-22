package com.avatar.ava.data.api;

import com.avatar.ava.domain.entities.AuthResponse;
import com.avatar.ava.domain.entities.ConfirmationDTO;
import com.avatar.ava.domain.entities.RegisterDTO;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthAPI {

    @GET("api/auth/send")
    Completable sendCode(@Query("email") String name);

    @GET("api/auth/confirm")
    Single<ConfirmationDTO> confirmEmail(@Query("email") String email,
                                         @Query("confirmCode") String confirmCode);

    @POST("api/auth/register")
    Single<Boolean> registerUser(@Body RegisterDTO userInfo);

    @POST("api/auth/authorize")
    Single<AuthResponse> authUser(@Query("email") String mail, @Query("password") String password);

    @GET("/api/auth/send_reset")
    Completable resetPassword(@Query("email") String email);
}