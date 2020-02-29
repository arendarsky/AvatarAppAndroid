package com.example.talentshow.domain.repository;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IAuthRepository {
    Completable sendCodeToMail(String mail);
    Completable confirmMail(String mail, String code);
    Single<Object> auth(String mail, String password);
    Single<Object> registerUser(String name, String mail, String password);
}
