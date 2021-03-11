package com.avatar.ava.domain.usecase.finalists

import com.avatar.ava.domain.entities.finalists.FinalDTO
import com.avatar.ava.domain.entities.finalists.FinalVoteResponse
import com.avatar.ava.domain.repository.IFinalistsRepository
import io.reactivex.Single
import javax.inject.Inject

class FinalUseCaseImpl @Inject constructor(
    private val repository: IFinalistsRepository
) : FinalUseCase {
    override fun getFinal(): Single<FinalDTO> {
        return repository.getFinal()
    }

    override fun vote(finalistId: Int): Single<FinalVoteResponse> {
        return repository.vote(finalistId)
    }
}