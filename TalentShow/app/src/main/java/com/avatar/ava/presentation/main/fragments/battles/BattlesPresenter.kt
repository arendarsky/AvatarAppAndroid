package com.avatar.ava.presentation.main.fragments.battles

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.avatar.ava.domain.Interactor
import com.avatar.ava.domain.entities.BattleDTO
import com.avatar.ava.domain.entities.BattleParticipant
import com.avatar.ava.domain.entities.Semifinalist
import com.avatar.ava.presentation.main.fragments.battles.item.BattleItem
import com.avatar.ava.presentation.main.fragments.battles.item.ParticipantItem
import com.avatar.ava.presentation.main.fragments.battles.item.SemifinalistItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class BattlesPresenter @Inject constructor(private val interactor: Interactor) : MvpPresenter<BattlesView>() {

    private lateinit var compositeDisposable: CompositeDisposable

    override fun attachView(view: BattlesView?) {
        super.attachView(view)
        compositeDisposable = CompositeDisposable()
        loadBattles()
    }

    fun loadBattles() {
        compositeDisposable.add(interactor.battles
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    viewState.enableUI()
                    it?.let { battles ->
                        val battleItems = battles.filterNotNull().mapToBattleItems()
                        viewState.setBattles(battleItems)
                    }
                }
                .doOnError { error: Throwable? ->
                    viewState.disableUI("Не удалось загрузить рейтинг. Попробуйте позже")
                }
                .subscribe { _, _ -> })
    }

    fun vote(battleId: Int, battleParticipant: ParticipantItem) {
        compositeDisposable.add(interactor.vote(battleId, battleParticipant.semifinalist.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                val votedSemifinalist =
                    battleParticipant.semifinalist.copy(isLikedByUser = it.isLikedByUser, votesNumber = it.votesNumber)
                val votedParticipant =
                    battleParticipant.copy(semifinalist = votedSemifinalist)

                viewState.vote(battleId, votedParticipant)
            }
            .doOnError { error: Throwable? ->
                viewState.disableUI("Не удалось загрузить рейтинг. Попробуйте позже")
            }
            .subscribe { _, _ -> })
    }

    override fun detachView(view: BattlesView?) {
        compositeDisposable.dispose()
        super.detachView(view)
    }

    private fun List<BattleParticipant>.mapToParticipantItems() : List<ParticipantItem> {
        return this.map {
            ParticipantItem(
                semifinalist = it.semifinalist.toSemifinalistItem(),
                id = it.id,
                name = it.name,
                description = it.description,
                profilePhoto = it.profilePhoto
            )
        }
    }

    private fun Semifinalist.toSemifinalistItem() : SemifinalistItem {
        return SemifinalistItem(
            id = this.id,
            videoName = this.videoName,
            votesNumber = this.votesNumber,
            isLikedByUser = this.isLikedByUser
        )
    }

    private fun List<BattleDTO>.mapToBattleItems() : List<BattleItem> {
        return this.map {
            BattleItem(
                id = it.id,
                endDate = it.endDate,
                votesNumber = it.totalVotesNumber,
                winnersNumber = it.winnerNumber,
                battleParticipants = it.battleParticipants.mapToParticipantItems().toMutableList()
            )
        }
    }
}