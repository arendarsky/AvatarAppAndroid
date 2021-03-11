package com.avatar.ava.di;

import android.text.TextUtils;

import com.avatar.ava.data.AuthRepository;
import com.avatar.ava.data.FinalistsRepository;
import com.avatar.ava.data.ProfileRepository;
import com.avatar.ava.data.RatingRepository;
import com.avatar.ava.data.SemifinalistsRepository;
import com.avatar.ava.data.SharedPreferencesRepository;
import com.avatar.ava.data.VideoRepository;
import com.avatar.ava.domain.repository.IAuthRepository;
import com.avatar.ava.domain.repository.IFinalistsRepository;
import com.avatar.ava.domain.repository.IProfileRepository;
import com.avatar.ava.domain.repository.IRatingRepository;
import com.avatar.ava.domain.repository.ISemifinalistsRepository;
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
//    public final static String SERVER_NAME = "https://avatarappapi.azurewebsites.net";
    public DataModule(){

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
                connectTimeout = Integer.parseInt(connectNew);
            }
            if (!TextUtils.isEmpty(readNew)) {
                readTimeout = Integer.parseInt(readNew);
            }
            if (!TextUtils.isEmpty(writeNew)) {
                writeTimeout = Integer.parseInt(writeNew);
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

        bind(IAuthRepository.class).to(AuthRepository.class).singleton();
        bind(IVideoRepository.class).to(VideoRepository.class).singleton();
        bind(IRatingRepository.class).to(RatingRepository.class).singleton();
        bind(IProfileRepository.class).to(ProfileRepository.class).singleton();
        bind(ISharedPreferencesRepository.class).to(SharedPreferencesRepository.class).singleton();
        bind(ISemifinalistsRepository.class).to(SemifinalistsRepository.class).singleton();
        bind(IFinalistsRepository.class).to(FinalistsRepository.class).singleton();
    }
}
