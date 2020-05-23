package com.avatar.ava.presentation.signing.fragments.resetpassword;

import com.arellomobile.mvp.MvpView;

public interface ResetPasswordView extends MvpView {
    void showError(String message);
    void nextScreen();
}
