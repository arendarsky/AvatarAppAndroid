package com.avatar.ava.data;

import android.content.Context;

import com.avatar.ava.data.api.RatingAPI;
import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.avatar.ava.domain.repository.IRatingRepository;


import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RatingRepository implements IRatingRepository {

    private RatingAPI ratingAPI;
    private SharedPreferencesRepository preferencesRepository;
    private Context appContext;
    private ArrayList<String> videoNames = new ArrayList<>();

    @Inject
    RatingRepository(Retrofit retrofit, SharedPreferencesRepository preferencesRepository, Context appContext){
        this.ratingAPI = retrofit.create(RatingAPI.class);
        this.preferencesRepository = preferencesRepository;
        this.appContext = appContext;
    }

    @Override
    public Single<ArrayList<PersonRatingDTO>> getRating(int number) {
        Disposable disposable = ratingAPI.getRating(preferencesRepository.getToken(), number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(arrayList -> {},
                        error -> {});



        return ratingAPI.getRating(preferencesRepository.getToken(), number);
    }
}
