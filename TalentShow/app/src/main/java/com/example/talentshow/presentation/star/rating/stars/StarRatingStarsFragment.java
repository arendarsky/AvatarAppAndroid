package com.example.talentshow.presentation.star.rating.stars;

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
import com.example.talentshow.domain.entities.PersonDTO;
import com.example.talentshow.presentation.star.rating.StarRatingAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StarRatingStarsFragment extends MvpAppCompatFragment implements StarRatingStarsView {

    @BindView(R.id.star_rating_recycler)
    RecyclerView recycler;

    private StarRatingAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static StarRatingStarsFragment newInstance(){ return new StarRatingStarsFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.star_rating_recycler, container, false);
        ButterKnife.bind(this, v);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new StarRatingAdapter();
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        PersonDTO person = new PersonDTO("Ivan", "Ivanov",
                "https://i.pinimg.com/originals/c6/e4/ff/c6e4ff2696c7e51ae4e2ffddceb80fef.jpg");
        adapter.addItem(person);
        adapter.addItem(person);
    }
}


