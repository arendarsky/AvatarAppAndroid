package com.example.talentshow.presentation.producer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.talentshow.App;
import com.example.talentshow.R;
import com.example.talentshow.presentation.CastingFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;

public class ActivityProducerYes extends AppCompatActivity {

    @Inject
    Context appContext;


    @OnClick(R.id.activity_producer_yes_button)
    void producerButtonClicked(){
        startActivity(new Intent(appContext, CastingFragment.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_yes);
        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }
}
