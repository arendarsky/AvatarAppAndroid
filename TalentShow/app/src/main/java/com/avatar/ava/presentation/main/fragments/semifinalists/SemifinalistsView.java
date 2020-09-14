package com.avatar.ava.presentation.main.fragments.semifinalists;

import com.arellomobile.mvp.MvpView;
import com.avatar.ava.domain.entities.ProfileSemifinalistsDTO;
import com.avatar.ava.domain.entities.PublicProfileDTO;

import java.util.ArrayList;

public interface SemifinalistsView extends MvpView {
    void setSemifinalists(ArrayList<ProfileSemifinalistsDTO> data);
    void showMessage(String text);
    void setDataProfile(PublicProfileDTO person);
}
