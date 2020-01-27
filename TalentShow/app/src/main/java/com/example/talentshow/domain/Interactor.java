package com.example.talentshow.domain;

import com.example.talentshow.domain.entities.ConfirmationDTO;
import com.example.talentshow.domain.repository.IAuthRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class Interactor {

    private IAuthRepository authRepository;

    @Inject
    public Interactor(IAuthRepository authRepository){
        this.authRepository = authRepository;
    }


    public Completable sendCodeToMail(String mail){
        return this.authRepository.sendCodeToMail(mail);
    }

    public Single<ConfirmationDTO> confirmEmail(String mail, String code){
        return this.authRepository.confirmMail(mail, code);
    }
}
