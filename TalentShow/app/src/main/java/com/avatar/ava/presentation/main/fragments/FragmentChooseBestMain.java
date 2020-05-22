package com.avatar.ava.presentation.main.fragments;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.avatar.ava.App;
import com.avatar.ava.R;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Toothpick;

public class FragmentChooseBestMain extends Fragment {

    private Activity activity;

    @Inject
    Context appContext;

    @BindView(R.id.fragment_video_best_main_video)
    PlayerView video;

    @BindView(R.id.fragment_video_best_main_bar)
    RangeSeekBar<Float> rangeSeekBar;

    @BindView(R.id.fragment_video_best_main_start)
    TextView startText;

    @BindView(R.id.fragment_video_best_main_end)
    TextView endText;

    @BindView(R.id.fragment_video_best_main_progressbar)
    ProgressBar progressBar;

    float duration = 0;

    MediaController mc;

    String fileName = "filename";

    SimpleExoPlayer player;
    private DataSource.Factory dataSourceFactory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));

    }

    //TODO переделать этот код
    private boolean firstPlay = true;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Uri uri = getArguments().getParcelable("uri");



        player = new SimpleExoPlayer.Builder(appContext).build();
        dataSourceFactory = new DefaultDataSourceFactory(appContext,
                Util.getUserAgent(appContext, getResources().getString(R.string.app_name)));

        video.setPlayer(player);

        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
        player.prepare(videoSource);

        //video.setVideoURI(uri);
        //video.seekTo(0);
        player.setPlayWhenReady(true);
        //video.setVideoURI(Uri.parse(link));
        //video.setVideoURI(uri);
        player.addAnalyticsListener(new AnalyticsListener() {
            @Override
            public void onPlayerStateChanged(EventTime eventTime, boolean playWhenReady, int playbackState) {
                if (playWhenReady && playbackState == Player.STATE_READY){
                    duration = player.getDuration() / 1000;
                    startText.setText("00:00:00");
                    endText.setText(getTime((int)duration));

                    rangeSeekBar.setRangeValues((float) 0, duration);
                    if(firstPlay){
                        if(duration > 30)rangeSeekBar.setSelectedMaxValue(30f);
                        firstPlay = false;
                    }


                    rangeSeekBar.setOnRangeSeekBarChangeListener(
                            (bar, minValue, maxValue) -> {
                                player.seekTo((int) (minValue * 1000));
                                Log.d("BestMain", minValue + " " + maxValue);
                                if((maxValue - minValue)  > 30){
                                    Log.d("BestMain", minValue + " " + maxValue);
                                    rangeSeekBar.setSelectedMaxValue(minValue + 30);
                                }
                            }
                    );
                }
            }
        });

        /*video.setOnPreparedListener(mp -> {
            video.start();

            duration = mp.getDuration()/1000;
            startText.setText("00:00:00");
            endText.setText(getTime(mp.getDuration()/1000));

            rangeSeekBar.setRangeValues((float) 0, duration);

            if(duration > 30)rangeSeekBar.setSelectedMaxValue(30f);

            rangeSeekBar.setOnRangeSeekBarChangeListener(
                    (bar, minValue, maxValue) -> {
                        video.seekTo((int) (minValue * 1000));
                        Log.d("BestMain", minValue + " " + maxValue);
                        if((maxValue - minValue)  > 30){
                            Log.d("BestMain", minValue + " " + maxValue);
                            rangeSeekBar.setSelectedMaxValue(minValue + 30);
                        }
                    }
            );
        });*/
//        btn.setOnClickListener(v -> {
//            List<Thumb> thums = rangeSeekBarView.getThumbs();
//            float value1 = thums.get(0).getVal() / 100 * video.getDuration();
//            float value2 = thums.get(1).getVal() / 100 * video.getDuration();
//            Log.d("videoTrimmerLog", value1 + " " + value2);
//            setInterval(fileName, (int)value1, (int)value2);
//            startActivity(new Intent(getContext(), MainScreenActivity.class));
//        });
    }

    @Override
    public void onPause() {
        super.onPause();
        player.stop();
    }

    private String getTime(int seconds) {
        int hr = seconds/3600;
        int rem = seconds%3600;
        int mn = rem/60;
        int sec = rem%60;
        return String.format("%02d",hr) + ":" +
                String.format("%02d", mn) + ":" +
                String.format("%02d", sec);
    }

    public List<Float> getInterval(){
//        List<Thumb> thums = rangeSeekBarView.getThumbs();
//        float value1 = thums.get(0).getVal() / 100 * video.getDuration();
//        float value2 = thums.get(1).getVal() / 100 * video.getDuration();
        List<Float> tmp = new ArrayList<>();
        tmp.add((float)rangeSeekBar.getSelectedMinValue());
        tmp.add((float)rangeSeekBar.getSelectedMaxValue());
        //video.stopPlayback();
        player.stop();
        return tmp;
    }

    public void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_best_main, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) activity = (Activity) context;
    }
    /*
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_video_best);
            ButterKnife.bind(this);
            Toothpick.inject(this, Toothpick.openScope(App.class));

            //Intent intent = getIntent();
            //String link = intent.getStringExtra("VideoLink");
            //Log.d("Video linkk", link);



            String url = "https://youtu.be/69FMw5OykjQ";



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






        }
    */


//    private void setInterval(String name, int startTime, int endTime){
//        Disposable disposable = interactor.setInterval(name, startTime, endTime)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(() -> startActivity(new Intent(getContext(), MainScreenActivity.class)),
//                        e -> Log.d("Error", "error"));
//    }
}
