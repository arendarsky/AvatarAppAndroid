package com.avatar.ava.presentation.main.fragments.profile.publicProfile;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.domain.entities.PublicProfileDTO;
import com.avatar.ava.domain.entities.VideoDTO;
import com.avatar.ava.presentation.main.MainScreenPostman;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Toothpick;

import static com.avatar.ava.DataModule.SERVER_NAME;

public class PublicProfileFragment extends MvpAppCompatFragment implements PublicProfileView {

    @Inject
    Context appContext;

    @InjectPresenter
    PublicProfilePresenter presenter;

    private int currCountVideos = 0;

    private ArrayList<VideoDTO> videos = new ArrayList<>();
    private int id = 0;

    @BindView(R.id.fragment_public_profile_image)
    ImageView profileImage;

    @BindView(R.id.fragment_public_profile_name)
    TextView name;

    @BindView(R.id.fragment_public_profile_description)
    TextView description;

    @BindView(R.id.fragment_public_profile_video_1)
    PlayerView video1;

    @BindView(R.id.fragment_public_profile_video_2)
    PlayerView video2;

    @BindView(R.id.fragment_public_profile_video_3)
    PlayerView video3;

    @BindView(R.id.fragment_public_profile_video_4)
    PlayerView video4;

    @BindView(R.id.fragment_public_profile_container1)
    ConstraintLayout container1;

    @BindView(R.id.fragment_public_profile_container2)
    ConstraintLayout container2;

    @BindView(R.id.fragment_public_profile_container3)
    ConstraintLayout container3;

    @BindView(R.id.fragment_public_profile_container4)
    ConstraintLayout container4;

    @BindView(R.id.fragment_profile_parent)
    ConstraintLayout parent;

    private Activity activity;


    @ProvidePresenter
    PublicProfilePresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(PublicProfilePresenter.class);
    }


    public PublicProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) activity = (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_public_profile, container, false);
        ButterKnife.bind(this, v);

        if (getContext() == null){
            try {
                ((MainScreenPostman) activity).closeFragment();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            video1 = new PlayerView(getContext());
            video1.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
            video2 = new PlayerView(getContext());
            video2.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
            video3 = new PlayerView(getContext());
            video3.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
            video4 = new PlayerView(getContext());
            video4.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        }

        DisplayMetrics displayMetrics = appContext.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        if (dpHeight < 1000){
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(parent);

            constraintSet.connect(container3.getId(), ConstraintSet.BOTTOM, parent.getId(), ConstraintSet.BOTTOM, 18);
            constraintSet.connect(container4.getId(), ConstraintSet.BOTTOM, parent.getId(), ConstraintSet.BOTTOM, 18);

            constraintSet.applyTo(parent);
        }
        if (getArguments() == null){
            try {
                ((MainScreenPostman) activity).closeFragment();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else this.id = getArguments().getInt("id");

        return v;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
        presenter.getProfile(id);
        description.setEnabled(false);
    }


    @Override
    public void setDataProfile(PublicProfileDTO person) {
        if(person.getPhoto() == null){
            Glide.with(this)
                    .load(R.drawable.empty_profile_icon)
                    .circleCrop()
                    .into(profileImage);
        }else{
            Glide.with(this)
                    .load(SERVER_NAME + "/api/profile/photo/get/" + person.getPhoto())
                    .circleCrop()
                    .into(profileImage);
        }

        name.setText(person.getName());
        description.setVisibility(View.VISIBLE);
        if(person.getDescription() != null)
            description.setText(person.getDescription());
        videos = person.getVideos();
        if(videos != null){
            currCountVideos = videos.size();
        }else{
            currCountVideos = 0;
        }

        showContainers();
        showVideos();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopVideos();
    }

    private void stopVideos(){
        if(video1.getPlayer() != null) {
            if (video1.getPlayer().isPlaying()) video1.getPlayer().stop();
        }
        if(video2.getPlayer() != null){
            if(video2.getPlayer().isPlaying()) video2.getPlayer().stop();
        }
        if(video3.getPlayer() != null){
            if(video3.getPlayer().isPlaying()) video3.getPlayer().stop();
        }
        if(video4.getPlayer() != null) {
            if (video4.getPlayer().isPlaying()) video4.getPlayer().stop();
        }
    }

    private void showContainers(){
        if(currCountVideos == 0){
            container2.setVisibility(View.INVISIBLE);
            container3.setVisibility(View.INVISIBLE);
            container4.setVisibility(View.INVISIBLE);
        }else if(currCountVideos == 1){
            container3.setVisibility(View.INVISIBLE);
            container4.setVisibility(View.INVISIBLE);
        }else if(currCountVideos == 2){
            container4.setVisibility(View.INVISIBLE);
        }
    }
    private void showVideos(){
        if(currCountVideos >= 1){
            setupVideo(1, Uri.parse(SERVER_NAME + "/api/video/" + videos.get(0).getName()));
            video1.setVisibility(View.VISIBLE);
            if(currCountVideos >= 2){
                setupVideo(2, Uri.parse(SERVER_NAME + "/api/video/" + videos.get(1).getName()));
                video2.setVisibility(View.VISIBLE);
                if(currCountVideos >= 3){
                    setupVideo(3, Uri.parse(SERVER_NAME + "/api/video/" + videos.get(2).getName()));
                    video3.setVisibility(View.VISIBLE);
                }
                if(currCountVideos == 4){
                    setupVideo(4, Uri.parse(SERVER_NAME + "/api/video/" + videos.get(3).getName()));
                    video4.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    private void setupVideo(int num, Uri uri){
        PlayerView playerView;
        switch (num){
            case 1:
                playerView = video1;
                break;
            case 2:
                playerView = video2;
                break;
            case 3:
                playerView = video3;
                break;
            case 4:
                playerView = video4;
                break;
            default:
                playerView = null;
        }

        SimpleExoPlayer player = new SimpleExoPlayer.Builder(appContext).build();
        if (playerView == null){
            try {
                ((MainScreenPostman) activity).closeFragment();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else playerView.setPlayer(player);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(appContext,
                Util.getUserAgent(appContext, "com.avatar.ava"));
        MediaSource videoSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(uri);

        player.prepare(videoSource);
    }

}
