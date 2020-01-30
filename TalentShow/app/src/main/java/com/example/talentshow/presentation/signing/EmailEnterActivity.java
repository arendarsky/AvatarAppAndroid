package com.example.talentshow.presentation.signing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.talentshow.App;
import com.example.talentshow.R;

import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import toothpick.Toothpick;

public class EmailEnterActivity extends MvpAppCompatActivity implements EmailEnterView{

    @Inject
    Context appContext;

    @BindView(R.id.activity_email_enter_mail)
    EditText mailEdit;

    @InjectPresenter
    EmailEnterPresenter presenter;

    @ProvidePresenter
    EmailEnterPresenter providePresenter(){
        return Toothpick.openScope(App.class).getInstance(EmailEnterPresenter.class);
    }

    private boolean buttonPressed = false;

    @BindView(R.id.activity_email_enter_progressbar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_enter);
        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @OnClick(R.id.activity_email_enter_continue)
    public void continueClicked(){
        buttonPressed = true;
        if(!Pattern.compile("\\w+@\\D+\\.\\D+")
                .matcher(mailEdit.getText().toString()).find()){
            mailEdit.setTextColor(getResources().getColor(R.color.red_text));
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            presenter.sendCodeToMail(mailEdit.getText().toString());
        }
    }

    @OnTextChanged(R.id.activity_email_enter_mail)
    public void mailEdited(){
        if (buttonPressed) {
            if (!Pattern.compile("\\w+@\\D+\\.\\D+")
                    .matcher(mailEdit.getText().toString()).find()) {
                mailEdit.setTextColor(getResources().getColor(R.color.red_text));
            } else mailEdit.setTextColor(getResources().getColor(R.color.blackText));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(appContext, ChooseRoleActivity.class));
        finish();
    }

    public void sendingSuccess(){
        progressBar.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(appContext, EmailSuccessCodeActivity.class);
        intent.putExtra("mail", mailEdit.getText().toString());
        intent.putExtra("role", getIntent().getBooleanExtra("role", true));
        startActivity(intent);
    }

    public void sendingFailure(){
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(appContext, "Connection error.\n Try later",
                Toast.LENGTH_SHORT).show();
    }
}
