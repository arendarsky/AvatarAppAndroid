package com.example.talentshow.presentation.signing.emailcode;

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
public class EmailSuccessCodePresenter extends MvpPresenter<EmailSuccessCodeView> {

    private Interactor interactor;
    private Router router;

    @Inject
    EmailSuccessCodePresenter(Interactor interactor, Router router){
        this.interactor = interactor;
        this.router = router;
    }

    void checkCode(String email, String code){
        Disposable disposable = this.interactor.confirmEmail(email, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    router.navigateTo(new Screens.StarNameEnterScreen());
//                    getViewState().codeConfirmed();
                        },
                        e -> getViewState().codeDiffers());
    }

}
