package com.avatar.ava.data.api;

import com.avatar.ava.domain.entities.PersonRatingDTO;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ProfileAPI {

    @GET("/api/profile/get")
    Single<PersonRatingDTO> getProfile(@Header("Authorization") String token);
}
