package com.example.talentshow.presentation.star.rating.viewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.talentshow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StarRatingStarsFragment extends MvpAppCompatFragment implements StarRatingStarsView {

    @BindView(R.id.star_rating_recycler)
    RecyclerView recycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static StarRatingStarsFragment newInstance(){ return new StarRatingStarsFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.star_rating_recycler, container, false);
        ButterKnife.bind(this, v);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler.setAdapter(new StarRatingAdapter());
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}


