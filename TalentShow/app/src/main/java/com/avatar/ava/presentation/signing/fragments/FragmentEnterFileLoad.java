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

import com.avatar.ava.App;
import com.avatar.ava.R;

import com.avatar.ava.presentation.signing.RegAuthPostman;

import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;

public class FragmentEnterFileLoad extends Fragment{

//    @Inject
//    Context appContext;

    private Activity activity;
    private final int LOAD_VIDEO = 3;
    private final int SKIP_AUTH = 2;
    private final int BACK = 7;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

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
        try {
            ((RegAuthPostman) activity).fragmentMessage(LOAD_VIDEO);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);


        // Start camera and wait for the results.
//        startActivityForResult(intent, REQUEST_ID_VIDEO_CAPTURE);

//        if (permissionAlreadyGranted()) {
//            Intent intentС = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//            startActivityForResult(intentС, REQUEST_ID_VIDEO_CAPTURE);
//            return;
//        }
//
//        requestPermission();
    }

    @OnClick(R.id.file_load__back)
    void backPressed(){
        try {
            ((RegAuthPostman) activity).fragmentMessage(BACK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    @OnClick(R.id.activity_star_load_file_continue)
//    void continueClicked(){
//        Intent intent = new Intent(appContext, FragmentChooseVideoBest.class);
//        intent.putExtra("VideoLink", link);
//        startActivity(intent);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("resultCodeVideo", resultCode + "");
//        if (resultCode == RESULT_OK && data != null){
//            Uri selectedVideo = data.getData();
//            Log.d("Video link", selectedVideo.toString());
//            link = selectedVideo.toString();
//            presenter.uploadVideoToServer(selectedVideo);
//        }
//        if(requestCode == REQUEST_ID_VIDEO_CAPTURE && data != null){
//            Uri selectedVideo = data.getData();
//            Log.d("Video link", selectedVideo.toString());
//            link = selectedVideo.toString();
//            presenter.uploadVideoToServer(selectedVideo);
//        }
//    }
}