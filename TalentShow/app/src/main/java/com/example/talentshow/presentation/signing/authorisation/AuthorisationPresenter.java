package com.example.talentshow.presentation.signing.authorisation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.talentshow.domain.Interactor;

import javax.inject.Inject;


@InjectViewState
public class AuthorisationPresenter extends MvpPresenter<AuthorisationView> {

    private Interactor interactor;

    @Inject
    public AuthorisationPresenter(Interactor interactor) {
        this.interactor = interactor;
    }


}
