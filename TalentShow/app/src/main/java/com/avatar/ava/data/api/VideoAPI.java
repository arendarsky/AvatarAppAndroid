package com.avatar.ava.data.api;


import java.util.ArrayList;
import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface VideoAPI {

    @Headers({"CONNECT_TIMEOUT:10000", "READ_TIMEOUT:10000", "WRITE_TIMEOUT:10000"})
    @Multipart
    @POST("api/video/upload")
    Single<String> uploadVideo(@Header("Authorization") String token, @Part  MultipartBody.Part file);

//    @GET("api/video/get_unwatched")
//    Single<ArrayList<String>> getUnwatched(@Header("Authorization") String token, @Query("number") int number);

    @GET("api/video/get_unwatched")
    Single<ArrayList<String>> getUnwatched(@Header("Authorization") String token, @Query("number") int number);

    @GET("api/video/set_like")
    Completable setLiked(@Header("Authorization") String token, @Query("name") String name, @Query("isLike") boolean liked);

    @GET("/api/video/set_interval")
    Completable setInterval(@Header("Authorization") String token, @Query("fileName") String fileName, @Query("startTime") int startTime, @Query("endTime") int endTime);
}
