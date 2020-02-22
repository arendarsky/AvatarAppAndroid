package com.example.talentshow.presentation.signing.authorisation;

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
import androidx.fragment.app.Fragment;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.talentshow.App;
import com.example.talentshow.R;
import com.example.talentshow.presentation.signing.RegAuthPostman;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import toothpick.Toothpick;

public class AuthorisationFragment extends Fragment{

    private Activity activity;
    private final int AUTH_FINISHED = 4;

    @BindView(R.id.auth_email_edit)
    EditText mailEdit;

    @BindView(R.id.auth_password_edit)
    EditText passwordEdit;

    @Inject
    Context appContext;

    public static AuthorisationFragment newInstance(){
        return new AuthorisationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    private AuthorisationFragment() {}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_authorisation, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            activity = (Activity) context;
        }
    }

    @OnClick(R.id.fragment_auth_continue)
    void continueClicked(){
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

        else if (mailEdit.getText().length() > 0 && passwordEdit.getText().length() > 0) {
            try {
                ((RegAuthPostman) activity).fragmentMessage(AUTH_FINISHED);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnTextChanged(R.id.auth_email_edit)
    void mailChanged(){
        if (mailEdit.getText().toString().length() >= 1)
            mailEdit.setTextColor(getResources().getColor(R.color.blackText));
    }

    @OnTextChanged(R.id.auth_password_edit)
    void passwordChanged(){
        if (passwordEdit.getText().toString().length() >= 1)
            passwordEdit.setTextColor(getResources().getColor(R.color.blackText));
    }
}

