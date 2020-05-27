package com.avatar.ava.presentation.main.fragments.rating;

import com.arellomobile.mvp.MvpView;
import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.avatar.ava.domain.entities.ProfileSemifinalistsDTO;

import java.util.ArrayList;

public interface RatingView extends MvpView {
    void setData(ArrayList<PersonRatingDTO> data);
    void setSemifinalists(ArrayList<ProfileSemifinalistsDTO> data);
    void hideProgressBar();
    void showMessage(String message);
}
