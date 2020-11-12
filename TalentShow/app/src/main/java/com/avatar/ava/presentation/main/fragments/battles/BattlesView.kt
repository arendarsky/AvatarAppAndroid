package com.avatar.ava.presentation.main.fragments.battles

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.avatar.ava.presentation.main.fragments.battles.item.BattleItem
import com.avatar.ava.presentation.main.fragments.battles.item.ParticipantItem


interface BattlesView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun disableUI(text: String)

    @StateStrategyType(SkipStrategy::class)
    fun enableUI()

    @StateStrategyType(SkipStrategy::class)
    fun vote(battleId: Int, battleParticipant: ParticipantItem)

    @StateStrategyType(SkipStrategy::class)
    fun setBattles(battles: List<BattleItem>)
}