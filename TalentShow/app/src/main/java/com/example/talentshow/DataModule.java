package com.example.talentshow;

import com.example.talentshow.data.AuthRepository;
import com.example.talentshow.data.SharedPreferencesRepository;
import com.example.talentshow.data.VideoRepository;
import com.example.talentshow.domain.repository.IAuthRepository;
import com.example.talentshow.domain.repository.ISharedPreferemcesRepository;
import com.example.talentshow.domain.repository.IVideoRepository;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import toothpick.config.Module;

public class DataModule extends Module{

    public DataModule(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(BuildConfig.DEBUG ?
                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        httpClient.addInterceptor(loggingInterceptor);

        bind(Retrofit.class).toInstance(
                new Retrofit.Builder()
                        .baseUrl("http://avatarapp.yambr.ru")
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build());

        bind(IAuthRepository.class).to(AuthRepository.class);
        bind(IVideoRepository.class).to(VideoRepository.class);
        bind(ISharedPreferemcesRepository.class).to(SharedPreferencesRepository.class);

    }
}
