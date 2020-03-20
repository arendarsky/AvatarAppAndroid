package com.avatar.ava.presentation.main;

import com.arellomobile.mvp.MvpView;

public interface MainScreenView extends MvpView {

    void changeTitle(String title);
    void showBackButton();
    void showMenuPoints();
    void showSaveButton();
    void showAddButton();
    void clearTopView();
    void showMessage(String message);
    void addButtonClicked();
    void hideBottomNavBar();
    void showBottomNavBar();
    void pickVideo();
}
