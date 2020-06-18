package com.avatar.ava.presentation.main.fragments.profile.publicProfile;

import android.net.Uri;

import com.arellomobile.mvp.MvpView;
import com.avatar.ava.domain.entities.ProfileDTO;
import com.avatar.ava.domain.entities.PublicProfileDTO;

public interface PublicProfileView extends MvpView {
    void setDataProfile(PublicProfileDTO person);
    void showMessage(String message);
    void hideProgressBar();
    void showLoadingProgBar();
    void changeLoadingState(Float aFloat);
    void loadingComplete(Uri uri);
    void enableLayout();
    void setVideoLoading(boolean flag);
}
