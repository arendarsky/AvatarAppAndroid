package com.example.talentshow.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.example.talentshow.R;


public class CastingActivity extends AppCompatActivity {


    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casting);
        /*K4LVideoTrimmer videoTrimmer = (K4LVideoTrimmer) findViewById(R.id.activity_casting_video);
        videoTrimmer.setOnTrimVideoListener(new OnTrimVideoListener() {
            @Override
            public void getResult(Uri uri) {
                if (videoTrimmer != null) {
                    //videoTrimmer.setVideoURI(uri);
                }
            }

            @Override
            public void cancelAction() {

            }
        });*/
    }
}
