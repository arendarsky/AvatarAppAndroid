package com.avatar.ava.presentation.main.fragments.profile.profileSettings.changePassword;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.presentation.main.MainScreenActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Toothpick;


public class ChangePasswordFragment extends MvpAppCompatFragment implements ChangePasswordView {

    private final int PROFILE_SETTINGS = 6;
    public static int ChangePasswordID;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ChangePasswordID = this.getId();
        //if(!this.isAdded())getActivity().getSupportFragmentManager().beginTransaction().add(this, "ProfileFragment1").commit();
        View v = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this, v);
        return v;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));

    }

    MainScreenActivity activity;
    @Override
    public void changePass() {
        activity = (MainScreenActivity) getActivity();
        activity.fragmentAction(PROFILE_SETTINGS);
    }

    public void changePassword(){
        presenter.changePassword(oldPassword.getText().toString(), newPassword.getText().toString());
    }
}
