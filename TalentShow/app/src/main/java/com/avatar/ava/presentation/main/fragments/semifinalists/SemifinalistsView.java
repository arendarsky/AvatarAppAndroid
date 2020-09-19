package com.avatar.ava.presentation.main.fragments.semifinalists;

import com.arellomobile.mvp.MvpView;
import com.avatar.ava.domain.entities.BattleDTO;
import com.avatar.ava.domain.entities.BattleParticipant;
import com.avatar.ava.domain.entities.ProfileSemifinalistsDTO;
import com.avatar.ava.domain.entities.PublicProfileDTO;

import java.util.ArrayList;

public interface SemifinalistsView extends MvpView {
    void setSemifinalists(ArrayList<BattleParticipant> data);
    void showMessage(String text);
    void setDataProfile(PublicProfileDTO person);
    void setBattles(ArrayList<BattleDTO> battles);
}
