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

public class FragmentFileLoadJust extends Fragment {

    private Activity activity;

    private final int SKIP_AUTH = 2;
    private final int VIDEO_SCREEN = 6;
    private final int BACK = 7;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_choose_just, container, false);
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

    @OnClick(R.id.file_load_just_back)
    void backClicked(){
        try {
            ((RegAuthPostman) activity).fragmentMessage(BACK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.fragment_video_load_just_skip)
    void skipClicked(){
        try {
            ((RegAuthPostman) activity).fragmentMessage(SKIP_AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.fragment_file_load_just_button_add)
    void addVideo(){
        try {
            ((RegAuthPostman) activity).fragmentMessage(VIDEO_SCREEN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
