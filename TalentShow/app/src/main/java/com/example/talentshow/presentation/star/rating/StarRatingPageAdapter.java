package com.example.talentshow.presentation.star.rating;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.talentshow.presentation.star.rating.viewpager.StarRatingStarsFragment;

import javax.inject.Inject;

public class StarRatingPageAdapter extends FragmentPagerAdapter {

    public StarRatingPageAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        switch (position){
//            case 0:
//            case 1:
//
//        }
        return StarRatingStarsFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
