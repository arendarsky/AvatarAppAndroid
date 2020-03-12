package com.avatar.ava.presentation.main.fragments.rating;

import com.arellomobile.mvp.MvpView;
import com.avatar.ava.domain.entities.PersonDTO;
import com.avatar.ava.domain.entities.PersonRatingDTO;

import java.util.ArrayList;

public interface RatingView extends MvpView {
    public void setData(ArrayList<PersonDTO> data);
}
