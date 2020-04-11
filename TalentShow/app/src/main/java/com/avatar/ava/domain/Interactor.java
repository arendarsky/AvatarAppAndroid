package com.avatar.ava.domain;

import android.net.Uri;

import com.avatar.ava.domain.entities.NotificationsDTO;
import com.avatar.ava.domain.entities.PersonDTO;
import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.avatar.ava.domain.entities.ProfileDTO;
import com.avatar.ava.domain.entities.PublicProfileDTO;
import com.avatar.ava.domain.repository.IAuthRepository;
import com.avatar.ava.domain.repository.IProfileRepository;
import com.avatar.ava.domain.repository.ISharedPreferemcesRepository;
import com.avatar.ava.domain.repository.IVideoRepository;
import com.avatar.ava.domain.repository.IRatingRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class Interactor {

    private IAuthRepository authRepository;
    private IVideoRepository videoRepository;
    private IRatingRepository ratingRepository;
    private IProfileRepository profileRepository;
    private ISharedPreferemcesRepository preferencesRepository;

    @Inject
    public Interactor(IAuthRepository authRepository, IVideoRepository videoRepository, ISharedPreferemcesRepository preferencesRepository,
                      IRatingRepository ratingRepository, IProfileRepository profileRepository){
        this.authRepository = authRepository;
        this.videoRepository = videoRepository;
        this.preferencesRepository = preferencesRepository;
        this.ratingRepository = ratingRepository;
        this.profileRepository = profileRepository;
    }

    public Completable setActive(String fileName){
        return videoRepository.setActive(fileName);
    }

    public Completable removeVideo(String name){
        return this.profileRepository.removeVideo(name);
    }

    public Completable uploadPhoto(Uri photoUri){
        return this.profileRepository.uploadPhoto(photoUri);
    }

    public Completable setPassword(String oldPassword, String newPassword){
        return this.profileRepository.setPassword(oldPassword, newPassword);
    }

    public Completable setName(String name){
        return this.profileRepository.setName(name);
    }

    public Completable setDescription(String description){
        return this.profileRepository.setDescription(description);
    }

    public Single<ProfileDTO> getProfile(){
        return this.profileRepository.getProfile();
    }

    public Single<ArrayList<PersonRatingDTO>> getRating(int number){
        return this.ratingRepository.getRating(number);
    }

    public Completable sendCodeToMail(String mail){
        return this.authRepository.sendCodeToMail(mail);
    }

    public Completable confirmEmail(String mail, String code){
        return this.authRepository.confirmMail(mail, code.replaceAll(" ", ""));
    }

    public Completable composeVideo(Uri videoUri){
        return this.videoRepository.composeVideo(videoUri);
    }

    public Completable uploadAndSetInterval(Uri fileUri, Float startTime, Float endTime){
        return this.videoRepository.uploadAndSetInterval(fileUri, startTime, endTime);
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

    public Single<List<NotificationsDTO>> getLikes(int number, int skipNumber){
        return this.profileRepository.getNotifications(number, skipNumber);
    }

    public void exitFromAccount(){
        this.preferencesRepository.exitAcc();
    }

    public Single<PublicProfileDTO> getPublicProfile(int id){
        return this.profileRepository.getPublicProfile(id);
    }
}
