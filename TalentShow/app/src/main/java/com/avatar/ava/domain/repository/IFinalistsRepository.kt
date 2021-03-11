package com.avatar.ava.domain.repository

import com.avatar.ava.domain.entities.finalists.FinalDTO
import com.avatar.ava.domain.entities.finalists.FinalVoteResponse
import io.reactivex.Single

interface IFinalistsRepository {
    fun getFinal(): Single<FinalDTO>
    fun vote(finalistId: Int): Single<FinalVoteResponse>
}