package com.avatar.ava.presentation.main.fragments.rating;

import com.arellomobile.mvp.MvpView;

import java.util.ArrayList;

public interface RatingView extends MvpView {
    void setData(ArrayList<String> data);
}
