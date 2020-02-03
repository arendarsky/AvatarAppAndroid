package com.example.talentshow.presentation.star;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.talentshow.App;
import com.example.talentshow.R;
import com.example.talentshow.presentation.CastingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Toothpick;

public class ActivityStarStatistics extends AppCompatActivity {

    @Inject
    Context appContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_statistics);

        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScope(App.class));



    }


}
