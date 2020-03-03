package com.example.talentshow.presentation.signing.fragments.authorisation;

import com.arellomobile.mvp.MvpView;

public interface AuthorisationView extends MvpView {

    void nextScreen();
    void showError(String error);
}
