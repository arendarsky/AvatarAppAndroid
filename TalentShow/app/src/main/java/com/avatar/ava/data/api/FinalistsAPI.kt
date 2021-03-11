package com.avatar.ava.data.api

import com.avatar.ava.domain.entities.finalists.FinalDTO
import com.avatar.ava.domain.entities.finalists.FinalVoteResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FinalistsAPI {
    @GET("/api/final/get")
    fun getFinal(@Header("Authorization") token: String): Single<FinalDTO>

    @POST("/api/final/vote")
    fun vote(@Header("Authorization") token: String, @Body finalistId: Int): Single<Boolean>
}