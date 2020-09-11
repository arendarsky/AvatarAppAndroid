package com.avatar.ava.presentation.main.fragments.semifinalists;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.domain.Interactor;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class SemifinalistsPresenter extends MvpPresenter<SemifinalistsView> {

    private Interactor interactor;

    @Inject
    SemifinalistsPresenter(Interactor interactor) {
        this.interactor = interactor;
    }
}
