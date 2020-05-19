package com.avatar.ava.presentation.main.fragments.profile.profileSettings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.presentation.main.MainScreenActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;


@SuppressWarnings("FieldCanBeLocal")
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

    @BindView(R.id.fragment_profile_settings_info)
    TextView info;

    private MainScreenActivity activity;

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
        View v = inflater.inflate(R.layout.fragment_profile_settings, container, false);
        ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
        presenter.showEmail();
        email.setEnabled(false);
        //info.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnClick(R.id.fragment_profile_settings_info)
    public void onLinkClicked(){
        Uri adress= Uri.parse("https://vk.com/xcefactor2020");
        Intent browser = new Intent(Intent.ACTION_VIEW, adress);
        startActivity(browser);
    }

    @OnClick(R.id.fragment_profile_settings_password)
    public void changePassword(){
        activity = (MainScreenActivity) getActivity();
        if (activity != null) {
            activity.fragmentAction(PROFILE_CHANGE_PASSWORD);
        }
    }

    @Override
    public void setEmail(String emailText) {
        email.setText(emailText);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
    }
}