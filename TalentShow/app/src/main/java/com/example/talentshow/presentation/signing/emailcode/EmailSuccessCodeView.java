package com.example.talentshow.presentation.signing.emailcode;

import com.arellomobile.mvp.MvpView;

public interface EmailSuccessCodeView extends MvpView {

    void codeConfirmed();
    void codeDiffers();
    void continueClicked();

}
