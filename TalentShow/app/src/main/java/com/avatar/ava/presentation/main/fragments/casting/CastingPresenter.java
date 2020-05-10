package com.avatar.ava.presentation.main.fragments.casting;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.avatar.ava.domain.Interactor;
import com.avatar.ava.domain.entities.PersonDTO;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.avatar.ava.DataModule.SERVER_NAME;

@SuppressWarnings({"ResultOfMethodCallIgnored", "unused"})
@InjectViewState
public class CastingPresenter extends MvpPresenter<CastingView> {

    private Interactor interactor;
    private PersonDTO currentPerson;


    @Inject
    CastingPresenter(Interactor interactor) {
        this.interactor = interactor;
    }

    @StateStrategyType(SkipStrategy.class)
    void getFirstVideo(){
        if (currentPerson != null){
            /*this.loadNewPerson(currentPerson);
            getViewState().loadNewVideo(currentPerson);*/
        }
        else {
            Disposable disposable = interactor.getVideoLinkOnCreate()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(person -> {
                                if (person.getVideo() != null) {
                                    this.loadNewPerson(person);
                                    getViewState().loadNewVideo(person);
                                } else getViewState().showNoMoreVideos();
                            },
                            error -> {
                                if (Objects.equals(error.getMessage(), "Empty list")) {
                                    getViewState().showNoMoreVideos();
                                }
                            });
        }
    }

    void likeVideo(){
        this.currentPerson = null;
        Disposable disposable = interactor.setLiked(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () ->
                                interactor.getNewVideoLink().subscribe(
                                        personDTO -> {
                                            this.loadNewPerson(personDTO);
                                            getViewState().loadNewVideo(personDTO);
                                        },
                                        error -> {
                                            if (Objects.equals(error.getMessage(), "Empty list")){
                                                getViewState().showNoMoreVideos();
                                            }
                                            Log.d("Casting presenter", Objects.requireNonNull(error.getMessage()));
                                        }),
                        error -> getViewState().showError("Не удалось оценить видео, попробуйте позже"));

    }

    void dislikeVideo(){
        this.currentPerson = null;
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
                                }),
                        error -> getViewState().showError("Не удалось оценить видео, попробуйте позже")
                );
    }

    private void loadNewPerson(PersonDTO person){

        this.currentPerson = person;

        if(person.getPhoto() != null)
            getViewState().setAvatar(SERVER_NAME + "/api/profile/photo/get/" + person.getPhoto());

        else getViewState().setAvatar("null");

        getViewState().setName(person.getName());
        getViewState().setDescription(person.getDescription());
    }

    int getPersonId(){
        return currentPerson.getId();
    }

    long getVideoBeginTime(){
        return (long) currentPerson.getVideo().getStartTime();
    }

    long getVideoEndTime(){
        return (long) currentPerson.getVideo().getEndTime();
    }

    public boolean checkPeronDTO(PersonDTO personDTO) {
        return personDTO == this.currentPerson;
    }
}

