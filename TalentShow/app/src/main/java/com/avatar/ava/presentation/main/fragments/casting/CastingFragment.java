package com.avatar.ava.presentation.main.fragments.casting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.amplitude.api.Amplitude;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.BuildConfig;
import com.avatar.ava.R;
import com.avatar.ava.domain.entities.PersonDTO;
import com.avatar.ava.presentation.main.MainScreenPostman;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;


public class CastingFragment extends MvpAppCompatFragment implements CastingView, CastingCard.Callback {

    public static int CASTING_ID;
    private Activity activity;

    @BindView(R.id.casting_swipe_view)
    SwipePlaceHolderView mSwipeView;

    @Inject
    Context appContext;

    @InjectPresenter
    CastingPresenter presenter;
    private boolean videoDownloading = false;

    @ProvidePresenter
    CastingPresenter getPresenter() {
        return Toothpick.openScope(App.class).getInstance(CastingPresenter.class);
    }


    @BindView(R.id.activity_casting_layout)
    ConstraintLayout layout;


    @BindView(R.id.casting_no_more_videos)
    TextView noMoreVideos;

    @BindView(R.id.activity_casting_btn_like)
    View likeButton;

    @BindView(R.id.activity_casting_btn_x)
    View dislikeButton;


    @BindView(R.id.casting_fragment_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.casting_fragment_restart_btn)
    ImageButton restartBtn;

    @BindView(R.id.casting_loading)
    ConstraintLayout videoLoading;

    @BindView(R.id.casting_loading_progbar)
    CircleProgressBar loadingProgbar;

    private BottomSheetDialog bottomSheetDialog;
    private CastingCard castingCard;

    private long stopTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSwipeView != null)
            mSwipeView.unlockViews();
        if (player != null)
            player.setPlayWhenReady(true);
//        layout.setEnabled(true);
//        bottomSheetDialog.hide();
        presenter.setOnResume();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (BuildConfig.DEBUG)
            Log.d("CastingFragment", "OnStop");
        mSwipeView.removeAllViews();
        mSwipeView.lockViews();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_casting, container, false);
    }

    private DataSource.Factory dataSourceFactory;
    private SimpleExoPlayer player;

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            if (player.isPlaying())
                player.stop();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (player != null)
            player.release();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        CASTING_ID = this.getId();

        player = new SimpleExoPlayer.Builder(appContext).build();
        dataSourceFactory = new DefaultDataSourceFactory(appContext,
                Util.getUserAgent(appContext, getResources().getString(R.string.app_name)));


        mSwipeView.getBuilder()
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL)
                .setDisplayViewCount(1)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.casting_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.casting_swipe_out_msg_view));

        noMoreVideos.setVisibility(View.INVISIBLE);
        likeButton.setVisibility(View.INVISIBLE);
        dislikeButton.setVisibility(View.INVISIBLE);


        player.addVideoListener(new VideoListener() {
            @Override
            public void onRenderedFirstFrame() {
            }
        });
        player.addAnalyticsListener(new AnalyticsListener() {
            @Override
            public void onPlayerStateChanged(EventTime eventTime, boolean playWhenReady, int playbackState) {
                if (playWhenReady && playbackState == Player.STATE_ENDED) {
                    restartBtn.setVisibility(View.VISIBLE);
                } else if (playWhenReady && playbackState == Player.STATE_READY) {
                    progressBar.setVisibility(View.INVISIBLE);
                    restartBtn.setVisibility(View.INVISIBLE);
                } else if (playbackState == Player.STATE_IDLE) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else progressBar.setVisibility(View.VISIBLE);
            }
        });
        if (BuildConfig.DEBUG)
            Log.d("CastingFragment", "OnViewCreated");
        presenter.getFirstVideo();

        mSwipeView.addItemRemoveListener(count -> {
            if (BuildConfig.DEBUG)
                Log.d("CastingSwipe", "SwipeRemove");

        });

        bottomSheetDialog = new BottomSheetDialog(activity);
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.share_bottomsheet, null);
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        ConstraintLayout inst = sheetView.findViewById(R.id.share_stories);
        ConstraintLayout text = sheetView.findViewById(R.id.share_text);
        inst.setOnClickListener(v -> {
            videoDownloading = true;
            bottomSheetDialog.hide();
            try {
                ((MainScreenPostman) activity).checkRequestPermissions();
            } catch (Exception e) {
                e.printStackTrace();
            }
            bottomSheetDialog.dismiss();
            presenter.downloadVideo();
        });

        text.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = presenter.getVideoName();
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (BuildConfig.DEBUG)
            Log.d("CastingFragment", "OnAttach");

        if (context instanceof Activity) activity = (Activity) context;
    }

    public void showError(String error) {
        Toast.makeText(appContext, error, Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.activity_casting_btn_like)
    public void likeClicked() {
        Amplitude.getInstance().logEvent("heart_button_tapped");
        progressBar.setVisibility(View.VISIBLE);
        restartBtn.setVisibility(View.INVISIBLE);
        mSwipeView.doSwipe(true);
    }

    @OnClick(R.id.activity_casting_btn_x)
    public void dislikeClicked() {
        Amplitude.getInstance().logEvent("x_button_tapped");
        progressBar.setVisibility(View.VISIBLE);
        restartBtn.setVisibility(View.INVISIBLE);
        mSwipeView.doSwipe(false);
    }

    @OnClick(R.id.casting_fragment_restart_btn)
    public void restartClicked() {
        player.seekTo(presenter.getVideoBeginTime());
        player.setPlayWhenReady(true);
        restartBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.setVideoNull();
    }

    @Override
    public void loadNewVideo(PersonDTO personDTO) {
        if (presenter.checkPeronDTO(personDTO)) {
            noMoreVideos.setVisibility(View.INVISIBLE);
            likeButton.setVisibility(View.VISIBLE);
            dislikeButton.setVisibility(View.VISIBLE);

            if (BuildConfig.DEBUG)
                Log.d("CastingSwipe", "loadVideo " + personDTO.getName());

            castingCard = new CastingCard(appContext, personDTO, mSwipeView, player, dataSourceFactory, this);
            mSwipeView.addView(castingCard);
        }

    }


    public void stopVideo() {
        if (player != null)
            player.stop();
        if (player != null) {
            player.release();
        }
    }

    @Override
    public void showNoMoreVideos() {
        noMoreVideos.setVisibility(View.VISIBLE);
        likeButton.setVisibility(View.INVISIBLE);
        dislikeButton.setVisibility(View.INVISIBLE);
        stopVideo();
        progressBar.setVisibility(View.INVISIBLE);
        restartBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void doSwipe(boolean swipe) {
        mSwipeView.doSwipe(swipe);
    }


    @Override
    public void onSwipeLike() {
        Amplitude.getInstance().logEvent("heart_button_tapped");
        presenter.likeVideo();
        progressBar.setVisibility(View.VISIBLE);
        restartBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSwipeDisLike() {
        Amplitude.getInstance().logEvent("x_button_tapped");
        presenter.dislikeVideo();
        progressBar.setVisibility(View.VISIBLE);
        restartBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void openProfile() {
        Amplitude.getInstance().logEvent("castingprofile_button_tapped");
        try {
            ((MainScreenPostman) activity).openPublicProfile(presenter.getPersonId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restartVideo() {

    }


    @Override
    public void shareVideoLink(String link) {
        if(!videoDownloading) {
            player.setPlayWhenReady(false);
            bottomSheetDialog.show();
        }
    }

    @Override
    public void openFullscreen(String link) {
        stopVideo();
        try {
            ((MainScreenPostman) activity).openFullScreen(link);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoadingProgBar() {
        videoLoading.setVisibility(View.VISIBLE);
        mSwipeView.lockViews();
        mSwipeView.disableTouchSwipe();
        likeButton.setEnabled(false);
        dislikeButton.setEnabled(false);
        player.setPlayWhenReady(false);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void changeLoadingState(Float aFloat) {
        loadingProgbar.setProgress(aFloat);
    }

    @Override
    public void loadingComplete(Uri uri) {
        videoDownloading = false;
        mSwipeView.unlockViews();
        mSwipeView.enableTouchSwipe();
        likeButton.setEnabled(true);
        dislikeButton.setEnabled(true);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        videoLoading.setVisibility(View.INVISIBLE);
        Intent intent = new Intent("com.instagram.share.ADD_TO_STORY");
        intent.setDataAndType(uri, "video/mp4");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        activity.grantUriPermission(
                "com.instagram.android", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

        activity.startActivity(intent);
    }

    @Override
    public void enableLayout() {
        videoDownloading = false;
        mSwipeView.unlockViews();
        mSwipeView.enableTouchSwipe();
        likeButton.setEnabled(true);
        dislikeButton.setEnabled(true);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        player.seekTo(stopTime);
        player.setPlayWhenReady(true);
        videoLoading.setVisibility(View.INVISIBLE);
        presenter.disposeVideoLoading();
    }

    @Override
    public void setVideoLoading(boolean flag) {
        try {
            ((MainScreenPostman) activity).setVideoLoading(flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
