package com.example.talentshow.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.talentshow.App;
import com.example.talentshow.R;
import com.example.talentshow.presentation.producer.ActivityProducerYes;
import com.example.talentshow.presentation.star.ActivityStarStatistics;
import com.example.talentshow.presentation.star.ActivityStarVideoBest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
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


public class CastingActivity extends AppCompatActivity implements CastingDialogFragment.ItemClickListener {

    @Inject
    Context appContext;

    @BindView(R.id.activity_casting_video)
    VideoView video;

    @BindView(R.id.activity_casting_head)
    TextView tv;

    @BindView(R.id.casting_activity_menu)
    BottomNavigationView menu;

    VideoView video_fullscreen;

    ImageButton btn_fullscreen;

    MediaController mc, mc1;
    private boolean mShouldStop = false;

    private int startVideo = 0;

    private boolean fullscreen = false;

    @BindView(R.id.activity_casting_layout)
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        String source = "android.resource://" + getPackageName() +"/"+R.raw.example;



        if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_casting_fullscreen);
            video_fullscreen = (VideoView) findViewById(R.id.activity_casting_video_fullscreen);
            video_fullscreen.setVideoURI(Uri.parse(source));
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

            btn_fullscreen = (ImageButton) findViewById(R.id.activity_casting_btn_fullscreen);

            btn_fullscreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fullscreen = false;
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            });


            video_fullscreen.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                            /*
                             * add media controller
                             */
                            mc1 = new MediaController(CastingActivity.this);
                            video_fullscreen.setMediaController(mc1);
                            /*
                             * and set its position on screen
                             */
                            mc1.setAnchorView(video_fullscreen);
                        }
                    });
                }
            });

            video_fullscreen.start();
        }else{
            setContentView(R.layout.activity_casting);


            ButterKnife.bind(this);
            Toothpick.inject(this, Toothpick.openScope(App.class));
            String url = "http://techslides.com/demos/sample-videos/small.mp4";

            menu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

            video.setVideoURI(Uri.parse(source));
            //video.setVideoPath(videoName1);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fullscreen = true;
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                }
            });

            video.seekTo(startVideo);
            video.start();


            video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                            /*
                             * add media controller
                             */
                            mc = new MediaController(CastingActivity.this);
                            video.setMediaController(mc);
                            /*
                             * and set its position on screen
                             */
                            mc.setAnchorView(video);
                        }
                    });
                }
            });

            trackProgress();
        }




    }


    private void trackProgress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!mShouldStop) {
                    if (video != null && video.isPlaying()) {
                        if (video.getCurrentPosition() >= startVideo + 30000) {
                            video.pause();
                            mShouldStop = true;
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_rating:
                    //startActivity(new Intent(appContext, ActivityStarStatistics.class));
                    break;
            }
            return false;
        }
    };



    CastingDialogFragment castingDialogFragment;
    public void showBottomSheetCasting(View view){
        castingDialogFragment =
                CastingDialogFragment.newInstance();
        castingDialogFragment.show(getSupportFragmentManager(),
                CastingDialogFragment.TAG);
    }

    @Override
    public void onItemClick(int item) {
        if(item == R.id.casting_dialog_btn){
            String text = castingDialogFragment.getText();
            Log.d("castingText", text);
        }
    }
}
