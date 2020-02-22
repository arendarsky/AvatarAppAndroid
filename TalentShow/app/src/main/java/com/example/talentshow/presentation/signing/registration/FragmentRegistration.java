package com.example.talentshow.presentation.signing.registration;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.talentshow.App;
import com.example.talentshow.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import toothpick.Toothpick;

public class FragmentRegistration extends MvpAppCompatFragment implements RegistrationView {

    public static FragmentRegistration newInstance(){
        return new FragmentRegistration();
    }

    public FragmentRegistration(){}

    @Inject
    Context appContext;

    @BindView(R.id.activity_star_name_enter_name)
    EditText nameEdit;


    @InjectPresenter
    RegistrationPresenter presenter;

    @ProvidePresenter
    RegistrationPresenter providePresenter(){
        return Toothpick.openScope(App.class).getInstance(RegistrationPresenter.class);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_star_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.activity_star_name_enter_continue)
    void continueClicked(){
        if (nameEdit.getText().toString().length() >= 2){
//            Intent intent = new Intent(appContext, ActivityStarFileLoad.class);
//            startActivity(intent);
//            finish();
        }
        else nameEdit.setTextColor(getResources().getColor(R.color.red_text));
    }

    @OnTextChanged(R.id.activity_star_name_enter_name)
    void textChanched(){
        if (nameEdit.getText().toString().length() >= 2)
            nameEdit.setTextColor(getResources().getColor(R.color.blackText));
    }
}
