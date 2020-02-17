package com.example.talentshow.presentation.signing.emailenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.talentshow.Screens;
import com.example.talentshow.domain.Interactor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class EmailEnterPresenter extends MvpPresenter<EmailEnterView> {

    private Interactor interactor;
    private Router router;


    @Inject
    EmailEnterPresenter(Interactor interactor, Router router){
        this.interactor = interactor;
        this.router = router;
    }

    void sendCodeToMail(String mail){
        Disposable disposable = interactor.sendCodeToMail(mail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    router.navigateTo(new Screens.EmailSuccessCodeScreen(mail));
//                    getViewState().sendingSuccess();
                        },
                        e -> {
                            Log.d("Auth", e.toString());
                    getViewState().sendingFailure();
                        });
    }
}
