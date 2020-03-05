package com.avatar.ava.presentation.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.avatar.ava.App;
import com.avatar.ava.R;

import javax.inject.Inject;

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
