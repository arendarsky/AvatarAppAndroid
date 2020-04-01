package com.avatar.ava.presentation.main;

import com.arellomobile.mvp.MvpView;

public interface MainScreenView extends MvpView {
    void setLoadVideoToServer(boolean loadVideoToServer);
    void changeTitle(String title);
    void showBackButton();
    void showMenuPoints();
    void showSaveButton();
    void showAddButton();
    void showSaveProfile();
    void showSavePassword();
    void showExit();
    void stopVideoFromCasting();
    void clearTopView();
    void showMessage(String message);
    void addButtonClicked();
    void hideBottomNavBar();
    void showBottomNavBar();
    void pickVideo();
    void captureVideo();
    void hideProgressBar();
}
