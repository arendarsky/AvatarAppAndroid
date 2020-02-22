package com.example.talentshow.presentation.signing.registration;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.talentshow.Screens;
import com.example.talentshow.domain.Interactor;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class RegistrationPresenter extends MvpPresenter<RegistrationView> {

    private Interactor interactor;
    private Router router;

    @Inject
    public RegistrationPresenter(Interactor interactor, Router router) {
        this.interactor = interactor;
        this.router = router;
    }

    void saveName(String name){
        interactor.saveName(name);
        router.newRootScreen(new Screens.StarMainScreen());
    }
}
