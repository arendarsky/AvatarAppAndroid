package com.example.talentshow.presentation.signing.registration;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.example.talentshow.App;
import com.example.talentshow.R;
import com.example.talentshow.presentation.signing.RegAuthPostman;
import com.googlecode.mp4parser.authoring.Edit;

import java.util.regex.Pattern;

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

    @BindView(R.id.reg_name_edit)
    EditText nameEdit;

    @BindView(R.id.reg_email_edit)
    EditText emailEdit;

    @BindView(R.id.reg_password_edit)
    EditText passwordEdit;

    @BindView(R.id.reg_load_photo)
    ImageView addAvatarButton;

    @InjectPresenter
    RegistrationPresenter presenter;

    @ProvidePresenter
    RegistrationPresenter providePresenter(){
        return Toothpick.openScope(App.class).getInstance(RegistrationPresenter.class);
    }

    private boolean continuePressed = false;
    private Activity activity;
    private final int AUTH_FINISHED = 4;
    private final int LOAD_AVATAR = 5;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Glide.with(view.getContext())
                .load(R.drawable.reg_load_avatar)
                .into(addAvatarButton);
    }

    @OnClick(R.id.fragment_reg_continue)
    void continueClicked(){
        continuePressed = true;
        boolean flag = true;
        if (nameEdit.getText().toString().length() < 2){
            nameEdit.setTextColor(getResources().getColor(R.color.red_text));
            Toast.makeText(appContext,
                    "Имя должно быть длиннее 1 символа", Toast.LENGTH_SHORT).show();
            flag = false;
        }

        if (!Pattern.compile("\\w+@\\D+\\.\\D+")
                .matcher(emailEdit.getText().toString()).find()){
            emailEdit.setTextColor(getResources().getColor(R.color.red_text));
            if (flag) Toast.makeText(appContext,
                    "Почта введена некорректно", Toast.LENGTH_SHORT).show();
            flag = false;
        }

        if (passwordEdit.getText().length() < 6){
            passwordEdit.setTextColor(getResources().getColor(R.color.red_text));
            if (flag) Toast.makeText(appContext,
                    "Пароль должен быть длиннее 5 символов", Toast.LENGTH_SHORT).show();
        }
        if (nameEdit.getText().toString().length() >= 2
                && Pattern.compile("\\w+@\\D+\\.\\D+")
                        .matcher(emailEdit.getText().toString()).find()
                && passwordEdit.getText().length() >= 6)
            try {
                ((RegAuthPostman) activity).fragmentMessage(AUTH_FINISHED);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @OnClick(R.id.reg_load_photo)
    void loadAvatarClicked(){
        try {
            ((RegAuthPostman) activity).fragmentMessage(LOAD_AVATAR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnTextChanged(R.id.reg_name_edit)
    void nameChanged(){
        if (nameEdit.getText().toString().length() >= 2)
            nameEdit.setTextColor(getResources().getColor(R.color.blackText));
    }

    @OnTextChanged(R.id.reg_email_edit)
    void mailChanged(){
        if (continuePressed) {
            if (!Pattern.compile("\\w+@\\D+\\.\\D+")
                    .matcher(emailEdit.getText().toString()).find()) {
                emailEdit.setTextColor(getResources().getColor(R.color.red_text));
            } else emailEdit.setTextColor(getResources().getColor(R.color.blackText));
        }
    }

    @OnTextChanged(R.id.reg_password_edit)
    void passwordChanged(){
        if (passwordEdit.getText().toString().length() >= 6)
            passwordEdit.setTextColor(getResources().getColor(R.color.blackText));
    }
}
