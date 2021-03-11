package com.avatar.ava.presentation.main.fragments.battles.semifinalists

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.avatar.ava.domain.Interactor
import com.avatar.ava.domain.entities.semifinalists.BattleDTO
import com.avatar.ava.domain.entities.semifinalists.BattleParticipant
import com.avatar.ava.domain.entities.semifinalists.Semifinalist
import com.avatar.ava.presentation.main.fragments.battles.item.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class SemifinalistsPresenter @Inject constructor(private val interactor: Interactor)
    : MvpPresenter<SemifinalistsView>() {

    private lateinit var compositeDisposable: CompositeDisposable

    override fun attachView(view: SemifinalistsView?) {
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
                .doOnError {
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
                            battleParticipant.semifinalist.copy(
                                isLikedByUser = it.isLikedByUser,
                                votesNumber = it.votesNumber
                            )
                    val votedParticipant =
                            battleParticipant.copy(semifinalist = votedSemifinalist)

                    viewState.vote(battleId, votedParticipant)
                }
                .doOnError {
                    viewState.disableUI("Не удалось загрузить рейтинг. Попробуйте позже")
                }
                .subscribe { _, _ -> })
    }

    override fun detachView(view: SemifinalistsView?) {
        compositeDisposable.dispose()
        super.detachView(view)
    }

    private fun List<BattleParticipant>.mapToParticipantItems() : List<ParticipantItem> {
        return this.map {
            ParticipantItem(
                    semifinalist = it.semifinalist.toParticipantInfo(),
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    profilePhoto = it.profilePhoto
            )
        }
    }

    private fun Semifinalist.toParticipantInfo() : SemifinalistItem {
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
                    endDate = it.secondsUntilTheEnd,
                    votesNumber = it.totalVotesNumber,
                    winnersNumber = it.winnerNumber,
                    battleParticipants = it.battleParticipants.mapToParticipantItems().toMutableList()
            )
        }
    }
}