package com.example.talentshow.data.api;


import java.util.ArrayList;
import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface VideoAPI {

    @Multipart
    @POST("api/video/upload")
    Completable uploadVideo(@Header("Authorization") String token, @Part  MultipartBody.Part file);

//    @GET("api/video/get_unwatched")
//    Single<ArrayList<String>> getUnwatched(@Header("Authorization") String token, @Query("number") int number);

    @GET("api/admin/get_videos")
    Single<ArrayList<String>> getUnwatched(@Header("Authorization") String token, @Query("number") int number);
}
