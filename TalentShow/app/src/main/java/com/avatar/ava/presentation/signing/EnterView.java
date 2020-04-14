package com.avatar.ava.presentation.signing;

import com.arellomobile.mvp.MvpView;

public interface EnterView extends MvpView {
    void startMain();
    void pickVideo();
    void showingError(String error);
}
