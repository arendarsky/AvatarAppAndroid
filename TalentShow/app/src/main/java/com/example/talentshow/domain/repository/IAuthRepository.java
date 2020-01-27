package com.example.talentshow.domain.repository;

import com.example.talentshow.domain.entities.ConfirmationDTO;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IAuthRepository {
    Completable senCodeToMail(String mail);
    Single<ConfirmationDTO> confirmMail(String mail, String code);
}
