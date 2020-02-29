package com.example.talentshow.presentation.signing;

import com.arellomobile.mvp.MvpView;

public interface SplashView extends MvpView {
    void startMain();
    void loadPhotoForAvatar();
    void pickVideo();
    void showingError(String error);
}
