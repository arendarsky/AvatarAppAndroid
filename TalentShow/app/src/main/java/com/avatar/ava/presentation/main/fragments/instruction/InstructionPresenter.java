package com.avatar.ava.presentation.main.fragments.instruction;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.avatar.ava.R;
import com.avatar.ava.presentation.main.MainScreenTitles;

import javax.inject.Inject;

@InjectViewState
public class InstructionPresenter extends MvpPresenter<InstructionView> {

    private Context appContext;

    @Inject
    InstructionPresenter(Context appContext) {
        this.appContext = appContext;
    }

    void setInfo(MainScreenTitles title) {
        switch (title) {
            case RATING: {
                getViewState().setInfo(appContext.getResources().getString(R.string.info_rating));
                break;
            }
            case CASTING: {
                getViewState().setInfo(appContext.getResources().getString(R.string.info_casting));
                break;
            }
            case PROFILE: {
                getViewState().setInfo(appContext.getResources().getString(R.string.info_profile));
                break;
            }
            case NOTIFICATIONS: {
                getViewState().setInfo(appContext.getResources().getString(R.string.info_notify));
                break;
            }
        }
    }
}
