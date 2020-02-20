package com.example.talentshow.presentation.signing.nameenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.talentshow.Screens;
import com.example.talentshow.domain.Interactor;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class StarNameEnterPresenter extends MvpPresenter<StarNameEnterView> {

    private Interactor interactor;
    private Router router;

    @Inject
    public StarNameEnterPresenter(Interactor interactor, Router router) {
        this.interactor = interactor;
        this.router = router;
    }

    void saveName(String name){
        interactor.saveName(name);
        router.newRootScreen(new Screens.StarMainScreen());
    }
}
