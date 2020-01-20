package com.example.talentshow.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.talentshow.App;
import com.example.talentshow.R;
import com.example.talentshow.presentation.ActivityProducerYes;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;

public class ActivityProducerName extends AppCompatActivity {

    @Inject
    Context appContext;


    @OnClick(R.id.activity_producer_name_btn)
    void producerButtonClicked(){
        startActivity(new Intent(appContext, ActivityProducerYes.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_name);
        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }


}
