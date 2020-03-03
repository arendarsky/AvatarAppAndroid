package com.example.talentshow.presentation.main.fragments.casting;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.talentshow.domain.Interactor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class CastingPresenter extends MvpPresenter<CastingView> {

    private Interactor interactor;

    @Inject
    CastingPresenter(Interactor interactor) {
        this.interactor = interactor;
    }

    String getNewVideoLink(){
        return interactor.getNewVideoLink();
    }

    void likeVideo(){
        Disposable disposable = interactor.setLiked(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getViewState().loadNewVideo(interactor.getNewVideoLink()),
                throwable ->{
                    Log.d("Casting presenter", throwable.getMessage());
//                    if (throwable.getMessage()) getViewState().showError(
//                            "Ошибка на сервере. Повторите попытку позднее"
//                    );
//                    else if (throwable.getMessage().contains("401")) getViewState().showError(
//                            "Чтобы оценивать видео необходима регистрация"
//                    );
                });
    }

    void dislikeVideo(){

        Disposable disposable = interactor.setLiked(false)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getViewState().loadNewVideo(interactor.getNewVideoLink()),
                        throwable ->{
                            if (throwable.getMessage().contains("500")) getViewState().showError(
                                    "Ошибка на сервере. Повторите попытку позднее"
                            );
                            else if (throwable.getMessage().contains("401")) getViewState().showError(
                                    "Чтобы оценивать видео необходима регистрация"
                            );
                        });
    }
}

