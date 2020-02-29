package com.example.talentshow.presentation.signing.fileload;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.talentshow.App;
import com.example.talentshow.R;
import com.example.talentshow.presentation.signing.RegAuthPostman;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;

public class FragmentFileLoad extends MvpAppCompatFragment implements MvpView {

    @Inject
    Context appContext;

    @BindView(R.id.fragment_file_load_progressbar)
    ProgressBar progressBar;

    @BindView(R.id.fragment_load_file_skip)
    TextView skip;

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
            progressBar.setVisibility(View.VISIBLE);
//            skip.setEnabled(false);
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

    @OnClick(R.id.fragment_load_file_skip)
    void skipLoad(){
        try {
            ((RegAuthPostman) activity).fragmentMessage(SKIP_AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.fragment_video_load_back)
    void backPressed(){
        try {
            ((RegAuthPostman) activity).fragmentMessage(BACK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProgressBarInvisible(){
        progressBar.setVisibility(View.INVISIBLE);
        skip.setVisibility(View.VISIBLE);
    }

    public void setSkipDisabled(){
        skip.setVisibility(View.INVISIBLE);
    }

//    @OnClick(R.id.activity_star_load_file_continue)
//    void continueClicked(){
//        Intent intent = new Intent(appContext, ActivityStarVideoBest.class);
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
