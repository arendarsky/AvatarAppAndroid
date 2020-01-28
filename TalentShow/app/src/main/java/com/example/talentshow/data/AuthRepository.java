package com.example.talentshow.data;

import com.example.talentshow.domain.entities.ConfirmationDTO;
import com.example.talentshow.domain.repository.IAuthRepository;

import io.reactivex.Completable;
import io.reactivex.Single;

public class AuthRepository implements IAuthRepository {
    @Override
    public Completable senCodeToMail(String mail) {
        return null;
    }

    @Override
    public Single<ConfirmationDTO> confirmMail(String mail, String code) {
        return null;
    }
}
