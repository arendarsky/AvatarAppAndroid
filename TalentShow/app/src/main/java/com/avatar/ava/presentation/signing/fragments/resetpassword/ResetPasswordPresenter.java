package com.avatar.ava.presentation.signing.fragments.resetpassword;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ResetPasswordPresenter extends MvpPresenter<ResetPasswordView> {
    private Interactor interactor;

    @Inject
    ResetPasswordPresenter(Interactor interactor){

        this.interactor = interactor;
    }

    void resetPassword(String email){
        Disposable disposable = interactor.resetPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            getViewState().showError("Проверьте свою почту");
                            getViewState().nextScreen();
                        },
                        error -> getViewState().showError("Введите почтку корректно")
                );
    }
}
