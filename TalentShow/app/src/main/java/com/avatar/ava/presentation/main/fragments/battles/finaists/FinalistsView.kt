package com.avatar.ava.presentation.main.fragments.battles.finaists

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.avatar.ava.domain.entities.finalists.FinalDTO
import com.avatar.ava.domain.entities.finalists.Finalist

interface FinalistsView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun disableUI(text: String)

    @StateStrategyType(SkipStrategy::class)
    fun enableUI()

    @StateStrategyType(SkipStrategy::class)
    fun vote(finalist: Finalist)

    @StateStrategyType(SkipStrategy::class)
    fun setBattle(battle: FinalDTO)
}