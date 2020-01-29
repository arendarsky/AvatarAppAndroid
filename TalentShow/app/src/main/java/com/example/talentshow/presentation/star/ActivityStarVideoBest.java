package com.example.talentshow.presentation.star;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.talentshow.App;
import com.example.talentshow.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Toothpick;

public class ActivityStarVideoBest extends AppCompatActivity {

    @BindView(R.id.activity_star_video_best_video)
    VideoView video;

    @BindView(R.id.activity_star_video_best_rangeSeekbar)
    CrystalRangeSeekbar rangeSeekbar;

    @BindView(R.id.activity_star_video_best_btn)
    Button btn;

    @BindView(R.id.activity_star_video_best_tv_min)
    TextView tvMin;

    @BindView(R.id.activity_star_video_best_tv_max)
    TextView tvMax;

    @Inject
    Context appContext;

    int duration = 0;

    MediaController mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_video_best);
        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScope(App.class));

        Intent intent = getIntent();
        String link = intent.getStringExtra("VideoLink");
        Log.d("Video linkk", link);

        String url = "https://youtu.be/69FMw5OykjQ";

        //MediaController controls = new MediaController(this);
        //controls.setAnchorView(video);
        //video.setMediaController(controls);
        video.setVideoURI(Uri.parse(link));
        //video.setVideoURI(Uri.parse(url));
        //video.setVideoPath(videoName1);
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
                        mc = new MediaController(ActivityStarVideoBest.this);
                        video.setMediaController(mc);
                        /*
                         * and set its position on screen
                         */
                        mc.setAnchorView(video);
                    }
                });


                duration = video.getDuration();
                Log.d("duration", duration + "");
                rangeSeekbar.setMinValue(0);
                rangeSeekbar.setMaxValue(duration);
                tvMin.setText("0:00");
                int sec = duration / 1000;
                int minute = 0;
                if(sec >= 60){
                    minute = sec / 60;
                    sec = sec % 60;
                }
                if(sec < 10){
                    tvMax.setText(minute + ":0" + sec);
                }else{
                    tvMax.setText(minute + ":" + sec);
                }
            }
        });


        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                video.pause();
                video.seekTo(Integer.parseInt(String.valueOf(minValue)));
                video.start();
                Log.d("videoR", minValue + "");
                int sec = Integer.parseInt(String.valueOf(minValue)) / 1000;
                int minute = 0;
                if(sec >= 60){
                    minute = sec / 60;
                    sec = sec % 60;
                }
                if(sec < 10){
                    tvMin.setText(minute + ":0" + sec);
                }else{
                    tvMin.setText(minute + ":" + sec);
                }
                sec = Integer.parseInt(String.valueOf(minValue)) / 1000 + 30;
                minute = 0;
                if(sec >= 60){
                    minute = sec / 60;
                    sec = sec % 60;
                }
                if(sec < 10){
                    tvMax.setText(minute + ":0" + sec);
                }else{
                    tvMax.setText(minute + ":" + sec);
                }
            }
        });

    }
}
