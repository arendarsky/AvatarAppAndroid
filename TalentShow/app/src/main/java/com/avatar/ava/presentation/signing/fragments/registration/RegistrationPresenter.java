package com.avatar.ava.presentation.signing.fragments.registration;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class RegistrationPresenter extends MvpPresenter<RegistrationView> {

    private Interactor interactor;

    @Inject
    public RegistrationPresenter(Interactor interactor) {
        this.interactor = interactor;
    }

    void registerUser(String name, String mail, String password){
        Disposable disposable = interactor.registerUser(name, mail, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(flag -> {
                            if (flag.equals(true)) getViewState().nextScreen();
                            else getViewState().showError("Такой пользователь уже существует");
                        },
                    e -> getViewState().showError("Ошибка сети")
                );
    }
}
