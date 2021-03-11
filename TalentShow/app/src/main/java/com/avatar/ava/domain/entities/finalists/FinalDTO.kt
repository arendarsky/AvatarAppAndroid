package com.avatar.ava.domain.entities.finalists

import com.google.gson.annotations.SerializedName

data class FinalDTO(
    @SerializedName("videoUrl")
    val videoUrl: String,
    @SerializedName("secondsUntilStart")
    val secondsUntilStart: Int,
    @SerializedName("secondsUntilEnd")
    val secondsUntilEnd: Int,
    @SerializedName("winnersNumber")
    val winnersNumber: Int,
    @SerializedName("finalists")
    val finalists: List<Finalist>
)