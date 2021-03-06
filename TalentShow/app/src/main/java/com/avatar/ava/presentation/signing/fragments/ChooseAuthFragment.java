package com.avatar.ava.presentation.signing.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.avatar.ava.R;
import com.avatar.ava.presentation.signing.RegAuthPostman;

import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressWarnings("FieldCanBeLocal")
public class ChooseAuthFragment extends Fragment {

    private Activity activity;
    private int START_REG = 0;
    private int START_AUTH = 1;

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
    void authChosen(){
        try {
            ((RegAuthPostman) activity).fragmentMessage(START_AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.choose_auth_reg)
    void regChosen() {
        try {
            ((RegAuthPostman) activity).fragmentMessage(START_REG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
