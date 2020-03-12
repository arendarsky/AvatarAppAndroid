package com.avatar.ava.data;

import android.content.Context;

import com.avatar.ava.data.api.ProfileAPI;
import com.avatar.ava.data.api.RatingAPI;
import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.avatar.ava.domain.repository.IProfileRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ProfileRepository implements IProfileRepository {

    private ProfileAPI profileAPI;
    private SharedPreferencesRepository preferencesRepository;
    private Context appContext;


    @Inject
    ProfileRepository(Retrofit retrofit, SharedPreferencesRepository preferencesRepository, Context appContext){
        this.profileAPI = retrofit.create(ProfileAPI.class);
        this.preferencesRepository = preferencesRepository;
        this.appContext = appContext;
    }


    @Override
    public Single<PersonRatingDTO> getProfile() {
        Disposable disposable = profileAPI.getProfile(preferencesRepository.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(person -> {},
                        error -> {});



        return profileAPI.getProfile(preferencesRepository.getToken());
    }
}
