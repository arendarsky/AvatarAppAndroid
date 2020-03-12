package com.avatar.ava.presentation.main.fragments.profile;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;
import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.avatar.ava.presentation.main.fragments.rating.RatingView;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProfilePresenter extends MvpPresenter<ProfileView> {


    private Interactor interactor;

    @Inject
    ProfilePresenter(Interactor interactor) {
        this.interactor = interactor;
    }

    void getProfile(){
        Disposable disposable = interactor.getProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(person -> {
                            Log.d("ProfileFragmentLog", person.getPersonDTO().getName());
                            getViewState().setDataProfile(person);

                        },
                        error -> {
                            Log.d("ProfileFragmentLog", "error");});
    }
}
