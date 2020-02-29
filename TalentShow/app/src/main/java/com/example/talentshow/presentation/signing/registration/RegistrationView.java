package com.example.talentshow.presentation.signing.registration;

import com.arellomobile.mvp.MvpView;

public interface RegistrationView extends MvpView{

    void loadAvatarClicked();
    void nameChanged();
    void mailChanged();
    void passwordChanged();
    void continueClicked();
    void nextScreen();
    void showError(String error);
}
