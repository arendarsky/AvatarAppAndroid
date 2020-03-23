package com.avatar.ava.presentation.main.fragments.profile.profileSettings;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;

import javax.inject.Inject;

@InjectViewState
public class ProfileSettingsPresenter extends MvpPresenter<ProfileSettingsView> {

    private Interactor interactor;

    @Inject
    ProfileSettingsPresenter(Interactor interactor) {
        this.interactor = interactor;
    }




}
