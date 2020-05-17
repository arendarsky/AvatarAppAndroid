package com.avatar.ava.presentation.main.fragments.profile.profileSettings;

import com.arellomobile.mvp.MvpView;

public interface ProfileSettingsView extends MvpView {
    void setEmail(String email);

    void showMessage(String message);
}
