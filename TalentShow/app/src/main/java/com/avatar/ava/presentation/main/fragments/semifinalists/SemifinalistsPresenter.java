package com.avatar.ava.presentation.main.fragments.semifinalists;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SemifinalistsPresenter extends MvpPresenter<SemifinalistsView> {

    private Interactor interactor;

    @Inject
    SemifinalistsPresenter(Interactor interactor) {
        this.interactor = interactor;
    }

    void getSemifinalists(){
        Disposable disposable = interactor.getSemifinalists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(arrayList -> {
                            //getViewState().hideProgressBar();
                            getViewState().setSemifinalists(arrayList);
                        },
                        error -> {
                            //getViewState().hideProgressBar();
                            getViewState().showMessage("Не удалось загрузить рейтинг. Попробуйте позже");
                        });
    }

    void getProfile(int id){
        Disposable disposable = interactor.getPublicProfile(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(person -> {
                            getViewState().setDataProfile(person);
                        },
                        error -> getViewState().showMessage("Упс. Произошла ошибка, попробуйте позже"));
    }
}
