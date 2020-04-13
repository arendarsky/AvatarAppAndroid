package com.avatar.ava.presentation.main.fragments.profile.profileSettings.changePassword;

import com.arellomobile.mvp.MvpView;

public interface ChangePasswordView extends MvpView {
    void changePass();
    void showMessage(String message);
}
