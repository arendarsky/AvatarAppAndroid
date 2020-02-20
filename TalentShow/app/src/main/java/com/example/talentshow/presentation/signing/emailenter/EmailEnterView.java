package com.example.talentshow.presentation.signing.emailenter;

import com.arellomobile.mvp.MvpView;

public interface EmailEnterView extends MvpView {
    void continueClicked();
    void mailEdited();
    void sendingSuccess();
    void sendingFailure();
}
