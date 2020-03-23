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
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.avatar.ava.App;
import com.avatar.ava.R;
import com.deep.videotrimmer.DeepVideoTrimmer;
import com.deep.videotrimmer.interfaces.OnRangeSeekBarListener;
import com.deep.videotrimmer.view.RangeSeekBarView;
import com.deep.videotrimmer.view.Thumb;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Toothpick;

public class FragmentChooseBestMain extends Fragment implements OnRangeSeekBarListener {

    private Activity activity;

    @BindView(R.id.fragment_video_best_main_video)
    VideoView video;

    @BindView(R.id.fragment_video_best_main_bar)
    RangeSeekBar<Float> rangeSeekBar;

    @BindView(R.id.fragment_video_best_main_start)
    TextView startText;

    @BindView(R.id.fragment_video_best_main_end)
    TextView endText;

    float duration = 0;

    MediaController mc;

    String fileName = "filename";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));

    }

    //TODO переделать этот код

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Uri uri = getArguments().getParcelable("uri");

        video.setVideoURI(uri);
        video.seekTo(0);

        //video.setVideoURI(Uri.parse(link));
        //video.setVideoURI(uri);


        video.setOnPreparedListener(mp -> {
            video.start();

            duration = mp.getDuration()/1000;
            startText.setText("00:00:00");
            endText.setText(getTime(mp.getDuration()/1000));

            rangeSeekBar.setRangeValues((float) 0, duration);


            rangeSeekBar.setOnRangeSeekBarChangeListener(
                    (bar, minValue, maxValue) -> video.seekTo((int) (minValue * 1000))
            );
        });
//        btn.setOnClickListener(v -> {
//            List<Thumb> thums = rangeSeekBarView.getThumbs();
//            float value1 = thums.get(0).getVal() / 100 * video.getDuration();
//            float value2 = thums.get(1).getVal() / 100 * video.getDuration();
//            Log.d("videoTrimmerLog", value1 + " " + value2);
//            setInterval(fileName, (int)value1, (int)value2);
//            startActivity(new Intent(getContext(), MainScreenActivity.class));
//        });
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
        tmp.add(rangeSeekBar.getSelectedMinValue());
        tmp.add(rangeSeekBar.getAbsoluteMaxValue());
        return tmp;
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

//    private void setInterval(String name, int startTime, int endTime){
//        Disposable disposable = interactor.setInterval(name, startTime, endTime)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(() -> startActivity(new Intent(getContext(), MainScreenActivity.class)),
//                        e -> Log.d("Error", "error"));
//    }
}
