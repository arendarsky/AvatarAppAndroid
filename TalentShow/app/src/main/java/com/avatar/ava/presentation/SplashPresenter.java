package com.avatar.ava.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;

import javax.inject.Inject;

@InjectViewState
public class SplashPresenter extends MvpPresenter<SplashView> {

    private Interactor interactor;

    @Inject
    SplashPresenter(Interactor interactor){
        this.interactor = interactor;
    }

    void checkAuth(){
        if (interactor.checkAuth()) getViewState().startMain();
        else getViewState().startEnter();
    }

}
