package com.avatar.ava.presentation.main.fragments.battles.listener

import com.avatar.ava.presentation.main.fragments.battles.item.ParticipantItem

interface RecyclerClickListener {
    fun voteClicked(battleId: Int, battleParticipant: ParticipantItem)
    fun selectClicked(battleId: Int, battleParticipant: ParticipantItem)
    fun detailClicked(battleParticipantId: Int)
}