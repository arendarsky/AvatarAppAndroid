package com.avatar.ava.presentation.main.fragments.rating;

import android.util.Log;

import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;
import com.avatar.ava.domain.entities.PersonDTO;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RatingPresenter extends MvpPresenter<RatingView> {

    private Interactor interactor;

    @Inject
    RatingPresenter(Interactor interactor) {
        this.interactor = interactor;
    }

    public void getRating(){


        Disposable disposable = interactor.getRating(10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(arrayList -> {
                            Log.d("RatingFragmentLog", arrayList + "");
//                    getViewState().setData(arrayList);
                    },
                        error -> {
                            Log.d("RatingFragmentLog", "error");});


    }
}
