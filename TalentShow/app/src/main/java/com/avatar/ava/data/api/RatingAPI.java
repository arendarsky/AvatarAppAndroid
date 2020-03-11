package com.avatar.ava.data.api;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RatingAPI {

    @GET("api/video/get_unwatched")
    Single<ArrayList<String>> getRating(@Header("Authorization") String token, @Query("number") int number);
}
