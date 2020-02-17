package com.example.talentshow.presentation.star.fileload;

import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.talentshow.domain.Interactor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class StarFileLoadPresenter extends MvpPresenter<StarFileLoadView> {

    private Interactor interactor;

    @Inject
    StarFileLoadPresenter(Interactor interactor){
        this.interactor = interactor;
    }

    void uploadVideoToServer(Uri videoUri){
        Disposable disposable = interactor.uploadVideo(videoUri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getViewState().startingNextActivity(),
                        e -> getViewState().showingError());
    }
}
