package com.avatar.ava.data;

import com.avatar.ava.data.api.AuthAPI;
import com.avatar.ava.domain.entities.RegisterDTO;
import com.avatar.ava.domain.repository.IAuthRepository;


import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
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

    @Override
    public Single<Object> auth(String mail, String password) {
        return authAPI.authUser(mail, password)
                .flatMap(s -> {
                            if (!s.isConfirmationRequired()) {
                                if (s.getToken() == null) return Single.just(1);
                                else {
                                    preferencesRepository.saveToken(s.getToken());
                                    return Single.just(0);
                                }
                            }
                            else {
                                return Single.just(2);
                            }
                }
                );
    }

    @Override
    public Single<Object> registerUser(String name, String mail, String password) {

        return authAPI.registerUser(new RegisterDTO(name, mail, password))
                .flatMap(aBoolean -> {
                    if (aBoolean){
                        return authAPI.sendCode(mail).toSingleDefault(true);
//                        return authAPI.authUser(mail, password)
//                                .flatMap((Function<AuthResponse, SingleSource<?>>) s -> {
//                                    if (s.getToken().isEmpty()) return Single.just(false);
//                                    else
//                                        {
//                                            preferencesRepository.saveToken(s.getToken());
//                                            return Single.just(true);
//                                        }
//                                }
//                                );
                    }
                    else return Single.just(false);
                }
                );
    }


}
