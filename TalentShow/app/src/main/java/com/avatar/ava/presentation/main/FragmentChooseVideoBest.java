package com.avatar.ava.presentation.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.avatar.ava.App;
import com.avatar.ava.data.AuthRepository;
import com.avatar.ava.domain.Interactor;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.avatar.ava.R;
import com.deep.videotrimmer.DeepVideoTrimmer;
import com.deep.videotrimmer.interfaces.OnRangeSeekBarListener;
import com.deep.videotrimmer.interfaces.OnTrimVideoListener;
import com.deep.videotrimmer.view.ProgressBarView;
import com.deep.videotrimmer.view.RangeSeekBarView;
import com.deep.videotrimmer.view.Thumb;
import com.deep.videotrimmer.view.TimeLineView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import toothpick.Toothpick;

public class FragmentChooseVideoBest extends AppCompatActivity implements OnRangeSeekBarListener {

    @Inject
    Interactor interactor;




    @BindView(R.id.video_loader)
    VideoView video;


    @BindView(R.id.activity_star_video_best_btn)
    Button btn;



    @BindView(R.id.timeLineBar)
    RangeSeekBarView rangeSeekBarView;


    @BindView(R.id.videoTrimmer)
    DeepVideoTrimmer videoTrimmer;

    @Inject
    Context appContext;

    int duration = 0;

    MediaController mc;

    String fileName = "filename";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_video_best);
        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScope(App.class));

        //Intent intent = getIntent();
        //String link = intent.getStringExtra("VideoLink");
        //Log.d("Video linkk", link);



        String url = "https://youtu.be/69FMw5OykjQ";

        //MediaController controls = new MediaController(this);
        //controls.setAnchorView(video);
        //video.setMediaController(controls);

        String fileName = "android.resource://"+ getPackageName()+"/raw/example";

        Uri uri = Uri.parse(fileName);

        //video.setVideoURI(Uri.parse(link));
        //video.setVideoURI(uri);


        videoTrimmer.setMaxDuration(30);
        videoTrimmer.setVideoURI(uri);



        rangeSeekBarView.addOnRangeSeekBarListener(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Thumb> thums = rangeSeekBarView.getThumbs();
                float value1 = thums.get(0).getVal() / 100 * video.getDuration();
                float value2 = thums.get(1).getVal() / 100 * video.getDuration();
                Log.d("videoTrimmerLog", value1 + " " + value2);
                setIntervak(fileName, (int)value1, (int)value2);
            }
        });

        //videoTrimmer.setVideoURI(uri);

        //video.setVideoURI(Uri.parse(url));
        //video.setVideoPath(videoName1);
        //video.start();

        /*
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {


                        mc = new MediaController(FragmentChooseVideoBest.this);
                        video.setMediaController(mc);

                        mc.setAnchorView(video);
                    }
                });





                duration = video.getDuration();
                Log.d("duration", duration + "");
                /*rangeSeekbar.setMinValue(0);
                rangeSeekbar.setMaxValue(duration);
                rangeSeekbar.apply();
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




                rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                    @Override
                    public void valueChanged(Number minValue, Number maxValue) {
                        video.pause();
                        video.seekTo(Integer.parseInt(String.valueOf(minValue)));
                        video.start();
                        Log.d("videoR", minValue + " " + maxValue);
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
                        int sec1 = Integer.parseInt(String.valueOf(minValue)) / 1000;
                        sec = Integer.parseInt(String.valueOf(maxValue)) / 1000;
                        if(sec - sec1 > 30){
                            sec = sec1 + 30;
                            Log.d("videoR", (sec1 * 1000) + " " + (sec * 1000));

                            rangeSeekbar.setRight(sec * 1000);
                            //rangeSeekbar.apply();
                        }
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

        });*/




    }

    @Override
    public void onCreate(RangeSeekBarView rangeSeekBarView, int index, float value) {

    }

    @Override
    public void onSeek(RangeSeekBarView rangeSeekBarView, int index, float value) {

        Log.d("videoTrimmerLog", index + " " + (value / 100 * video.getDuration()));
    }

    @Override
    public void onSeekStart(RangeSeekBarView rangeSeekBarView, int index, float value) {

    }

    @Override
    public void onSeekStop(RangeSeekBarView rangeSeekBarView, int index, float value) {

    }

    private void setIntervak(String name, int startTime, int endTime){
        Disposable disposable = interactor.setInterval(name, startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
