package com.avatar.ava.presentation.main.fragments.rating.producers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.avatar.ava.domain.entities.PersonDTO;
import com.avatar.ava.presentation.main.fragments.rating.RatingAdapter;
import com.avatar.ava.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StarRatingProducersFragment extends MvpAppCompatFragment
        implements StarRatingProducersView {

    @BindView(R.id.star_rating_recycler)
    RecyclerView recycler;

    private RatingAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static StarRatingProducersFragment newInstance(){ return new StarRatingProducersFragment();}

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
        adapter = new RatingAdapter();
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        PersonDTO person = new PersonDTO(0 , "Ivan", "Ivanov", "",
                "https://i.pinimg.com/originals/c6/e4/ff/c6e4ff2696c7e51ae4e2ffddceb80fef.jpg");
        //adapter.addItem(person);
        //adapter.addItem(person);
    }

}
