package com.avatar.ava.presentation.main.fragments.casting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.domain.entities.PersonDTO;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.ClippingMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;

import javax.inject.Inject;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;

import static com.avatar.ava.DataModule.SERVER_NAME;


public class CastingFragment extends MvpAppCompatFragment implements CastingView {

    public static int CASTING_ID;

    @Inject
    Context appContext;

    @InjectPresenter
    CastingPresenter presenter;

    @ProvidePresenter
    CastingPresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(CastingPresenter.class);
    }

    @BindView(R.id.activity_casting_video)
    PlayerView video;

    @BindView(R.id.activity_casting_name)
    TextView name;

    @BindView(R.id.casting_activity_avatar)
    ImageView avatar;

    @BindView(R.id.activity_casting_description)
    TextView description;

    @BindView(R.id.activity_casting_layout)
    ConstraintLayout layout;

    @BindView(R.id.activity_casting_card)
    CardView castingCard;

    @BindView(R.id.casting_no_more_videos)
    TextView noMoreVideos;

    @BindView(R.id.activity_casting_btn_like)
    View likeButton;

    @BindView(R.id.activity_casting_btn_x)
    View dislikeButton;

    @BindView(R.id.casting_view_x)
    View crossView;

    @BindView(R.id.casting_view_heart)
    View heartView;

    @BindView(R.id.casting_fragment_progress_bar)
    ProgressBar progressBar;

    VideoView video_fullscreen;

    ImageButton btn_fullscreen;

    MediaController mc, mc1;
    private boolean mShouldStop = false;

    private int startVideo = 0;

    private boolean fullscreen = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_casting, container, false);
    }

    MediaSource videoSource;
    DataSource.Factory dataSourceFactory;
    SimpleExoPlayer player;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        /*video.seekTo(startVideo);
        video.start();*/
        CASTING_ID = this.getId();

        player = new SimpleExoPlayer.Builder(getContext()).build();
        video.setPlayer(player);
        // Produces DataSource instances through which media data is loaded.
        dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "Talent Show"));

        castingCard.setVisibility(View.INVISIBLE);
        noMoreVideos.setVisibility(View.INVISIBLE);
        likeButton.setVisibility(View.INVISIBLE);
        dislikeButton.setVisibility(View.INVISIBLE);
        heartView.setVisibility(View.INVISIBLE);
        crossView.setVisibility(View.INVISIBLE);

        player.addVideoListener(new VideoListener() {
            @Override
            public void onRenderedFirstFrame() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        
        /*video.setOnPreparedListener(mp -> mp.setOnVideoSizeChangedListener(
                (mp12, width, height) -> {
                    mp.setLooping(true);
                    *//*
                     * add media controller
                     *//*
//                    mc = new MediaController(appContext);
//                    video.setMediaController(mc);
                    *//*
                     * and set its position on screen
                     *//*
//                    mc.setAnchorView(video);
                }));*/
//
//        trackProgress();
        presenter.getFirstVideo();
    }
//
//    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        String source = "android.resource://" + getPackageName() +"/"+R.raw.example;
//
//        if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
//            setContentView(R.layout.activity_casting_fullscreen);
//            video_fullscreen = findViewById(R.id.activity_casting_video_fullscreen);
//            video_fullscreen.setVideoURI(Uri.parse(source));
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//
//            btn_fullscreen = findViewById(R.id.activity_casting_btn_fullscreen);
//
//            btn_fullscreen.setOnClickListener(v -> {
//                fullscreen = false;
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            });
//
//
//            video_fullscreen.setOnPreparedListener(mp -> mp.setOnVideoSizeChangedListener(
//                    (mp1, width, height) -> {
//                /*
//                 * add media controller
//                 */
//                mc1 = new MediaController(appContext);
//                video_fullscreen.setMediaController(mc1);
//                /*
//                 * and set its position on screen
//                 */
//                mc1.setAnchorView(video_fullscreen);
//            }));
//
//            video_fullscreen.start();
//        }else{
//            setContentView(R.layout.fragment_casting);


//            ButterKnife.bind(this);
//            Toothpick.inject(this, Toothpick.openScope(App.class));
//            String url = "http://techslides.com/demos/sample-videos/small.mp4";

//            menu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//            video.setVideoURI(Uri.parse(source));
//            video.setVideoPath("https://avatarapp.yambr.ru/api/video/qmxicx0h.24p.mp4");

//            tv.setOnClickListener(v -> {
//                fullscreen = true;
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//
//            });
//
//
//
//    }


    /*private void trackProgress() {
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
    }*/

//    CastingDialogFragment castingDialogFragment;
//    public void showBottomSheetCasting(View view){
//        castingDialogFragment =
//                CastingDialogFragment.newInstance();
//        castingDialogFragment.show(getSupportFragmentManager(),
//                CastingDialogFragment.TAG);
//    }

//    @Override
//    public void onItemClick(int item) {
//        if(item == R.id.casting_dialog_btn){
//            String text = castingDialogFragment.getText();
//            Log.d("castingText", text);
//        }
//    }

    public void showError(String error){
        Toast.makeText(appContext, error, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.activity_casting_btn_like)
    public void likeClicked(){
        progressBar.setVisibility(View.VISIBLE);
        presenter.likeVideo();
    }

    @OnClick(R.id.activity_casting_btn_x)
    public void dislikeClicked() {
        progressBar.setVisibility(View.VISIBLE);
        presenter.dislikeVideo();
    }

    @Override
    public void loadNewVideo(PersonDTO personDTO){
        castingCard.setVisibility(View.VISIBLE);
        noMoreVideos.setVisibility(View.INVISIBLE);
        likeButton.setVisibility(View.VISIBLE);
        dislikeButton.setVisibility(View.VISIBLE);
        heartView.setVisibility(View.VISIBLE);
        crossView.setVisibility(View.VISIBLE);

        String videoLink = SERVER_NAME + "/api/video/" + personDTO.getVideo().getName();
        int start = (int)personDTO.getVideo().getStartTime() * 1000;
        int end = (int)personDTO.getVideo().getEndTime() * 1000;
        Log.d("Casting link", videoLink);
        //video.setVideoURI(Uri.parse(videoLink));
        player.stop(false);
        videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(videoLink));
        ClippingMediaSource clippingMediaSource =  new ClippingMediaSource(videoSource, start, end);
        LoopingMediaSource loopingMediaSource = new LoopingMediaSource(clippingMediaSource, 3);
        player.prepare(loopingMediaSource);

        player.setPlayWhenReady(true);

    }

    public void stopVideo(){
        if(player != null)
        player.stop();
    }

    @Override
    public void setAvatar(String avatarLink) {
        if(avatarLink.equals("null")){
            Glide.with(getView())
                    .load(R.drawable.empty_profile_icon)
                    .circleCrop()
                    .into(avatar);
        }
        else{
            Glide.with(getView())
                    .load(avatarLink)
                    .circleCrop()
                    .into(avatar);
        }

    }

    @Override
    public void setDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public void setName(String name) {
        this.name.setText(name);
    }

    @Override
    public void showNoMoreVideos() {
        castingCard.setVisibility(View.INVISIBLE);
        noMoreVideos.setVisibility(View.VISIBLE);
        likeButton.setVisibility(View.INVISIBLE);
        dislikeButton.setVisibility(View.INVISIBLE);
        crossView.setVisibility(View.INVISIBLE);
        heartView.setVisibility(View.INVISIBLE);
    }


}
