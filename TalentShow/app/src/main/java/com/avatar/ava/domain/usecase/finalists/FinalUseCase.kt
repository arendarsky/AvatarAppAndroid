package com.avatar.ava.domain.usecase.finalists

import com.avatar.ava.domain.entities.finalists.FinalDTO
import com.avatar.ava.domain.entities.finalists.FinalVoteResponse
import io.reactivex.Single

interface FinalUseCase {
    fun getFinal() : Single<FinalDTO>
    fun vote(finalistId: Int) : Single<FinalVoteResponse>
}