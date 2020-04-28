package com.avatar.ava.presentation.main.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.presentation.main.MainScreenPostman;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Toothpick;

import static com.avatar.ava.DataModule.SERVER_NAME;

public class FullScreenVideoDialog extends DialogFragment {

    @BindView(R.id.video_fullscreen)
    PlayerView playerView;

    @Inject
    Context appContext;

    public static String TAG = "FullScreenDialog";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) activity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    private Activity activity;
    private String videoName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.video_fullscreen, container, false);
        ButterKnife.bind(this,v);

        if (getArguments() == null) {
            try {
                ((MainScreenPostman) activity).closeFragment();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
            this.videoName = getArguments().getString("videoName");


        DataSource.Factory dataSourceFactory;
        MediaSource videoSource;
        SimpleExoPlayer player;

        player = new SimpleExoPlayer.Builder(appContext).build();

        dataSourceFactory = new DefaultDataSourceFactory(appContext,
                Util.getUserAgent(appContext, "XCE FACTOR"));

        videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(SERVER_NAME + "/api/video/" + this.videoName));
        playerView.setPlayer(player);
        player.prepare(videoSource);

        playerView.showController();

        player.setPlayWhenReady(true);

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(playerView.getPlayer() != null) {
            if (playerView.getPlayer().isPlaying()) playerView.getPlayer().stop();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
