package com.avatar.ava.presentation.signing.fragments.authorisation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.presentation.signing.RegAuthPostman;
import com.avatar.ava.R;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import toothpick.Toothpick;

public class AuthorisationFragment extends MvpAppCompatFragment implements AuthorisationView {

    private Activity activity;
    private final int AUTH_FINISHED = 4;
    private final int BACK = 7;

    @BindView(R.id.auth_email_edit)
    EditText mailEdit;

    @BindView(R.id.auth_password_edit)
    EditText passwordEdit;

    @Inject
    Context appContext;

    @InjectPresenter
    AuthorisationPresenter presenter;

    @ProvidePresenter
    AuthorisationPresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(AuthorisationPresenter.class);
    }

    public static AuthorisationFragment newInstance(){
        return new AuthorisationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    public AuthorisationFragment() {}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_authorisation, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) activity = (Activity) context;
    }

    @OnClick(R.id.fragment_auth_continue)
    public void continueClicked(){
        boolean flag = false;
        if (mailEdit.getText().length() == 0) {
            mailEdit.setTextColor(getResources().getColor(R.color.red_text));
            Toast.makeText(appContext,
                    getResources().getString(R.string.fields_empty), Toast.LENGTH_SHORT).show();
            flag = true;
        }
        if (passwordEdit.getText().length() == 0){
            passwordEdit.setTextColor(getResources().getColor(R.color.red_text));
            if (!flag)
                Toast.makeText(appContext,
                        getResources().getString(R.string.fields_empty), Toast.LENGTH_SHORT).show();
        }

        if (mailEdit.getText().length() > 0 && passwordEdit.getText().length() > 0) {
            presenter.auth(mailEdit.getText().toString(), passwordEdit.getText().toString());
        }
    }

    @Override
    public void nextScreen() {
        try {
            ((RegAuthPostman) activity).fragmentMessage(AUTH_FINISHED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.fragment_auth_back)
    void backClicked(){
        try {
            ((RegAuthPostman) activity).fragmentMessage(BACK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(appContext, error, Toast.LENGTH_SHORT).show();
    }

    @OnTextChanged(R.id.auth_email_edit)
    public void mailChanged(){
        if (mailEdit.getText().toString().length() >= 1)
            mailEdit.setTextColor(getResources().getColor(R.color.whiteText));
    }

    @OnTextChanged(R.id.auth_password_edit)
    public void passwordChanged(){
        if (passwordEdit.getText().toString().length() >= 1)
            passwordEdit.setTextColor(getResources().getColor(R.color.whiteText));
    }

}