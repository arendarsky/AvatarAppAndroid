package com.avatar.ava.presentation.main.fragments.profile.publicProfile;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.BuildConfig;
import com.avatar.ava.domain.Interactor;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class PublicProfilePresenter extends MvpPresenter<PublicProfileView> {

    private Interactor interactor;
    private Context appContext;
    private Disposable loadingVideoDisp;

    @Inject
    PublicProfilePresenter(Interactor interactor, Context appContext) {
        this.interactor = interactor;
        this.appContext = appContext;
    }

    void getProfile(int id){
        Disposable disposable = interactor.getPublicProfile(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(person -> {
                            getViewState().hideProgressBar();
                            getViewState().setDataProfile(person);
                        },
                        error -> getViewState().showMessage("Упс. Произошла ошибка, попробуйте позже"));
    }

    void downloadVideo(String videoName){

        String fileName = videoName;

        String videoLocalPath = Environment.getExternalStorageDirectory()+ File.separator+"XCE_FACTOR"+ fileName;

        File file = null;
        try {
            file = File.createTempFile("video",
                    ".mp4", appContext.getCacheDir());


//        File file = new File(videoLocalPath);
            Uri uri = FileProvider.getUriForFile(appContext,appContext.
                    getApplicationContext().
                    getPackageName()
                    + ".provider", file);

            interactor.downloadVideo(videoName, Uri.parse(file.getAbsolutePath()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            new Observer<Float>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    loadingVideoDisp = d;
                                    getViewState().setVideoLoading(true);
                                    getViewState().showLoadingProgBar();
                                }

                                @Override
                                public void onNext(Float aFloat) {
                                    getViewState().changeLoadingState(aFloat);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    if (BuildConfig.DEBUG) Log.d("Loading error", e.toString());
                                    getViewState().enableLayout();
                                    getViewState().setVideoLoading(false);
                                    getViewState().showMessage("Не удалось загрузить видео. Попробуйте позже");
                                }

                                @Override
                                public void onComplete() {
                                    getViewState().setVideoLoading(false);
                                    getViewState().loadingComplete(uri);
                                    getViewState().enableLayout();
                                }
                            }
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void disposeVideoLoading(){
        if(loadingVideoDisp != null) loadingVideoDisp.dispose();
    }


}
