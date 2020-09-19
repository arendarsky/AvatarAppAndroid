package com.avatar.ava.domain;

import android.net.Uri;

import com.avatar.ava.domain.entities.BattleDTO;
import com.avatar.ava.domain.entities.NotificationsDTO;
import com.avatar.ava.domain.entities.PersonDTO;
import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.avatar.ava.domain.entities.ProfileDTO;
import com.avatar.ava.domain.entities.ProfileSemifinalistsDTO;
import com.avatar.ava.domain.entities.PublicProfileDTO;
import com.avatar.ava.domain.repository.IAuthRepository;
import com.avatar.ava.domain.repository.IProfileRepository;
import com.avatar.ava.domain.repository.IRatingRepository;
import com.avatar.ava.domain.repository.ISemifinalistsRepository;
import com.avatar.ava.domain.repository.ISharedPreferencesRepository;
import com.avatar.ava.domain.repository.IVideoRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class Interactor {

    private IAuthRepository authRepository;
    private IVideoRepository videoRepository;
    private IRatingRepository ratingRepository;
    private IProfileRepository profileRepository;
    private ISharedPreferencesRepository preferencesRepository;
    private ISemifinalistsRepository semifinalistsRepository;

    @Inject
    public Interactor(IAuthRepository authRepository, IVideoRepository videoRepository, ISharedPreferencesRepository preferencesRepository,
                      IRatingRepository ratingRepository, IProfileRepository profileRepository, ISemifinalistsRepository semifinalistsRepository){
        this.authRepository = authRepository;
        this.videoRepository = videoRepository;
        this.preferencesRepository = preferencesRepository;
        this.ratingRepository = ratingRepository;
        this.profileRepository = profileRepository;
        this.semifinalistsRepository = semifinalistsRepository;
    }

    public Completable vote(String battleId, String semifinalistId){
        return semifinalistsRepository.vote(battleId, semifinalistId);
    }

    public Completable cancelVote(String battleId, String semifinalistId){
        return semifinalistsRepository.cancelVote(battleId, semifinalistId);
    }

    public Single<ArrayList<BattleDTO>> getBattles(){
        return semifinalistsRepository.getBattles();
    }

    public Completable updateProfile(String name, String description, String instagramLogin){
        return profileRepository.updateProfile(name, description, instagramLogin);
    }

    public Single<ArrayList<ProfileSemifinalistsDTO>> getSemifinalists(){
        return ratingRepository.getSemifinalists();
    }

    public Completable setFirebaseToken(String token){
        return authRepository.setFirebaseToken(token);
    }

    public Completable resetPassword(String email){
        return authRepository.resetPassword(email);
    }

    public Completable setInterval(String fileName, Float beginTime, Float endTime){
        return videoRepository.setInterval(fileName, beginTime, endTime);
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

    public Single<Boolean> setPassword(String oldPassword, String newPassword){
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

    public Completable uploadAndSetInterval(Uri fileUri, Float startTime, Float endTime){
        return this.videoRepository.uploadAndSetInterval(fileUri, startTime, endTime);
    }

    public boolean checkAuth(){
        return this.preferencesRepository.checkAuth();
    }

    public void saveName(String name){
        this.preferencesRepository.saveName(name);
    }

    public Single<Object> authUser(String mail, String password){
        return this.authRepository.auth(mail, password);
    }

    public Single<Object> registerUser(String name, String mail, String password, Boolean consentToGeneralEmail){
        return this.authRepository.registerUser(name, mail, password, consentToGeneralEmail);
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
        this.videoRepository.clearVideoList();
        this.preferencesRepository.exitAcc();
    }

    public Single<PublicProfileDTO> getPublicProfile(int id){
        return this.profileRepository.getPublicProfile(id);
    }

    public Uri getLoadingVideo(){
        return videoRepository.getLoadingVideo();
    }

    public Observable<Float> downloadVideo(String name, Uri uri) {
        return this.videoRepository.downloadVideo(name, uri);
    }
}
