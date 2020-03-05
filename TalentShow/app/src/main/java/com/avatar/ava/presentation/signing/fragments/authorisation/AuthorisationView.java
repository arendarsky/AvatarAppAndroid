package com.avatar.ava.presentation.signing.fragments.authorisation;

import com.arellomobile.mvp.MvpView;

public interface AuthorisationView extends MvpView {

    void nextScreen();
    void showError(String error);
}
