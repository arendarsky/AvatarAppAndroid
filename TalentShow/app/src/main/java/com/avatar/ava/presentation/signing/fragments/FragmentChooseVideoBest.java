package com.avatar.ava.presentation.signing.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amplitude.api.Amplitude;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.presentation.signing.RegAuthPostman;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;

@SuppressWarnings("FieldCanBeLocal")
public class FragmentChooseVideoBest extends Fragment  {


    private Activity activity;

    @BindView(R.id.fragment_video_best_video)
    VideoView video;

    @BindView(R.id.fragment_video_best_bar)
    RangeSeekBar<Float> rangeSeekBar;

    @BindView(R.id.fragment_video_best_start)
    TextView startText;

    @BindView(R.id.fragment_video_best_end)
    TextView endText;

    @BindView(R.id.fragment_video_best_progressbar)
    ProgressBar progressBar;

    private float duration = 0;

    private final int BACK = 7;
    private final int UPLOAD_VIDEO = 10;


    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Uri uri = null;
        if (getArguments() != null) {
            uri = getArguments().getParcelable("uri");
        }

        video.setVideoURI(uri);
        video.seekTo(0);

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
    }

    @SuppressLint("DefaultLocale")
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
        List<Float> tmp = new ArrayList<>();
        tmp.add(rangeSeekBar.getSelectedMinValue());
        tmp.add(rangeSeekBar.getSelectedMaxValue());
        return tmp;
    }

    @OnClick(R.id.fragment_video_best_save)
    void saveClicked(){
        Amplitude.getInstance().logEvent("newvideo_save_button_tapped");
        try {
            ((RegAuthPostman) activity).fragmentMessage(UPLOAD_VIDEO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.fragment_video_best_back)
    void backClicked(){
        try {
            ((RegAuthPostman) activity).fragmentMessage(BACK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_best, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) activity = (Activity) context;
    }
}
