package com.avatar.ava.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.presentation.main.MainScreenActivity;
import com.avatar.ava.presentation.signing.EnterActivity;

import javax.inject.Inject;

import toothpick.Toothpick;

public class SplashActivity extends MvpAppCompatActivity implements SplashView {


    @Inject
    Context appContext;

    @InjectPresenter
    SplashPresenter presenter;

    @ProvidePresenter
    SplashPresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(SplashPresenter.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_splash);
        Toothpick.inject(this, Toothpick.openScope(App.class));
        presenter.checkAuth();
    }

    @Override
    public void startMain() {
        startActivity(new Intent(appContext, MainScreenActivity.class));
        finish();
    }

    @Override
    public void startEnter() {
        startActivity(new Intent(appContext, EnterActivity.class));
        finish();
    }
}
