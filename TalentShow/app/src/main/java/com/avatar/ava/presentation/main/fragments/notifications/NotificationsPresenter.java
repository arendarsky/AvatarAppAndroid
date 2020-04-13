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

    @Inject
    NotificationsPresenter(Interactor interactor){
        this.interactor = interactor;
    }

    void getLikes(){
        Disposable disposable = interactor.getLikes(20, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            if (list.isEmpty()) getViewState().showNoNotifText();
                            else getViewState().addLike(list);
                        },
                        error -> getViewState().showError("Сервер не отвечает. Попробуйте позже"));
    }
}
