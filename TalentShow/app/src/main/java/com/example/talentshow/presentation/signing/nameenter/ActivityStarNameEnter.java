package com.example.talentshow.presentation.signing.nameenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.talentshow.App;
import com.example.talentshow.R;
import com.example.talentshow.presentation.star.fileload.ActivityStarFileLoad;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import toothpick.Toothpick;

public class ActivityStarNameEnter extends AppCompatActivity {

    @Inject
    Context appContext;

    @BindView(R.id.activity_star_name_enter_name)
    EditText nameEdit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_name);
        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @OnClick(R.id.activity_star_name_enter_continue)
    void continueClicked(){
        if (nameEdit.getText().toString().length() >= 2){
            Intent intent = new Intent(appContext, ActivityStarFileLoad.class);
            startActivity(intent);
            finish();
        }
        else nameEdit.setTextColor(getResources().getColor(R.color.red_text));
    }

    @OnTextChanged(R.id.activity_star_name_enter_name)
    void textChanched(){
        if (nameEdit.getText().toString().length() >= 2)
            nameEdit.setTextColor(getResources().getColor(R.color.blackText));
    }
}
