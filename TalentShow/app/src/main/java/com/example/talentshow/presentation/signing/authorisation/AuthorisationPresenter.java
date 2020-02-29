package com.example.talentshow.presentation.signing.authorisation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.talentshow.domain.Interactor;

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
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(object -> {
                    if (object.equals(true)) getViewState().nextScreen();
                    else getViewState().showError("Почта или пароль введены неверно");
                },
                error -> getViewState().showError("Ошибка сети"));
    }
}
