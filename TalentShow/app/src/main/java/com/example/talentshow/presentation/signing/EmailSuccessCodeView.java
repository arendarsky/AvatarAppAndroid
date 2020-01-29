package com.example.talentshow.presentation.signing;

import com.arellomobile.mvp.MvpView;

public interface EmailSuccessCodeView extends MvpView {

    void codeConfirmed(String token);
    void codeDiffers();
    void continueClicked();

}
