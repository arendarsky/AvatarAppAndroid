package com.avatar.ava.presentation.main.fragments.rating;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.domain.entities.PersonDTO;
import com.avatar.ava.domain.entities.PersonRatingDTO;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Toothpick;

public class RatingFragment extends MvpAppCompatFragment implements RatingView {


    @Inject
    Context appContext;

    @InjectPresenter
    RatingPresenter presenter;

    @ProvidePresenter
    RatingPresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(RatingPresenter.class);
    }

    @BindView(R.id.rating_recycler)
    RecyclerView recycler;


    private ArrayList<PersonRatingDTO> data = new ArrayList<PersonRatingDTO>();

    private RatingAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static RatingFragment newInstance(){ return new RatingFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rating, container, false);
        ButterKnife.bind(this, v);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
        adapter = new RatingAdapter();
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        PersonDTO person = new PersonDTO(0 , "Ivan", "Ivanov", "",
                "https://i.pinimg.com/originals/c6/e4/ff/c6e4ff2696c7e51ae4e2ffddceb80fef.jpg");
        //adapter.addItem(person);
        //adapter.addItem(person);

        makeResponse();
        if(data != null)
        for(PersonRatingDTO el : data){
            Log.d("RatingFragmentLog", el.getLikesNumber() + "");
        }
    }

    public void setData(ArrayList<PersonRatingDTO> data){
        this.data = data;
        Log.d("RatingFragmentLog", this.data.size() + "  " + data.size());
        Log.d("RatingFragmentLog", data.get(0).getLikesNumber() + " " + data.get(0).getPersonDTO().getName());
        adapter.setItems(data);
    }

    public void makeResponse(){
        presenter.getRating();
        //Log.d("RatingFragmentLog", data.size() + " fragment");
    }


    public RatingAdapter getAdapter() {
        return adapter;
    }


}
