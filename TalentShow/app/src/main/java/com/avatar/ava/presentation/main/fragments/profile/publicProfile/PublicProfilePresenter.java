package com.avatar.ava.presentation.main.fragments.profile.publicProfile;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class PublicProfilePresenter extends MvpPresenter<PublicProfileView> {

    private Interactor interactor;

    @Inject
    PublicProfilePresenter(Interactor interactor) {
        this.interactor = interactor;
    }

    public void getProfile(int id){
        Disposable disposable = interactor.getPublicProfile(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(person -> getViewState().setDataProfile(person),
                        error -> getViewState().showMessage("Упс. Произошла ошибка, попробуйте позже"));
    }
}
