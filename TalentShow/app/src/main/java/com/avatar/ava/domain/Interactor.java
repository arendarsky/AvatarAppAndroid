package com.avatar.ava.domain;

import android.net.Uri;

import com.avatar.ava.domain.entities.PersonDTO;
import com.avatar.ava.domain.repository.IAuthRepository;
import com.avatar.ava.domain.repository.ISharedPreferemcesRepository;
import com.avatar.ava.domain.repository.IVideoRepository;
import com.avatar.ava.domain.repository.IRatingRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class Interactor {

    private IAuthRepository authRepository;
    private IVideoRepository videoRepository;
    private IRatingRepository ratingRepository;
    private ISharedPreferemcesRepository preferencesRepository;

    @Inject
    public Interactor(IAuthRepository authRepository, IVideoRepository videoRepository, ISharedPreferemcesRepository preferencesRepository, IRatingRepository ratingRepository){
        this.authRepository = authRepository;
        this.videoRepository = videoRepository;
        this.preferencesRepository = preferencesRepository;
        this.ratingRepository = ratingRepository;

    }

    public Single<ArrayList<String>> getRating(int number){
        return this.ratingRepository.getRating(number);
    }

    public Completable sendCodeToMail(String mail){
        return this.authRepository.sendCodeToMail(mail);
    }

    public Completable confirmEmail(String mail, String code){
        return this.authRepository.confirmMail(mail, code.replaceAll(" ", ""));
    }

    public Completable uploadVideo(Uri videoUri){
        return this.videoRepository.uploadVideo(videoUri);
    }

    public Completable setInterval(String fileName, int startTime, int endTime) {
        return this.videoRepository.setInterval(fileName, startTime, endTime);
    }

    public boolean checkAuth(){
        return this.preferencesRepository.checkAuth();
    }

    public void saveRole(String role){
        this.preferencesRepository.saveRole(role);
    }

    public String getRole(){
        return this.preferencesRepository.getRole();
    }

    public void saveName(String name){
        this.preferencesRepository.saveName(name);
    }

    public Single<ArrayList<PersonDTO>> getUnwatchedVideos(int number){
        return this.videoRepository.getUnwatchedVideos(number);
    }

    public Single<Object> authUser(String mail, String password){
        return this.authRepository.auth(mail, password);
    }

    public Single<Object> registerUser(String name, String mail, String password){
        return this.authRepository.registerUser(name, mail, password);
    }

    public Single<PersonDTO> getNewVideoLink(){
        return this.videoRepository.getNewVideoLink();
    }

    public Completable setLiked(boolean like){
        return this.videoRepository.setLiked(like);
    }

    public Single<PersonDTO> getVideoLinkOnCreate(){
        return this.videoRepository.getVideoLinkOnCreate();
    }
}
