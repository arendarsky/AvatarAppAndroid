package com.example.talentshow;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseRoleActivity extends AppCompatActivity {

    @BindView(R.id.role_choice_star_button)
    ConstraintLayout starButton;

    @BindView(R.id.role_choice_producer_button)
    ConstraintLayout producerButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    
}
