package com.example.talentshow.presentation.star.rating;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.talentshow.App;
import com.example.talentshow.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;

public class StarRatingFragment extends MvpAppCompatFragment implements StarRatingView{

    @BindView(R.id.star_rating_viewpager)
    ViewPager viewPager;

    @BindView(R.id.star_rating_star)
    TextView starButton;

    @BindView(R.id.star_rating_choose_button)
    ConstraintLayout button;

    @Inject
    Context appContext;

    private StarRatingPageAdapter pageAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pageAdapter = new StarRatingPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new StarRatingViewPagerListener(appContext, button));
        viewPager.setCurrentItem(0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.star_rating, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.star_rating_star)
    public void starButtonClicked(){
//        if (pageAdapter.getCurrentPosition()==0)
//            viewPager.arrowScroll(ViewPager.FOCUS_FORWARD);
            viewPager.setCurrentItem(0, true);
//        else
//            viewPager.arrowScroll(ViewPager.FOCUS_BACKWARD);
//            viewPager.setCurrentItem(0, true);
    }

    @OnClick(R.id.star_rating_producer)
    public void producerButtonClicked(){
        viewPager.setCurrentItem(1, true);
    }
}
