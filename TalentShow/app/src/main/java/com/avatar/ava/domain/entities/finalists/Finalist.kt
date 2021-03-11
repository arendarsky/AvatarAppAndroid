package com.avatar.ava.domain.entities.finalists

import com.google.gson.annotations.SerializedName

data class Finalist(
    @SerializedName("id")
    val id: Int,
    @SerializedName("isVotedByUser")
    val isVoted: Boolean,
    @SerializedName("contestant")
    val finalistInfo: FinalistInfo
)