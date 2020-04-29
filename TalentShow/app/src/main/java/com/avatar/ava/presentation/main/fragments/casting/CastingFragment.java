package com.avatar.ava.presentation.main.fragments.casting;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.domain.entities.PersonDTO;
import com.avatar.ava.presentation.main.MainScreenPostman;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.ClippingMediaSource;
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
    private Activity activity;

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

    @BindView(R.id.casting_fragment_restart_btn)
    ImageButton restartBtn;

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

    private DataSource.Factory dataSourceFactory;
    private SimpleExoPlayer player;

    @Override
    public void onPause() {
        super.onPause();
        if (video.getPlayer() != null) {
            if (video.getPlayer().isPlaying())
                video.getPlayer().stop();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        CASTING_ID = this.getId();

        player = new SimpleExoPlayer.Builder(appContext).build();
        video.setPlayer(player);
        // Produces DataSource instances through which media data is loaded.
        dataSourceFactory = new DefaultDataSourceFactory(appContext,
                Util.getUserAgent(appContext, getResources().getString(R.string.app_name)));

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
                restartBtn.setVisibility(View.INVISIBLE);
            }
        });
        player.addAnalyticsListener(new AnalyticsListener() {
            @Override
            public void onPlayerStateChanged(EventTime eventTime, boolean playWhenReady, int playbackState) {
                if(playWhenReady && playbackState == Player.STATE_ENDED){
                    restartBtn.setVisibility(View.VISIBLE);
                }
                else if (playWhenReady && playbackState == Player.STATE_READY){
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else progressBar.setVisibility(View.VISIBLE);
            }
        });


        presenter.getFirstVideo();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) activity = (Activity) context;
    }

    @OnClick({R.id.casting_activity_avatar, R.id.activity_casting_name})
    void avatarClicked(){
        try {
            ((MainScreenPostman) activity).openPublicProfile(presenter.getPersonId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        player.stop();
        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(videoLink));
        ClippingMediaSource clippingMediaSource =  new ClippingMediaSource(videoSource, start, end);
        player.prepare(clippingMediaSource);

        player.setPlayWhenReady(true);

    }

    @OnClick(R.id.casting_fragment_restart_btn)
    void restartVideo(){
        restartBtn.setVisibility(View.INVISIBLE);
        player.seekTo(0);
        player.setPlayWhenReady(true);
    }



    public void stopVideo(){
        if(player != null)
            player.stop();
        if (player != null) {
            player.release();
        }
    }

    @Override
    public void setAvatar(String avatarLink) {
        if(avatarLink.equals("null")){
            Glide.with(this)
                    .load(R.drawable.empty_profile_icon)
                    .circleCrop()
                    .into(avatar);
        }
        else{
            Glide.with(this)
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
        stopVideo();
    }


}
