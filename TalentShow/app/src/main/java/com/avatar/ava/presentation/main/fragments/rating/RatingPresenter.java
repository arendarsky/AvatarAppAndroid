package com.avatar.ava.presentation.main.fragments.rating;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.BuildConfig;
import com.avatar.ava.domain.Interactor;
import com.avatar.ava.domain.entities.PersonRatingDTO;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("unused")
@InjectViewState
public class RatingPresenter extends MvpPresenter<RatingView> {

    private Interactor interactor;
    private Context appContext;
    private Disposable loadingVideoDisp;


    @Inject
    RatingPresenter(Interactor interactor, Context appContext) {
        this.appContext = appContext;
        this.interactor = interactor;
    }

    void getRating(){
        Disposable disposable = interactor.getRating(50)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(arrayList -> {
                            getViewState().hideProgressBar();
                            getViewState().setData(arrayList);
                        },
                        error -> {
                            getViewState().hideProgressBar();
                            getViewState().showMessage("Не удалось загрузить рейтинг. Попробуйте позже");
                        });


    }

    void getSemifinalists(){
        Disposable disposable = interactor.getSemifinalists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(arrayList -> {
                            getViewState().hideProgressBar();
                            getViewState().setSemifinalists(arrayList);
                        },
                        error -> {
                            //getViewState().hideProgressBar();
                            getViewState().showMessage("Не удалось загрузить рейтинг. Попробуйте позже");
                        });
    }

    void downloadVideo(PersonRatingDTO currentPerson){

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
