package com.example.talentshow.presentation.star.rating;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.talentshow.presentation.star.rating.producers.StarRatingProducersFragment;
import com.example.talentshow.presentation.star.rating.stars.StarRatingStarsFragment;

public class StarRatingPageAdapter extends FragmentPagerAdapter {

    public StarRatingPageAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return StarRatingStarsFragment.newInstance();
            case 1:
                Log.d("adapter", "1");
                return StarRatingProducersFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

}