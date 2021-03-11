package com.avatar.ava.presentation.main.fragments.battles

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.avatar.ava.domain.Interactor
import com.avatar.ava.domain.usecase.finalists.FinalUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class BattlesPresenter @Inject constructor(
    private val interactor: Interactor,
    private val finalUseCase: FinalUseCase
) : MvpPresenter<BattlesView>() {

    //private lateinit var compositeDisposable: CompositeDisposable

    override fun attachView(view: BattlesView?) {
        super.attachView(view)
        viewState.addSemifinalTab()
        viewState.addFinalTab()
        //compositeDisposable = CompositeDisposable()
        //loadBattles()
    }

    /*fun loadBattles() {
        loadFinalBattle()
        loadSemifinalBattles()
    }*/

    /*private fun loadSemifinalBattles() {
        compositeDisposable.add(interactor.battles
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    it?.let {
                        viewState.addSemifinalTab()
                    }
                }
                .doOnError {}
                .subscribe { _, _ -> })
    }*/

    /*private fun loadFinalBattle() {
        compositeDisposable.add(
            finalUseCase.getFinal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    it?.let { finalDTO ->
                        /*if (finalDTO.isVotingStarted) {
                            viewState.addFinalTab()
                        }*/
                        //
                        viewState.addFinalTab()
                        //
                    }
                }
                .doOnError {}
                .subscribe { _, _ -> }
        )
    }*/

    /*override fun detachView(view: BattlesView?) {
        compositeDisposable.dispose()
        super.detachView(view)
    }*/
}