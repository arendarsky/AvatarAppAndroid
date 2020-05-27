package com.avatar.ava.presentation.main.fragments.rating;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplitude.api.Amplitude;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.avatar.ava.domain.entities.ProfileSemifinalistsDTO;
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

    @BindView(R.id.rating_recycler_semifinalists)
    RecyclerView recyclerSemifinalists;

    @BindView(R.id.fragment_rating_progressbar)
    ProgressBar progressBar;

    @BindView(R.id.rating_no_semifinalists)
    TextView noSemiText;

    private RatingAdapter adapter;
    private RatingSemifinalistsAdapter ratingSemifinalistsAdapter;

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

        noSemiText.setVisibility(View.INVISIBLE);
        recyclerSemifinalists.setVisibility(View.INVISIBLE);

        adapter = new RatingAdapter(appContext, (v, position) -> {
            Amplitude.getInstance().logEvent("ratingprofile_button_tapped");
            try {
                ((MainScreenPostman) activity).openPublicProfile(adapter.getPersonId(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        presenter.getRating();

        ratingSemifinalistsAdapter = new RatingSemifinalistsAdapter(appContext, (v, position) -> {
            try {
                ((MainScreenPostman) activity).openPublicProfile(ratingSemifinalistsAdapter.getPersonId(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        recyclerSemifinalists.setAdapter(ratingSemifinalistsAdapter);
        recyclerSemifinalists.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL,false));

        presenter.getSemifinalists();
    }

    public void setData(ArrayList<PersonRatingDTO> data){
        adapter.setItems(data);
    }

    @Override
    public void setSemifinalists(ArrayList<ProfileSemifinalistsDTO> data) {
        Log.d("RatingFragmentLog", String.valueOf(data == null));
        if(data != null && data.size() > 0){
            ratingSemifinalistsAdapter.setItems(data);
            noSemiText.setVisibility(View.INVISIBLE);
            recyclerSemifinalists.setVisibility(View.VISIBLE);
        }
        else{
            noSemiText.setVisibility(View.VISIBLE);
            recyclerSemifinalists.setVisibility(View.INVISIBLE);
        }
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
