package com.avatar.ava.presentation.main.fragments.profile;

import com.arellomobile.mvp.MvpView;
import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.avatar.ava.domain.entities.ProfileDTO;

public interface ProfileView extends MvpView {
    public void setDataProfile(ProfileDTO person);
}
