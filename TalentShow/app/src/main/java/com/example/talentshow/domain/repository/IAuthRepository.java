package com.example.talentshow.domain.repository;

import io.reactivex.Completable;

public interface IAuthRepository {
    Completable sendCodeToMail(String mail);
    Completable confirmMail(String mail, String code);
    Completable auth(String mail, String password);
}
