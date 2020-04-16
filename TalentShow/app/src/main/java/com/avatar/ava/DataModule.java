package com.avatar.ava;

import android.text.TextUtils;

import com.avatar.ava.data.AuthRepository;
import com.avatar.ava.data.ProfileRepository;
import com.avatar.ava.data.RatingRepository;
import com.avatar.ava.data.SharedPreferencesRepository;
import com.avatar.ava.data.VideoRepository;
import com.avatar.ava.domain.repository.IAuthRepository;
import com.avatar.ava.domain.repository.IProfileRepository;
import com.avatar.ava.domain.repository.IRatingRepository;
import com.avatar.ava.domain.repository.ISharedPreferencesRepository;
import com.avatar.ava.domain.repository.IVideoRepository;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import toothpick.config.Module;

public class DataModule extends Module{

    public final static String SERVER_NAME = "https://xce-factor.ru";
//    public final static String SERVER_NAME = "https://avatarapp.yambr.ru";

    DataModule(){

        final String CONNECT_TIMEOUT = "CONNECT_TIMEOUT";
        final String READ_TIMEOUT = "READ_TIMEOUT";
        final String WRITE_TIMEOUT = "WRITE_TIMEOUT";

        Interceptor timeoutInterceptor = chain -> {
            Request request = chain.request();

            int connectTimeout = chain.connectTimeoutMillis();
            int readTimeout = chain.readTimeoutMillis();
            int writeTimeout = chain.writeTimeoutMillis();

            String connectNew = request.header(CONNECT_TIMEOUT);
            String readNew = request.header(READ_TIMEOUT);
            String writeNew = request.header(WRITE_TIMEOUT);

            if (!TextUtils.isEmpty(connectNew)) {
                connectTimeout = Integer.valueOf(connectNew);
            }
            if (!TextUtils.isEmpty(readNew)) {
                readTimeout = Integer.valueOf(readNew);
            }
            if (!TextUtils.isEmpty(writeNew)) {
                writeTimeout = Integer.valueOf(writeNew);
            }

            Response response = chain
                    .withConnectTimeout(connectTimeout, TimeUnit.SECONDS)
                    .withReadTimeout(readTimeout, TimeUnit.SECONDS)
                    .withWriteTimeout(writeTimeout, TimeUnit.SECONDS)
                    .proceed(request);
            if (response.code() != 200) throw new IllegalStateException(String.valueOf(response.code()));
            return response;
        };


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(timeoutInterceptor);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(com.avatar.ava.BuildConfig.DEBUG ?
                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        httpClient.addInterceptor(loggingInterceptor);

        bind(Retrofit.class).toInstance(
                new Retrofit.Builder()
                        .baseUrl(SERVER_NAME)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build());

        bind(IAuthRepository.class).to(AuthRepository.class).singletonInScope();
        bind(IVideoRepository.class).to(VideoRepository.class).singletonInScope();
        bind(IRatingRepository.class).to(RatingRepository.class).singletonInScope();
        bind(IProfileRepository.class).to(ProfileRepository.class).singletonInScope();
        bind(ISharedPreferencesRepository.class).to(SharedPreferencesRepository.class).singletonInScope();

    }
}
