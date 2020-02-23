package com.example.talentshow.presentation.signing.authorisation;

import com.arellomobile.mvp.MvpPresenter;
import com.example.talentshow.domain.Interactor;

import javax.inject.Inject;

public class AuthorisationPresenter extends MvpPresenter<AuthorisationView>{

    private Interactor interactor;

    @Inject
    AuthorisationPresenter(Interactor interactor){

        this.interactor = interactor;
    }

    void auth(String mail, String password){
        //TODO Добавить обработку Completable
        interactor.authUser(mail, password);
    }
}
