package com.avatar.ava.presentation.signing.fragments.authorisation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class AuthorisationPresenter extends MvpPresenter<AuthorisationView>{

    private Interactor interactor;

    @Inject
    AuthorisationPresenter(Interactor interactor){

        this.interactor = interactor;
    }

    void auth(String mail, String password){
        Disposable disposable = interactor.authUser(mail, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object -> {
                            getViewState().hideProgressBar();
                            if (object.equals(0)) getViewState().nextScreen();
                            if (object.equals(1)) getViewState().showError("Почта или пароль введены неверно");
                            if (object.equals(2)) getViewState().showError("Почта не подтверждена");
                        },
                        error -> {
                            getViewState().hideProgressBar();
                            getViewState().showError("Ошибка сети");
                        });
    }
}
