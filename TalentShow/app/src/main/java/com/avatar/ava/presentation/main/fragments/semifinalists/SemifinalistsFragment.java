package com.avatar.ava.presentation.main.fragments.semifinalists;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.presentation.main.fragments.rating.RatingPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Toothpick;


public class SemifinalistsFragment extends MvpAppCompatFragment implements SemifinalistsView {


    @Inject
    Context appContext;

    @InjectPresenter
    SemifinalistsPresenter presenter;

    @ProvidePresenter
    SemifinalistsPresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(SemifinalistsPresenter.class);
    }

    @BindView(R.id.fragment_semifinalists_list)
    RecyclerView recyclerView;

    SemifinalistsAdapter adapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_semifinalists, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));

        adapter = new SemifinalistsAdapter(appContext);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);


    }
}