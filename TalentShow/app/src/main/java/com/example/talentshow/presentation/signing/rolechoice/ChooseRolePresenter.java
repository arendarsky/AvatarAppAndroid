package com.example.talentshow.presentation.signing.rolechoice;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.talentshow.Screens;
import com.example.talentshow.domain.Interactor;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class ChooseRolePresenter extends MvpPresenter<ChooseRoleView>{

    private Interactor interactor;
    private Router router;

    @Inject
    ChooseRolePresenter(Interactor interactor, Router router){
        this.interactor = interactor;
        this.router = router;
    }

    void setRole(String role){
        interactor.saveRole(role);
        router.navigateTo(new Screens.EmailEnterScreen());
    }

    void checkAuth(){
        if (interactor.checkAuth()) router.newRootScreen(new Screens.StarMainScreen());
    }
}
