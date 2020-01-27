package com.example.talentshow.domain;

import com.example.talentshow.domain.repository.IAuthRepository;

import javax.inject.Inject;

public class Interactor {

    private IAuthRepository authRepository;

    @Inject
    public Interactor(IAuthRepository authRepository){
        this.authRepository = authRepository;
    }
}
