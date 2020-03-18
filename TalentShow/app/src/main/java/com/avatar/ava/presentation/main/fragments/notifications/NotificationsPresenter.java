package com.avatar.ava.presentation.main.fragments.notifications;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;
import com.avatar.ava.domain.entities.NotificationsDTO;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
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
        Disposable disposable = interactor.getLikes(20, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            if (list.isEmpty()) getViewState().showNoNotifText();
                            else getViewState().addLike(list);
                        },
                        error -> getViewState().showError("Сервер не отвечает. Попробуйте позже"));
//                        notificationsDTO -> {
//                            getViewState().addLike(notificationsDTO);
//                            ++likesGetAmount;
//                        },
//                        error -> getViewState().showError("Сервер не отвечает. Попробуйте позже"),
//                        () -> {
//                            if (likesGetAmount == 0) getViewState().showNoNotifText();
//
//                        }
//                        new DisposableSingleObserver<List<NotificationsDTO>>() {
//                            @Override
//                            public void onSuccess(List<NotificationsDTO> notificationsDTOS) {
//                                if (notificationsDTOS.isEmpty()) getViewState().showNoNotifText();
//                                else getViewState().addLike(notificationsDTOS);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//
//                            }
//                        }
//
//                        error -> getViewState().showError("Сервер не отвечает. Попробуйте позже"),
//                        () -> getViewState().showNoNotifText()
    }
}
