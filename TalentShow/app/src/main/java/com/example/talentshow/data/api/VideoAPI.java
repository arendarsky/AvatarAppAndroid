package com.example.talentshow.data.api;

import io.reactivex.Completable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface VideoAPI {

    @Multipart
    @POST("api/video/upload")
    Completable uploadVideo(@Header("Authorisation") String token, @Part MultipartBody.Part file);
}
