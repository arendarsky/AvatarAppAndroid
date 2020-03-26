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

import static com.avatar.ava.DataModule.SERVER_NAME;

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
                    if (person.getVideo() != null) {
                        this.loadNewPerson(person);
                        getViewState().loadNewVideo(person);
                    }
                    else getViewState().showNoMoreVideos();
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
                .subscribe(
                        () ->
//                .andThen(
                        interactor.getNewVideoLink().subscribe(
                        personDTO -> {
                            this.loadNewPerson(personDTO);
                            getViewState().loadNewVideo(personDTO);
                        },
                        error -> {
                            if (Objects.equals(error.getMessage(), "Empty list")){
                                getViewState().showNoMoreVideos();
                            }
                            Log.d("Casting presenter", error.getMessage());
                        }),
                error -> getViewState().showError("Не удалось оценить видео, попробуйте позже"));
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
                .subscribe(
                        () -> interactor.getNewVideoLink().subscribe(
                        personDTO -> {
                            this.loadNewPerson(personDTO);
                            getViewState().loadNewVideo(personDTO);
                        },
                        error -> {
                            if (Objects.equals(error.getMessage(), "Empty list")){
                                getViewState().showNoMoreVideos();
                            }
                            Log.d("Casting presenter", error.getMessage());
                        }),
                        error -> getViewState().showError("Не удалось оценить видео, попробуйте позже")
                        );
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
        if(person.getPhoto() != null){
            getViewState().setAvatar(SERVER_NAME + "/api/profile/photo/get" + person.getPhoto());
        }else{
            getViewState().setAvatar("null");
        }

        getViewState().setName(person.getName());
        getViewState().setDescription(person.getDescription());
    }
}

