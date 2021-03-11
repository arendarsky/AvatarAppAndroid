package com.avatar.ava.presentation.main.fragments.battles.finaists

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.avatar.ava.domain.entities.finalists.Finalist
import com.avatar.ava.domain.usecase.finalists.FinalUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class FinalistsPresenter @Inject constructor(private val finalUseCase: FinalUseCase)
    : MvpPresenter<FinalistsView>() {

    private lateinit var compositeDisposable: CompositeDisposable

    override fun attachView(view: FinalistsView?) {
        super.attachView(view)
        compositeDisposable = CompositeDisposable()
        loadBattle()
    }

    fun loadBattle() {
        compositeDisposable.add(
                finalUseCase.getFinal()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess {
                            viewState.enableUI()
                            it?.let { finalDTO ->
                                viewState.setBattle(finalDTO)
                            }
                        }
                        .doOnError {
                            viewState.disableUI("Не удалось загрузить рейтинг. Попробуйте позже")
                        }
                        .subscribe { _, _ -> }
        )
    }

    fun vote(finalist: Finalist) {
        compositeDisposable.add(
                finalUseCase.vote(finalist.id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess {
                            viewState.vote(
                                finalist.copy(
                                    isVoted = it.isLikedByUser
                                )
                            )
                        }
                        .doOnError {}
                        .subscribe { _, _ -> }
        )
    }

    override fun detachView(view: FinalistsView?) {
        compositeDisposable.dispose()
        super.detachView(view)
    }
}