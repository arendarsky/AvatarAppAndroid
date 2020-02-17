package com.example.talentshow.presentation.signing.rolechoice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.talentshow.App;
import com.example.talentshow.R;
import com.example.talentshow.presentation.signing.emailenter.EmailEnterActivity;
import com.example.talentshow.presentation.star.mainscreen.StarMainScreenActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;
import toothpick.Toothpick;

public class ChooseRoleActivity extends MvpAppCompatActivity implements ChooseRoleView{

    @BindView(R.id.role_choice_star_button)
    ConstraintLayout starButton;

    @BindView(R.id.role_choice_producer_button)
    ConstraintLayout producerButton;

    @InjectPresenter
    ChooseRolePresenter presenter;

    @ProvidePresenter
    ChooseRolePresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(ChooseRolePresenter.class);
    }

    @Inject
    Context appContext;

    @Inject
    Router router;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.checkAuth();
        setContentView(R.layout.activity_role_choice);
        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @OnClick(R.id.role_choice_star_button)
    public void starButtonClicked(){
        presenter.setRole("star");
//        Intent intent = new Intent(appContext, StarMainScreenActivity.class);
//        intent.putExtra("role", true);
//        startActivity(intent);
    }

    @OnClick(R.id.role_choice_producer_button)
    public void producerButtonClicked(){
        presenter.setRole("producer");
//        Intent intent = new Intent(appContext, EmailEnterActivity.class);
//        intent.putExtra("role", false);
//        startActivity(intent);
    }
}
