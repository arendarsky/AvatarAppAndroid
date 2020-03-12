package com.avatar.ava.presentation.main.fragments.profile;

import android.net.Uri;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;
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

    void setDesription(String description){
        Disposable disposable = interactor.setDescription(description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {});

    }


    void uploadPhoto(Uri uri){
        Disposable disposable = interactor.uploadPhoto(uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    getProfile();
                    Log.d("ProfileFragmentLog", "successPhotoUpload");},
                        e -> Log.d("ProfileFragmentLog", "errorPhotoUpload" + e.getMessage()));
    }
}
