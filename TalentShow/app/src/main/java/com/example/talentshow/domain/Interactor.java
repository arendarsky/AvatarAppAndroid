package com.example.talentshow.domain;

import android.net.Uri;

import com.example.talentshow.domain.repository.IAuthRepository;
import com.example.talentshow.domain.repository.IVideoRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class Interactor {

    private IAuthRepository authRepository;
    private IVideoRepository videoRepository;


    @Inject
    public Interactor(IAuthRepository authRepository, IVideoRepository videoRepository){
        this.authRepository = authRepository;
        this.videoRepository = videoRepository;
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
}
