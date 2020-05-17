package com.avatar.ava.presentation.main.fragments.profile.profileSettings;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProfileSettingsPresenter extends MvpPresenter<ProfileSettingsView> {

    private String errorMessage = "Произошла ошибка. Попробуйте позже";
    private Interactor interactor;

    @Inject
    ProfileSettingsPresenter(Interactor interactor) {
        this.interactor = interactor;
    }

    void showEmail() {
        Disposable disposable = interactor.getProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(person -> getViewState().setEmail(person.getEmail()),
                        error -> getViewState().showMessage(errorMessage));
    }
}