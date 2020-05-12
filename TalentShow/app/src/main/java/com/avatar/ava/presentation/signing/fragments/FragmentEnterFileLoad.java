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

import com.amplitude.api.Amplitude;
import com.avatar.ava.App;
import com.avatar.ava.R;

import com.avatar.ava.presentation.signing.RegAuthPostman;

import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;

@SuppressWarnings("FieldCanBeLocal")
public class FragmentEnterFileLoad extends Fragment{


    private Activity activity;
    private final int LOAD_VIDEO = 3;
    private final int BACK = 7;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_load_file, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) activity = (Activity) context;
    }

    @OnClick(R.id.fragment_file_load_button_add)
    void loadFileClicked(){
        Amplitude.getInstance().logEvent("newvideo_squared_button_tapped");
        try {
            ((RegAuthPostman) activity).fragmentMessage(LOAD_VIDEO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.file_load__back)
    void backPressed(){
        try {
            ((RegAuthPostman) activity).fragmentMessage(BACK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
