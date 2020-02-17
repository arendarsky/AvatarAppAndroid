package com.example.talentshow.data;

import com.example.talentshow.data.api.AuthAPI;
import com.example.talentshow.domain.repository.IAuthRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import retrofit2.Retrofit;

public class AuthRepository implements IAuthRepository {

    private AuthAPI authAPI;
    private SharedPreferencesRepository preferencesRepository;

    @Inject
    AuthRepository(Retrofit retrofit, SharedPreferencesRepository preferencesRepository){
        this.authAPI = retrofit.create(AuthAPI.class);
        this.preferencesRepository = preferencesRepository;
    }

    @Override
    public Completable sendCodeToMail(String mail) {
        return authAPI.sendCode(mail);
    }

    @Override
    public Completable confirmMail(String mail, String code) {
        return authAPI.confirmEmail(mail, code).doAfterSuccess(confirmationDTO ->
                preferencesRepository.saveToken(confirmationDTO.getKey())).ignoreElement();
    }
}
