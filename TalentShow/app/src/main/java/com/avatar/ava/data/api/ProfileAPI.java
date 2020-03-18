package com.avatar.ava.data.api;

import com.avatar.ava.domain.entities.NotificationsDTO;
import com.avatar.ava.domain.entities.PersonRatingDTO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ProfileAPI {

    @GET("/api/profile/get")
    Single<PersonRatingDTO> getProfile(@Header("Authorization") String token);

    @POST("/api/profile/set_description")
    Completable setDescription(@Header("Authorization") String token,
                               @Query("description") String description);

    @GET("/api/profile/set_name")
    Completable setName(@Header("Authorization") String token,
                        @Query("name") String name);

    @POST("/api/profile/set_password")
    Completable setPassword(@Header("Authorization") String token,
                            @Query("oldPassword") String oldPassword,
                            @Query("newPassword") String newPassword);

    @Headers({"CONNECT_TIMEOUT:10000", "READ_TIMEOUT:10000", "WRITE_TIMEOUT:10000"})
    @Multipart
    @POST("/api/profile/photo/upload")
    Single<String> uploadPhoto(@Header("Authorization") String token,
                               @Part MultipartBody.Part file);

    @GET("/api/profile/notifications")
    Single<List<NotificationsDTO>> getNotifications(@Header("Authorization") String token,
                                                       @Query("number") int number,
                                                       @Query("skip") int skipNumber);

}
