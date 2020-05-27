package com.avatar.ava.presentation.main.fragments.rating;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("unused")
@InjectViewState
public class RatingPresenter extends MvpPresenter<RatingView> {

    private Interactor interactor;

    @Inject
    RatingPresenter(Interactor interactor) {
        this.interactor = interactor;
    }

    void getRating(){
        Disposable disposable = interactor.getRating(20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(arrayList -> {
                            getViewState().hideProgressBar();
                            getViewState().setData(arrayList);
                        },
                        error -> {
                            getViewState().hideProgressBar();
                            getViewState().showMessage("Не удалось загрузить рейтинг. Попробуйте позже");
                        });


    }

    void getSemifinalists(){
        Disposable disposable = interactor.getSemifinalists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(arrayList -> {
                            getViewState().hideProgressBar();
                            getViewState().setSemifinalists(arrayList);
                        },
                        error -> {
                            getViewState().hideProgressBar();
                            getViewState().showMessage("Не удалось загрузить рейтинг. Попробуйте позже");
                        });
    }
}
