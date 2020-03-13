package com.avatar.ava.data.api;

import com.avatar.ava.domain.entities.NotificationsDTO;
import com.avatar.ava.domain.entities.PersonDTO;
import com.avatar.ava.domain.entities.PersonRatingDTO;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RatingAPI {

    @GET("/api/rating/get")
    Single<ArrayList<PersonRatingDTO>> getRating(@Header("Authorization") String token, @Query("number") int number);

    @GET("/api/rating/likes/get")
    Observable<NotificationsDTO> getLikes(@Header("Authorization") String token, @Query("number") int number);
}
