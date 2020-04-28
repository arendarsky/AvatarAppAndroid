package com.avatar.ava.presentation.main;

public interface MainScreenPostman {
    void fragmentAction(int code);
    void openPublicProfile(int id);
    void closeFragment();
    void openFullScreen(String videoName);
}
