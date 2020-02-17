package com.example.talentshow.presentation.signing;

import android.util.Log;

import androidx.annotation.LongDef;
import androidx.annotation.MainThread;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.talentshow.domain.Interactor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class EmailEnterPresenter extends MvpPresenter<EmailEnterView> {

    private Interactor interactor;

    @Inject
    EmailEnterPresenter(Interactor interactor){
        this.interactor = interactor;
    }

    void sendCodeToMail(String mail){
        Disposable disposable = interactor.sendCodeToMail(mail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->getViewState().sendingSuccess(),
                        e -> {
                            Log.d("Auth", e.toString());
                    getViewState().sendingFailure();
                        });
    }

}
