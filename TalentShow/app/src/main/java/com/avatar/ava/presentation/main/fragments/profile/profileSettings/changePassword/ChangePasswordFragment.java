package com.avatar.ava.presentation.main.fragments.profile.profileSettings.changePassword;

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
import com.avatar.ava.R;
import com.avatar.ava.presentation.main.MainScreenPostman;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Toothpick;


@SuppressWarnings("FieldCanBeLocal")
public class ChangePasswordFragment extends MvpAppCompatFragment implements ChangePasswordView {

    private final int BACK = 9;
    public static int ChangePasswordID;
    private Activity activity;


    @Inject
    Context appContext;

    @InjectPresenter
    ChangePasswordPresenter presenter;

    @ProvidePresenter
    ChangePasswordPresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(ChangePasswordPresenter.class);
    }

    @BindView(R.id.fragment_change_profile_old_password_edit)
    EditText oldPassword;

    @BindView(R.id.fragment_change_profile_new_password_edit)
    EditText newPassword;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) activity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ChangePasswordID = this.getId();
        View v = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this, v);
        return v;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));

    }

    @Override
    public void changePass() {
        try {
            ((MainScreenPostman) activity).fragmentAction(BACK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
    }

    public void changePassword(){
        presenter.changePassword(oldPassword.getText().toString(), newPassword.getText().toString());
    }
}
