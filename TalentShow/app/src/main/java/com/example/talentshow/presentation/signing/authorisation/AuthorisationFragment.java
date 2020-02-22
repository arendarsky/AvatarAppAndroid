package com.example.talentshow.presentation.signing.authorisation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.talentshow.App;
import com.example.talentshow.R;

import toothpick.Toothpick;

public class AuthorisationFragment extends MvpAppCompatFragment implements AuthorisationView{

    @InjectPresenter
    AuthorisationPresenter presenter;

    @ProvidePresenter
    AuthorisationPresenter providePresenter(){
        return Toothpick.openScope(App.class).getInstance(AuthorisationPresenter.class);
    }

    public static AuthorisationFragment newInstance(){
        return new AuthorisationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    private AuthorisationFragment() {}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_star_name, container, false);
    }
}
