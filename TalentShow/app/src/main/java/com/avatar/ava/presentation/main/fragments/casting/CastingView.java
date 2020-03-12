package com.avatar.ava.presentation.main.fragments.casting;

import com.arellomobile.mvp.MvpView;

public interface CastingView extends MvpView {

    void likeClicked();
    void dislikeClicked();
    void loadNewVideo(String videoLink);
    void showError(String error);
    void setAvatar(String avatarLink);
    void setDescription(String description);
    void setName(String name);
    void showNoMoreVideos();
}
