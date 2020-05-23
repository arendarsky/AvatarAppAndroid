package com.avatar.ava.presentation.signing.fragments.resetpassword;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.presentation.signing.RegAuthPostman;
import com.avatar.ava.presentation.signing.fragments.authorisation.AuthorisationPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;

public class ResetPasswordFragment extends MvpAppCompatFragment implements ResetPasswordView{

    private Activity activity;
    private final int AUTH = 1;

    @BindView(R.id.reset_email_edit)
    EditText mailET;

    @Inject
    Context appContext;

    @InjectPresenter
    ResetPasswordPresenter presenter;

    @ProvidePresenter
    ResetPasswordPresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(ResetPasswordPresenter.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_password, container, false);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(appContext, error, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.reset_fragment_btn)
    public void onResetPassClicked(){
        presenter.resetPassword(mailET.getText().toString());
    }

    @Override
    public void nextScreen() {
        try {
            ((RegAuthPostman) activity).fragmentMessage(AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) activity = (Activity) context;
    }
}
