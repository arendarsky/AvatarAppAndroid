package com.avatar.ava.data

import com.avatar.ava.data.api.FinalistsAPI
import com.avatar.ava.domain.entities.finalists.FinalDTO
import com.avatar.ava.domain.entities.finalists.FinalVoteResponse
import com.avatar.ava.domain.repository.IFinalistsRepository
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

class FinalistsRepository @Inject constructor(
    retrofit: Retrofit,
    private val preferencesRepository: SharedPreferencesRepository
) : IFinalistsRepository {

    private var finalistsAPI: FinalistsAPI = retrofit.create(FinalistsAPI::class.java)

    override fun getFinal(): Single<FinalDTO> {
        return finalistsAPI.getFinal(preferencesRepository.token)
    }

    override fun vote(finalistId: Int): Single<FinalVoteResponse> {
        return finalistsAPI.vote(preferencesRepository.token, finalistId).map { FinalVoteResponse(it) }
    }
}