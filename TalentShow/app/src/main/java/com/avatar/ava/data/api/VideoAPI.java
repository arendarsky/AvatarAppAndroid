package com.avatar.ava.data.api;


import com.avatar.ava.domain.entities.PersonDTO;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface VideoAPI {

    @Headers({"CONNECT_TIMEOUT:10000", "READ_TIMEOUT:10000", "WRITE_TIMEOUT:10000"})
    @Multipart
    @POST("api/video/upload")
    Single<String> uploadVideo(@Header("Authorization") String token,
                               @Part  MultipartBody.Part file);

    @GET("api/video/get_unwatched")
    Single<ArrayList<PersonDTO>> getUnwatched(@Header("Authorization") String token,
                                              @Query("number") int number);

    @GET("api/video/set_like")
    Completable setLiked(@Header("Authorization") String token,
                         @Query("name") String name,
                         @Query("isLike") boolean liked);

    @GET("/api/video/set_interval")
    Completable setInterval(@Header("Authorization") String token,
                            @Query("fileName") String fileName,
                            @Query("startTime") double startTime,
                            @Query("endTime") double endTime);

    @GET("/api/video/set_active")
    Completable setActive(@Header("Authorization") String token,
                          @Query("fileName") String fileName);

    @Streaming
    @GET
    Observable<Response<ResponseBody>> downloadFile(@Url String fileUrl);

}
