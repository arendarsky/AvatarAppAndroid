package com.avatar.ava.data;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.avatar.ava.data.api.ProfileAPI;
import com.avatar.ava.domain.entities.NotificationsDTO;
import com.avatar.ava.domain.entities.ProfileDTO;
import com.avatar.ava.domain.entities.PublicProfileDTO;
import com.avatar.ava.domain.repository.IProfileRepository;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

import static com.avatar.ava.data.VideoRepository.getFilePathFromUri;

public class ProfileRepository implements IProfileRepository {

    private ProfileAPI profileAPI;
    private SharedPreferencesRepository preferencesRepository;
    private Context appContext;
    private String img;


    @Inject
    ProfileRepository(Retrofit retrofit, SharedPreferencesRepository preferencesRepository, Context appContext){
        this.profileAPI = retrofit.create(ProfileAPI.class);
        this.preferencesRepository = preferencesRepository;
        this.appContext = appContext;
    }


    @Override
    public Single<ProfileDTO> getProfile() {
        return profileAPI.getProfile(preferencesRepository.getToken());
    }

    @Override
    public Completable setDescription(String description) {
        return profileAPI.setDescription(preferencesRepository.getToken(), description);
    }

    @Override
    public Completable setName(String name) {
        return profileAPI.setName(preferencesRepository.getToken(), name);
    }

    @Override
    public Single<Boolean> setPassword(String oldPassword, String newPassword) {
        return profileAPI.setPassword(preferencesRepository.getToken(), oldPassword, newPassword);
    }

    @Override
    public Completable uploadPhoto(Uri photoUri) {

        File file = new File(getFilePathFromUri(appContext, photoUri));

        RequestBody requestFile =
                RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        return profileAPI.uploadPhoto(preferencesRepository.getToken(), body)
                .doOnSuccess(
                        name -> {
                            this.img = name;
                            Log.d("Extension", img);
                        })
                .ignoreElement();

    }

    @Override
    public Single<List<NotificationsDTO>> getNotifications(int number, int skipNumber) {
        return profileAPI.getNotifications(preferencesRepository.getToken(), number, skipNumber);
    }

    @Override
    public Completable removeVideo(String name) {
        return profileAPI.removeVideo(preferencesRepository.getToken(), name);
    }

    @Override
    public Single<PublicProfileDTO> getPublicProfile(int id) {
        return profileAPI.getPublicProfile(preferencesRepository.getToken(), id);
    }


}
