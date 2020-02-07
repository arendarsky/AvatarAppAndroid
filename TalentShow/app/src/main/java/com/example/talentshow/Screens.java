package com.example.talentshow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.talentshow.presentation.star.rating.StarRatingFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {
    public static final class StarRatingScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            Bundle bundle = new Bundle();
            StarRatingFragment starRatingFragment = new StarRatingFragment();
            starRatingFragment.setArguments(bundle);
            return starRatingFragment;
        }
    }
}
