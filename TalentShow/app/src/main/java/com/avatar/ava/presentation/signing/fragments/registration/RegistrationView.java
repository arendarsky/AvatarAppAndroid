package com.avatar.ava.presentation.signing.fragments.registration;

import android.view.MotionEvent;
import android.view.View;

import com.arellomobile.mvp.MvpView;

public interface RegistrationView extends MvpView{

    void nameChanged();
    void mailChanged();
    void passwordChanged();
    void continueClicked();
    void nextScreen();
    void showError(String error);
    void hideProgressBar();
}
