package com.avatar.ava.presentation.main.fragments.casting;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.avatar.ava.BuildConfig;
import com.avatar.ava.domain.Interactor;
import com.avatar.ava.domain.entities.PersonDTO;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings({"ResultOfMethodCallIgnored", "unused"})
@InjectViewState
public class CastingPresenter extends MvpPresenter<CastingView> {

    private Interactor interactor;
    private PersonDTO currentPerson;
    private Context appContext;

    private Disposable loadingVideoDisp;

    @Inject
    CastingPresenter(Interactor interactor, Context appContext) {
        this.interactor = interactor;
        this.appContext = appContext;
    }

    @StateStrategyType(SkipStrategy.class)
    void getFirstVideo(){
        Disposable disposable = interactor.getVideoLinkOnCreate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(person -> {
                            if (person.getVideo() != null) {
                                this.currentPerson = person;
                                getViewState().loadNewVideo(person);
                            } else getViewState().showNoMoreVideos();
                        },
                        error -> {
                            if (Objects.equals(error.getMessage(), "Empty list")) {
                                getViewState().showNoMoreVideos();
                            }
                        });
    }

    void likeVideo(){
        this.currentPerson = null;
        Disposable disposable = interactor.setLiked(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () ->
                                interactor.getNewVideoLink().subscribe(
                                        personDTO -> {
                                            this.currentPerson = personDTO;
                                            getViewState().loadNewVideo(personDTO);
                                        },
                                        error -> {
                                            if (Objects.equals(error.getMessage(), "Empty list")){
                                                getViewState().showNoMoreVideos();
                                            }
                                            if(BuildConfig.DEBUG)
                                                Log.d("Casting presenter", Objects.requireNonNull(error.getMessage()));
                                        }),
                        error -> getViewState().showError("Не удалось оценить видео, попробуйте позже"));

    }

    void dislikeVideo(){
        this.currentPerson = null;
        Disposable disposable = interactor.setLiked(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> interactor.getNewVideoLink().subscribe(
                                personDTO -> {
                                    this.currentPerson = personDTO;
                                    getViewState().loadNewVideo(personDTO);
                                },
                                error -> {
                                    if (Objects.equals(error.getMessage(), "Empty list")){
                                        getViewState().showNoMoreVideos();
                                    }
                                }),
                        error -> getViewState().showError("Не удалось оценить видео, попробуйте позже")
                );
    }

    void setOnResume(){
        if(currentPerson != null) getViewState().loadNewVideo(currentPerson);
    }

    int getPersonId(){
        return currentPerson.getId();
    }

    long getVideoBeginTime(){
        return (long) currentPerson.getVideo().getStartTime();
    }

    long getVideoEndTime(){
        return (long) currentPerson.getVideo().getEndTime();
    }

    String getVideoName(){
        return currentPerson.getVideo().getName();
    }

    boolean checkPeronDTO(PersonDTO personDTO) {
        return personDTO == this.currentPerson;
    }

    public void setVideoNull() {
        this.currentPerson = null;
    }

    void downloadVideo(){

        String fileName = currentPerson.getVideo().getName();

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

        interactor.downloadVideo(currentPerson.getVideo().getName(), Uri.parse(file.getAbsolutePath()))
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
                                getViewState().showError("Не удалось загрузить видео. Попробуйте позже");
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

