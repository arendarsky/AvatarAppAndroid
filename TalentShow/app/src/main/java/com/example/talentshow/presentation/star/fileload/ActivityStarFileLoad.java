package com.example.talentshow.presentation.star.fileload;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.talentshow.App;
import com.example.talentshow.R;
import com.example.talentshow.presentation.CastingActivity;
import com.example.talentshow.presentation.signing.nameenter.ActivityStarNameEnter;
import com.example.talentshow.presentation.star.ActivityStarVideoBest;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;

public class ActivityStarFileLoad extends MvpAppCompatActivity implements StarFileLoadView{

    @InjectPresenter
    StarFileLoadPresenter presenter;

    @ProvidePresenter
    StarFileLoadPresenter providePresenter(){
        return Toothpick.openScope(App.class).getInstance(StarFileLoadPresenter.class);
    }

    @Inject
    Context appContext;

    String link = "";
    int REQUEST_ID_VIDEO_CAPTURE = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_load_file);
        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @OnClick(R.id.activity_star_load_file_circle)
    void loadFileClicked(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select video"), 1);
//        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);


        // Start camera and wait for the results.
//        startActivityForResult(intent, REQUEST_ID_VIDEO_CAPTURE);
    }

    @OnClick(R.id.activity_star_load_file_continue)
    void continueClicked(){
        Intent intent = new Intent(appContext, ActivityStarVideoBest.class);
        intent.putExtra("VideoLink", link);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("resultCodeVideo", resultCode + "");
        if (resultCode == RESULT_OK && data != null){
            Uri selectedVideo = data.getData();
            Log.d("Video link", selectedVideo.toString());
            link = selectedVideo.toString();
            presenter.uploadVideoToServer(selectedVideo);
        }
        if(requestCode == REQUEST_ID_VIDEO_CAPTURE && data != null){
            Uri selectedVideo = data.getData();
            Log.d("Video link", selectedVideo.toString());
            link = selectedVideo.toString();
            presenter.uploadVideoToServer(selectedVideo);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(appContext, ActivityStarNameEnter.class));
    }


    @Override
    public void startingNextActivity() {
        startActivity(new Intent(appContext, CastingActivity.class));
    }

    @Override
    public void showingError() {
        Toast.makeText(appContext, "Loading error. Try again later", Toast.LENGTH_SHORT).show();
    }
}
