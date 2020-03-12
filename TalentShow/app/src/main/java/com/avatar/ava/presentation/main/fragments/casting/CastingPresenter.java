package com.avatar.ava.presentation.main.fragments.casting;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;
import com.avatar.ava.domain.entities.PersonDTO;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class CastingPresenter extends MvpPresenter<CastingView> {

    private Interactor interactor;

    @Inject
    CastingPresenter(Interactor interactor) {
        this.interactor = interactor;
    }

//    String getNewVideoLink(){
//        return interactor.getNewVideoLink();
//    }

    void getFirstVideo(){
        Disposable disposable = interactor.getVideoLinkOnCreate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(person -> {
                    this.loadNewPerson(person);
                    getViewState().loadNewVideo(
                        "https://avatarapp.yambr.ru/api/video/"
                                + person.getUsedVideo().getName());
                },
                        error -> {
                    if (Objects.equals(error.getMessage(), "Empty list")){
                        getViewState().showNoMoreVideos();
                    }
                    Log.d("Casting presenter", error.getMessage());
                        });
    }

    void likeVideo(){
        Disposable disposable = interactor.setLiked(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .andThen(interactor.getNewVideoLink()).subscribe(
                        personDTO -> {
                            this.loadNewPerson(personDTO);
                            getViewState().loadNewVideo(
                                    "https://avatarapp.yambr.ru/api/video/" +
                                            personDTO.getUsedVideo().getName()
                            );
                        },
                        error -> {
                            if (Objects.equals(error.getMessage(), "Empty list")){
                                getViewState().showNoMoreVideos();
                            }
                            Log.d("Casting presenter", error.getMessage());
                        });
//                .subscribe(
//                .subscribe(
//                        () ->
//                        {
//                            PersonDTO person = interactor.getNewVideoLink();
//                            this.loadNewPerson(person);
//                            getViewState().loadNewVideo(
//                                    "https://avatarapp.yambr.ru/api/video/" +
//                                            person.getUsedVideo().getName()
//                            );
//                        },
//                throwable ->{
//                }
//                );
    }

    void dislikeVideo(){
        Disposable disposable = interactor.setLiked(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .andThen(interactor.getNewVideoLink()).subscribe(
                        personDTO -> {
                            this.loadNewPerson(personDTO);
                            getViewState().loadNewVideo(
                                    "https://avatarapp.yambr.ru/api/video/" +
                                            personDTO.getUsedVideo().getName()
                            );
                        },
                        error -> {
                            if (Objects.equals(error.getMessage(), "Empty list")){
                                getViewState().showNoMoreVideos();
                            }
                            Log.d("Casting presenter", error.getMessage());
                        });
//        Disposable disposable = interactor.setLiked(false)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(() -> {
//                            PersonDTO person = interactor.getNewVideoLink();
//                            this.loadNewPerson(person);
//                            getViewState().loadNewVideo(
//                                    "https://avatarapp.yambr.ru/api/video/" +
//                                            person.getUsedVideo().getName()
//                            );
//                        },
//                        throwable ->{
////                            if (throwable.getMessage().contains("500")) getViewState().showError(
////                                    "Ошибка на сервере. Повторите попытку позднее"
////                            );
////                            else if (throwable.getMessage().contains("401")) getViewState().showError(
////                                    "Чтобы оценивать видео необходима регистрация"
////                            );
//                        });
    }

    void loadNewPerson(PersonDTO person){
        getViewState().setAvatar(person.getPhoto());
        getViewState().setName(person.getName());
        getViewState().setDescription(person.getDescription());
    }
}

