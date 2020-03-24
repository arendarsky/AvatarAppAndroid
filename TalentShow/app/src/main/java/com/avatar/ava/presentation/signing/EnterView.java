package com.avatar.ava.presentation.signing;

import com.arellomobile.mvp.MvpView;

import java.util.List;

public interface EnterView extends MvpView {
    void startMain();
//    void loadPhotoForAvatar();
    void pickVideo();
    void showingError(String error);
}
