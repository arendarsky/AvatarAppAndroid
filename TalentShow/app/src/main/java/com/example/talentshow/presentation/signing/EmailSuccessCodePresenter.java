package com.example.talentshow.presentation.signing;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.talentshow.domain.Interactor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class EmailSuccessCodePresenter extends MvpPresenter<EmailSuccessCodeView> {

    private Interactor interactor;

    @Inject
    EmailSuccessCodePresenter(Interactor interactor){
        this.interactor = interactor;
    }

    void checkCode(String email, String code){
        Disposable disposable = this.interactor.confirmEmail(email, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(confirmationDTO -> getViewState().codeConfirmed(confirmationDTO.getKey()),
                        e -> getViewState().codeDiffers());
    }

}
