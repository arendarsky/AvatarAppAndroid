package com.avatar.ava.data;

import com.avatar.ava.data.api.RatingAPI;
import com.avatar.ava.data.api.SemifinalistsAPI;
import com.avatar.ava.domain.repository.ISemifinalistsRepository;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class SemifinalistsRepository implements ISemifinalistsRepository {

    private SemifinalistsAPI semifinalistsAPI;
    private SharedPreferencesRepository preferencesRepository;

    @Inject
    SemifinalistsRepository(Retrofit retrofit){
        this.semifinalistsAPI = retrofit.create(SemifinalistsAPI.class);
        this.preferencesRepository = preferencesRepository;
    }

}
