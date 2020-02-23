package com.example.talentshow.presentation.signing.chooseauth;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.talentshow.App;
import com.example.talentshow.R;
import com.example.talentshow.presentation.signing.RegAuthPostman;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Router;
import toothpick.Toothpick;

public class ChooseAuthFragment extends Fragment {

    public static ChooseAuthFragment newInstance(){
        return new ChooseAuthFragment();
    }

    @Inject
    Context appContext;

    @Inject
    Router router;


    private Activity activity;
    private int START_REG = 0;
    private int START_AUTH = 1;
    private int SKIP_AUTH = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            activity = (Activity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.choose_auth_auth)
    public void authChoosed(){
        try {
            ((RegAuthPostman) activity).fragmentMessage(START_AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.choose_auth_reg)
    public void regChoosed() {
        try {
            ((RegAuthPostman) activity).fragmentMessage(START_REG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.choose_auth_skip)
    public void skip() {
        try {
            ((RegAuthPostman) activity).fragmentMessage(SKIP_AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
