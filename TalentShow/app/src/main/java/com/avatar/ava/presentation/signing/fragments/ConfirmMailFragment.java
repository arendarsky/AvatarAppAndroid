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
public class ConfirmMailFragment extends Fragment {

    private Activity activity;

    private final int START_AUTH = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mail_confirm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) activity = (Activity) context;
    }


    @OnClick(R.id.fragment_mail_confirm_continue)
    void continueClicked(){
        try {
            ((RegAuthPostman) activity).fragmentMessage(START_AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
