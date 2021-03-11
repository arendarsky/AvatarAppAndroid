package com.avatar.ava.presentation.main.fragments.battles.item

data class BattleItem (
    val id: Int,
    val endDate: Int,
    var votesNumber: Int? = null,
    val winnersNumber: Int,
    val battleParticipants: MutableList<ParticipantItem>,
    var selectedParticipant: ParticipantItem = battleParticipants.first()
)