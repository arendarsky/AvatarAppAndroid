package com.avatar.ava.presentation.main.fragments.rating;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;
import com.avatar.ava.domain.entities.PersonDTO;
import com.avatar.ava.domain.entities.PersonRatingDTO;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class RatingPresenter extends MvpPresenter<RatingView> {

    private Interactor interactor;
    private ArrayList<PersonRatingDTO> data = new ArrayList<PersonRatingDTO>();

    @Inject
    RatingPresenter(Interactor interactor) {
        this.interactor = interactor;
    }

    public void getRating(){


        Disposable disposable = interactor.getRating(10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(arrayList -> {
                            Log.d("RatingFragmentLog", arrayList.size() + " arrayList");
                            getViewState().setData(arrayList);
                            //data.addAll(arrayList);
                            //Log.d("RatingFragmentLog", data.size() + " data");

                    },
                        error -> {
                            Log.d("RatingFragmentLog", "error");});


    }

    public ArrayList<PersonRatingDTO> getData(){
        Log.d("RatingFragmentLog", data.size() + " presenter");
        return this.data;
    }
}
