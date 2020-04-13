package com.avatar.ava.data;

import com.avatar.ava.data.api.RatingAPI;
import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.avatar.ava.domain.repository.IRatingRepository;


import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class RatingRepository implements IRatingRepository {

    private RatingAPI ratingAPI;
    private SharedPreferencesRepository preferencesRepository;

    @Inject
    RatingRepository(Retrofit retrofit, SharedPreferencesRepository preferencesRepository){
        this.ratingAPI = retrofit.create(RatingAPI.class);
        this.preferencesRepository = preferencesRepository;
    }

    @Override
    public Single<ArrayList<PersonRatingDTO>> getRating(int number) {
        return ratingAPI.getRating(preferencesRepository.getToken(), number);
    }
}
