package com.example.talentshow.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.talentshow.App;
import com.example.talentshow.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;


public class CastingFragment extends AppCompatActivity implements CastingDialogFragment.ItemClickListener {

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
            video_fullscreen = findViewById(R.id.activity_casting_video_fullscreen);
            video_fullscreen.setVideoURI(Uri.parse(source));
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

            btn_fullscreen = findViewById(R.id.activity_casting_btn_fullscreen);

            btn_fullscreen.setOnClickListener(v -> {
                fullscreen = false;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            });


            video_fullscreen.setOnPreparedListener(mp -> mp.setOnVideoSizeChangedListener(
                    (mp1, width, height) -> {
                /*
                 * add media controller
                 */
                mc1 = new MediaController(CastingFragment.this);
                video_fullscreen.setMediaController(mc1);
                /*
                 * and set its position on screen
                 */
                mc1.setAnchorView(video_fullscreen);
            }));

            video_fullscreen.start();
        }else{
            setContentView(R.layout.activity_casting);


            ButterKnife.bind(this);
            Toothpick.inject(this, Toothpick.openScope(App.class));
            String url = "http://techslides.com/demos/sample-videos/small.mp4";

            menu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

            video.setVideoURI(Uri.parse(source));
//            video.setVideoPath("https://avatarapp.yambr.ru/api/video/qmxicx0h.24p.mp4");

            tv.setOnClickListener(v -> {
                fullscreen = true;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            });

            video.seekTo(startVideo);
            video.start();


            video.setOnPreparedListener(mp -> mp.setOnVideoSizeChangedListener(
                    (mp12, width, height) -> {
                /*
                 * add media controller
                 */
                mc = new MediaController(CastingFragment.this);
                video.setMediaController(mc);
                /*
                 * and set its position on screen
                 */
                mc.setAnchorView(video);
            }));

            trackProgress();
        }




    }


    private void trackProgress() {
        new Thread(() -> {
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
        }).start();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_rating:
                        //startActivity(new Intent(appContext, ActivityStarStatistics.class));
                        break;
                }
                return false;
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

    @OnClick(R.id.activity_casting_btn_like)
    void likeClicked(){
        video.setVideoPath("https://avatarapp.yambr.ru/api/video/qmxicx0h.24p.mp4");
    }

    @OnClick(R.id.activity_casting_btn_x)
    void dislikeClicked(){
        video.setVideoPath("https://avatarapp.yambr.ru/api/video/qmxicx0h.24p.mp4");}
}
