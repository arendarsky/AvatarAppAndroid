package com.avatar.ava.presentation.main.fragments.rating;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.avatar.ava.presentation.main.MainScreenPostman;

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

    @BindView(R.id.fragment_rating_progressbar)
    ProgressBar progressBar;

    private RatingAdapter adapter;

    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rating, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) activity = (Activity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));

        adapter = new RatingAdapter(appContext, (v, position) -> {
            try {
                ((MainScreenPostman) activity).openPublicProfile(adapter.getPersonId(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        presenter.getRating();

    }

    public void setData(ArrayList<PersonRatingDTO> data){
        adapter.setItems(data);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.stopPlayer();
    }
}
