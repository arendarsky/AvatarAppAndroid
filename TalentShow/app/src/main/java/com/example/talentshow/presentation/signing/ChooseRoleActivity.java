package com.example.talentshow.presentation.signing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.talentshow.App;
import com.example.talentshow.R;
import com.example.talentshow.presentation.producer.ActivityProducerName;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;

public class ChooseRoleActivity extends AppCompatActivity {

    @BindView(R.id.role_choice_star_button)
    ConstraintLayout starButton;

    @BindView(R.id.role_choice_producer_button)
    ConstraintLayout producerButton;

    @Inject
    Context appContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_choice);
        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @OnClick(R.id.role_choice_star_button)
    void starButtonClicked(){
        startActivity(new Intent(appContext, EmailEnterActivity.class));
    }

    @OnClick(R.id.role_choice_producer_button)
    void producerButtonClicked(){
        startActivity(new Intent(appContext, ActivityProducerName.class));
    }
}
