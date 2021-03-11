package com.avatar.ava.data;

import com.avatar.ava.data.api.SemifinalistsAPI;
import com.avatar.ava.domain.entities.semifinalists.BattleDTO;
import com.avatar.ava.domain.entities.semifinalists.BattleVoteDTO;
import com.avatar.ava.domain.entities.semifinalists.BattleVoteResponse;
import com.avatar.ava.domain.repository.ISemifinalistsRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class SemifinalistsRepository implements ISemifinalistsRepository {

    private SemifinalistsAPI semifinalistsAPI;
    private SharedPreferencesRepository preferencesRepository;

    @Inject
    SemifinalistsRepository(Retrofit retrofit, SharedPreferencesRepository preferencesRepository){
        this.semifinalistsAPI = retrofit.create(SemifinalistsAPI.class);
        this.preferencesRepository = preferencesRepository;
    }

    @Override
    public Single<ArrayList<BattleDTO>> getBattles() {
        return semifinalistsAPI.getBattles(preferencesRepository.getToken());
    }

    @Override
    public Single<BattleVoteResponse> vote(int battleId, int semifinalistId) {
        return semifinalistsAPI.vote(preferencesRepository.getToken(), new BattleVoteDTO(battleId, semifinalistId));
    }
}
