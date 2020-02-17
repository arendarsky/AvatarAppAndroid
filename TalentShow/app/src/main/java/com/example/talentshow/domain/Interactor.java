package com.example.talentshow.domain;

import android.net.Uri;

import com.example.talentshow.domain.repository.IAuthRepository;
import com.example.talentshow.domain.repository.ISharedPreferemcesRepository;
import com.example.talentshow.domain.repository.IVideoRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class Interactor {

    private IAuthRepository authRepository;
    private IVideoRepository videoRepository;
    private ISharedPreferemcesRepository preferemcesRepository;

    @Inject
    public Interactor(IAuthRepository authRepository, IVideoRepository videoRepository, ISharedPreferemcesRepository preferemcesRepository){
        this.authRepository = authRepository;
        this.videoRepository = videoRepository;
        this.preferemcesRepository = preferemcesRepository;
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

    public boolean checkAuth(){
        return this.preferemcesRepository.checkAuth();
    }

    public void saveRole(String role){
        this.preferemcesRepository.saveRole(role);
    }

    public String getRole(){
        return this.preferemcesRepository.getRole();
    }

    public void saveName(String name){
        this.preferemcesRepository.saveName(name);
    }
}
