package com.avatar.ava.presentation.main.fragments.profile.profileSettings;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.avatar.ava.presentation.main.fragments.profile.ProfilePresenter;
import com.avatar.ava.presentation.main.fragments.profile.ProfileView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;


public class ProfileSettingsFragment extends MvpAppCompatFragment implements ProfileSettingsView {

    private final int PROFILE_CHANGE_PASSWORD = 7;

    @Inject
    Context appContext;

    @InjectPresenter
    ProfileSettingsPresenter presenter;

    @ProvidePresenter
    ProfileSettingsPresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(ProfileSettingsPresenter.class);
    }

    @BindView(R.id.fragment_profile_settings_email_edit)
    EditText email;

    @BindView(R.id.fragment_profile_settings_password_edit)
    EditText password;


    public ProfileSettingsFragment() {
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

        //if(!this.isAdded())getActivity().getSupportFragmentManager().beginTransaction().add(this, "ProfileFragment1").commit();
        View v = inflater.inflate(R.layout.fragment_profile_settings, container, false);
        ButterKnife.bind(this, v);
        return v;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
        email.setEnabled(false);
        //password.setEnabled(false);
    }

    MainScreenActivity activity;
    @OnClick(R.id.fragment_profile_settings_field_1)
    public void changePassword(){
         activity = (MainScreenActivity) getActivity();
         activity.fragmentAction(PROFILE_CHANGE_PASSWORD);
        Log.d("ProfileSettings", "click");
    }
}
