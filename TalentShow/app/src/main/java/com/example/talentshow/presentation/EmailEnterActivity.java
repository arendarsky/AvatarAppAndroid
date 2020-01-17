package com.example.talentshow.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.talentshow.App;
import com.example.talentshow.R;

import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import toothpick.Toothpick;

public class EmailEnterActivity extends AppCompatActivity {

    @Inject
    Context appContext;

    @BindView(R.id.activity_email_enter_mail)
    EditText mailEdit;

    private boolean buttonPressed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_enter);
        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @OnClick(R.id.activity_email_enter_continue)
    void continueClicked(){
        buttonPressed = true;
        if(!Pattern.compile("\\w+@\\D+\\.\\D+")
                .matcher(mailEdit.getText().toString()).find()){
            mailEdit.setTextColor(getResources().getColor(R.color.red_text));
        }
        else startActivity(new Intent(appContext, EmailSuccessCodeActivity.class));
    }

    @OnTextChanged(R.id.activity_email_enter_mail)
    void mailEdited(){
        if (buttonPressed) {
            if (!Pattern.compile("\\w+@\\D+\\.\\D+")
                    .matcher(mailEdit.getText().toString()).find()) {
                mailEdit.setTextColor(getResources().getColor(R.color.red_text));
            } else mailEdit.setTextColor(getResources().getColor(R.color.blackText));
        }
    }
}
