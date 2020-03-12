package com.avatar.ava.presentation.main.fragments.profile;

import com.arellomobile.mvp.MvpView;
import com.avatar.ava.domain.entities.PersonRatingDTO;

public interface ProfileView extends MvpView {
    public void setDataProfile(PersonRatingDTO person);
    public void updatePhoto();
}
