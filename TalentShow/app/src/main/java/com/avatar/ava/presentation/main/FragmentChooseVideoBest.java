package com.avatar.ava.presentation.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.MvpView;
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

public class FragmentChooseVideoBest extends MvpAppCompatFragment implements MvpView, OnRangeSeekBarListener {

    @Inject
    Interactor interactor;

    private Activity activity;


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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        String url = "https://youtu.be/69FMw5OykjQ";



        String fileName = "android.resource://"+ getActivity().getPackageName()+"/raw/example";

        Uri uri = Uri.parse(fileName);

        //video.setVideoURI(Uri.parse(link));
        //video.setVideoURI(uri);


        videoTrimmer.setMaxDuration(30);
        videoTrimmer.setVideoURI(uri);



        rangeSeekBarView.addOnRangeSeekBarListener(this);

        btn.setOnClickListener(v -> {
            List<Thumb> thums = rangeSeekBarView.getThumbs();
            float value1 = thums.get(0).getVal() / 100 * video.getDuration();
            float value2 = thums.get(1).getVal() / 100 * video.getDuration();
            Log.d("videoTrimmerLog", value1 + " " + value2);
            setInterval(fileName, (int)value1, (int)value2);
            startActivity(new Intent(getContext(), MainScreenActivity.class));
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_star_video_best, container, false);
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
        setContentView(R.layout.activity_star_video_best);
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

    private void setInterval(String name, int startTime, int endTime){
        Disposable disposable = interactor.setInterval(name, startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> startActivity(new Intent(getContext(), MainScreenActivity.class)),
                        e -> Log.d("Error", "error"));
    }
}
