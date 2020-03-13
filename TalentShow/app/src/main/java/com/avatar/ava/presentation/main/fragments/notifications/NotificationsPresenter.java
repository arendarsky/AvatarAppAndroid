package com.avatar.ava.presentation.main.fragments.notifications;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NotificationsPresenter extends MvpPresenter<NotificationsView> {

    private Interactor interactor;
    private int likesGetAmount = 0;

    @Inject
    NotificationsPresenter(Interactor interactor){
        this.interactor = interactor;
    }

    void getLikes(){
        likesGetAmount = 0;
        Disposable disposable = interactor.getLikes(20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(notificationsDTO -> {
                    getViewState().addLike(notificationsDTO);
                    ++likesGetAmount;
                        },
                        error -> getViewState().showError("Сервер не отвечает. Попробуйте позже"),
                        () -> getViewState().showNoNotifText()
//                        () ->  {
//                    if (likesGetAmount == 0) getViewState().showNoNotifText();
//                        }
                    );
    }
}
