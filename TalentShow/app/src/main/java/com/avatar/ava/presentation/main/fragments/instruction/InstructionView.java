package com.avatar.ava.presentation.main.fragments.instruction;

import android.text.Spannable;

import com.arellomobile.mvp.MvpView;

interface InstructionView extends MvpView {
    void setInfoText(String text);
    void setInfoText(Spannable text);
}
