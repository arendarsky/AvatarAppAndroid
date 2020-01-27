package com.example.talentshow.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.talentshow.App;
import com.example.talentshow.R;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;


import butterknife.BindView;
import butterknife.ButterKnife;
import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;
import toothpick.Toothpick;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class CastingActivity extends AppCompatActivity {

    @Inject
    Context appContext;

    @BindView(R.id.activity_casting_video)
    VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casting);
        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScope(App.class));

        String url = "http://techslides.com/demos/sample-videos/small.mp4";

        MediaController controls = new MediaController(this);
        controls.setAnchorView(video);
        video.setMediaController(controls);
        video.setVideoURI(Uri.parse(url));
        //video.setVideoPath(videoName1);
        video.start();
    }


}
