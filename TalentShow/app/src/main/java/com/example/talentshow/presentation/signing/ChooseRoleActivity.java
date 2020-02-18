package com.example.talentshow.presentation.signing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.talentshow.App;
import com.example.talentshow.R;
import com.example.talentshow.presentation.star.fileload.ActivityStarFileLoad;
import com.example.talentshow.presentation.star.mainscreen.StarMainScreenActivity;
import com.example.talentshow.presentation.star.rating.StarRatingFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;
import toothpick.Toothpick;

public class ChooseRoleActivity extends AppCompatActivity {

    @BindView(R.id.role_choice_star_button)
    ConstraintLayout starButton;

    @BindView(R.id.role_choice_producer_button)
    ConstraintLayout producerButton;

    @Inject
    Context appContext;

    @Inject
    Router router;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_choice);
        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @OnClick(R.id.role_choice_star_button)
    void starButtonClicked(){
        Intent intent = new Intent(appContext, EmailEnterActivity.class);
        intent.putExtra("role", true);
        startActivity(intent);
    }

    @OnClick(R.id.role_choice_producer_button)
    void producerButtonClicked(){
        Intent intent = new Intent(appContext, EmailEnterActivity.class);
        intent.putExtra("role", false);
        startActivity(intent);
    }
}
