package com.avatar.ava.presentation.main.fragments.casting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;

import javax.inject.Inject;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;


public class CastingFragment extends MvpAppCompatFragment implements CastingView {

    @Inject
    Context appContext;

    @InjectPresenter
    CastingPresenter presenter;

    @ProvidePresenter
    CastingPresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(CastingPresenter.class);
    }

    @BindView(R.id.activity_casting_video)
    VideoView video;

    @BindView(R.id.activity_casting_head)
    TextView tv;

    @BindView(R.id.activity_casting_layout)
    ConstraintLayout layout;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        video.seekTo(startVideo);
        video.start();


        video.setOnPreparedListener(mp -> mp.setOnVideoSizeChangedListener(
                (mp12, width, height) -> {
                    /*
                     * add media controller
                     */
                    mc = new MediaController(appContext);
                    video.setMediaController(mc);
                    /*
                     * and set its position on screen
                     */
                    mc.setAnchorView(video);
                }));

        trackProgress();
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

    //TODO такую реализацию точно убираем позже
    @OnClick(R.id.activity_casting_btn_like)
    public void likeClicked(){
        presenter.likeVideo();
//        video.setVideoPath(presenter.getNewVideoLink());
    }

    @OnClick(R.id.activity_casting_btn_x)
    public void dislikeClicked() {
        presenter.dislikeVideo();
//        video.setVideoPath(presenter.getNewVideoLink());
    }

    public void loadNewVideo(String videoLink){
        video.setVideoPath(videoLink);
    }
}
