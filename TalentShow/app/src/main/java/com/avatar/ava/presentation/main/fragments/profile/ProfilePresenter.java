package com.avatar.ava.presentation.main.fragments.profile;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.BuildConfig;
import com.avatar.ava.domain.Interactor;
import com.avatar.ava.domain.entities.ProfileDTO;
import com.avatar.ava.domain.entities.VideoDTO;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("unused")
@InjectViewState
public class ProfilePresenter extends MvpPresenter<ProfileView> {

    private Context appContext;
    private Interactor interactor;
    private String errorMessage = "Произошла ошибка. Попробуйте позже";

    private Disposable loadingVideoDisp;

    @Inject
    ProfilePresenter(Interactor interactor, Context appContext) {
        this.interactor = interactor;
        this.appContext = appContext;
    }

    void getProfile(){
        Disposable disposable = interactor.getProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(person -> {
                            if (this.getLoadingVideo() != null)
                                person.addLoadingVideo(this.getLoadingVideo());
                            getViewState().setDataProfile(person);
                            getViewState().hideProgressBar();
                            getViewState().update();
                        },
                        error -> {
                            getViewState().hideProgressBar();
                            getViewState().showMessage(errorMessage);
                        });
    }

    void setDescription(String description){
        Disposable disposable = interactor.setDescription(description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getViewState().hideProgressBar(),
                        error -> {
                            getViewState().hideProgressBar();
                            getViewState().showMessage(errorMessage);
                        });

    }

    void setName(String name){
        Disposable disposable = interactor.setName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getViewState().hideProgressBar(),
                        error -> getViewState().showMessage(errorMessage));
    }

    void updateProfile(String name, String description, String instagramLogin){
        Disposable disposable = interactor.updateProfile(name, description, instagramLogin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getViewState().hideProgressBar(),
                        error -> getViewState().showMessage(errorMessage));
    }

    void uploadPhoto(Uri uri){
        Disposable disposable = interactor.uploadPhoto(uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            getViewState().hideProgressBar();
                            this.getProfile();
                        },
                        e -> {
                            if (Objects.equals(e.getMessage(), "Too big"))
                                getViewState().showMessage("Фото слишком большое");
                            else getViewState().showMessage(errorMessage);
                        });
    }

    void removeVideo(String name){
        Disposable disposable = interactor.removeVideo(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            getViewState().hideProgressBar();
                            this.getProfile();
                        },
                        error -> {
                            getViewState().hideProgressBar();
                            getViewState().showMessage(errorMessage);
                        });
    }

    void setActive(String fileName){
        Disposable disposable = interactor.setActive(fileName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            getViewState().hideProgressBar();
                            this.getProfile();
                        },
                        error -> {
                            getViewState().hideProgressBar();
                            getViewState().showMessage(errorMessage);
                        });
    }

    void changeInterval(String fileName, Float start, Float end){

    }

    Uri getLoadingVideo() {
        return interactor.getLoadingVideo();
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