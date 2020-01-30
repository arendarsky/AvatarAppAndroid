package com.example.talentshow.data;

import com.example.talentshow.data.api.AuthAPI;
import com.example.talentshow.domain.entities.ConfirmationDTO;
import com.example.talentshow.domain.entities.ConfirmationDTO;
import com.example.talentshow.domain.repository.IAuthRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Retrofit;

public class AuthRepository implements IAuthRepository {

    private AuthAPI authAPI;

    @Inject
    AuthRepository(Retrofit retrofit){
        this.authAPI = retrofit.create(AuthAPI.class);
    }

    @Override
    public Completable sendCodeToMail(String mail) {
        return authAPI.sendCode(mail);
    }

    @Override
    public Single<ConfirmationDTO> confirmMail(String mail, String code) {
        return authAPI.confirmEmail(mail, code);
    }
}
