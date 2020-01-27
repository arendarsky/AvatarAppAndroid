package com.example.talentshow;

import com.example.talentshow.data.AuthRepository;
import com.example.talentshow.domain.repository.IAuthRepository;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import toothpick.config.Module;

public class DataModule extends Module{

    public DataModule(){
        bind(Retrofit.class).toInstance(
                new Retrofit.Builder()
                        .baseUrl("https://avatarappapi20200123093213.azurewebsites.net")
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build());

        bind(IAuthRepository.class).to(AuthRepository.class);

    }
}
