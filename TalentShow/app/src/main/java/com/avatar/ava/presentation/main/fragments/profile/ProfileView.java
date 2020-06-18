package com.avatar.ava.presentation.main.fragments.profile;

import android.net.Uri;

import com.arellomobile.mvp.MvpView;
import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.avatar.ava.domain.entities.ProfileDTO;

public interface ProfileView extends MvpView {
    void setDataProfile(ProfileDTO person);
    void update();
    void showMessage(String message);
    void hideProgressBar();
    void hideLoadingString();
    void showLoadingProgBar();
    void changeLoadingState(Float aFloat);
    void loadingComplete(Uri parse);
    void enableLayout();
    void setVideoLoading(boolean flag);
}
