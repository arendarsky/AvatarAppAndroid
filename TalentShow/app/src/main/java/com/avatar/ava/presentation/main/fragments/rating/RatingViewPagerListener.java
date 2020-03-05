package com.avatar.ava.presentation.main.fragments.rating;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.avatar.ava.R;

public class RatingViewPagerListener implements ViewPager.OnPageChangeListener {

    private Context appContext;
    private ConstraintLayout button;


    RatingViewPagerListener(Context appContext, ConstraintLayout button){
        this.appContext = appContext;
        this.button = button;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        if (position == 0) button.setBackground(appContext.getResources()
                .getDrawable(R.drawable.star_rating_viewpager_star));
        else button.setBackground(appContext.getResources()
                .getDrawable(R.drawable.star_rating_viewpager_producer));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
