package com.avatar.ava.presentation.main.fragments.profile.publicProfile;

import com.arellomobile.mvp.MvpView;
import com.avatar.ava.domain.entities.ProfileDTO;
import com.avatar.ava.domain.entities.PublicProfileDTO;

public interface PublicProfileView extends MvpView {
    void setDataProfile(PublicProfileDTO person);
    void showMessage(String message);
}
