package com.avatar.ava.domain.entities.finalists

import com.google.gson.annotations.SerializedName

data class FinalistInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("profilePhoto")
    val profilePhoto: String
)