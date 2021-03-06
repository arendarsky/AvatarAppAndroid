package com.avatar.ava.presentation.main.fragments.casting;

import com.arellomobile.mvp.MvpView;
import com.avatar.ava.domain.entities.PersonDTO;

public interface CastingView extends MvpView {

    void likeClicked();
    void dislikeClicked();
    void loadNewVideo(PersonDTO personDTO);
    void showError(String error);
    void setAvatar(String avatarLink);
    void setDescription(String description);
    void setName(String name);
    void showNoMoreVideos();
}
