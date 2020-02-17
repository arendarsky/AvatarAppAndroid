package com.example.talentshow.presentation.star.rating;

import android.content.ContentResolver;
import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.talentshow.R;

import javax.inject.Inject;

public class StarRatingViewPagerListener implements ViewPager.OnPageChangeListener {

    private Context appContext;
    private ConstraintLayout button;


    StarRatingViewPagerListener(Context appContext, ConstraintLayout button){
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
