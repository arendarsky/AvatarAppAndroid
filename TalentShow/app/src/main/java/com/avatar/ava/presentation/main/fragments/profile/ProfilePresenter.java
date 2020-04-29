package com.avatar.ava.presentation.main.fragments.profile;

import android.net.Uri;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("unused")
@InjectViewState
public class ProfilePresenter extends MvpPresenter<ProfileView> {


    private Interactor interactor;
    private String errorMessage = "Произошла ошибка. Попробуйте позже";

    @Inject
    ProfilePresenter(Interactor interactor) {
        this.interactor = interactor;
    }

    void getProfile(){
        Disposable disposable = interactor.getProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(person -> {
                            if (this.getLoadingVideo() != null)
                                person.addLoadingVideo(this.getLoadingVideo());
                            getViewState().setDataProfile(person);
                            getViewState().hideProgressBar();
                            getViewState().update();
                        },
                        error -> {
                            getViewState().hideProgressBar();
                            getViewState().showMessage(errorMessage);
                        });
    }

    void setDescription(String description){
        Disposable disposable = interactor.setDescription(description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getViewState().hideProgressBar(),
                        error -> {
                            getViewState().hideProgressBar();
                            getViewState().showMessage(errorMessage);
                        });

    }

    void setName(String name){
        Disposable disposable = interactor.setName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getViewState().hideProgressBar(),
                        error -> getViewState().showMessage(errorMessage));
    }

    void uploadPhoto(Uri uri){
        Disposable disposable = interactor.uploadPhoto(uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            getViewState().hideProgressBar();
                            this.getProfile();
                        },
                        e -> {
                            if (Objects.equals(e.getMessage(), "Too big"))
                                getViewState().showMessage("Фото слишком большое");
                            else getViewState().showMessage(errorMessage);
                        });
    }

    void removeVideo(String name){
        Disposable disposable = interactor.removeVideo(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            getViewState().hideProgressBar();
                            this.getProfile();
                        },
                        error -> {
                            getViewState().hideProgressBar();
                            getViewState().showMessage(errorMessage);
                        });
    }

    void setActive(String fileName){
        Disposable disposable = interactor.setActive(fileName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            getViewState().hideProgressBar();
                            this.getProfile();
                        },
                        error -> {
                            getViewState().hideProgressBar();
                            getViewState().showMessage(errorMessage);
                        });
    }

    Uri getLoadingVideo() {
        return interactor.getLoadingVideo();
    }
}
