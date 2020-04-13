package com.avatar.ava.presentation.main.fragments.profile.profileSettings.changePassword;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ChangePasswordPresenter extends MvpPresenter<ChangePasswordView> {

    private Interactor interactor;

    @Inject
    ChangePasswordPresenter(Interactor interactor) {
        this.interactor = interactor;
    }

    void changePassword(String oldPassword, String newPassword){
        Disposable disposable = interactor.setPassword(oldPassword, newPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->
                        {
                            if (result)
                            {
                                getViewState().showMessage("Пароль успешно изменён");
                                getViewState().changePass();
                            }

                            else getViewState().showMessage("Старый пароль введён неверно");
                        },
                        error -> getViewState().showMessage("При смене пароля произошла ошибка. Попробуйте позже"));
    }
}