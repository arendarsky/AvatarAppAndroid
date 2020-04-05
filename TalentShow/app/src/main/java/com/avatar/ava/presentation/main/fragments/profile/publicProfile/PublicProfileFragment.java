package com.avatar.ava.presentation.main.fragments.profile.publicProfile;

import android.content.Context;
import android.media.MediaCodec;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.domain.entities.ProfileDTO;
import com.avatar.ava.domain.entities.PublicProfileDTO;
import com.avatar.ava.domain.entities.VideoDTO;
import com.avatar.ava.presentation.main.MainScreenActivity;
import com.avatar.ava.presentation.main.fragments.profile.ProfilePresenter;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.MediaCodecVideoRenderer;

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

    ArrayList<VideoDTO> videos = new ArrayList<VideoDTO>();
    int id = 0;

    @BindView(R.id.fragment_public_profile_image)
    ImageView profileImage;

    @BindView(R.id.fragment_public_profile_name)
    EditText name;

    @BindView(R.id.fragment_public_profile_description)
    EditText description;

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


    @ProvidePresenter
    PublicProfilePresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(PublicProfilePresenter.class);
    }


    public PublicProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //if(!this.isAdded())getActivity().getSupportFragmentManager().beginTransaction().add(this, "ProfileFragment1").commit();
        View v = inflater.inflate(R.layout.fragment_public_profile, container, false);
        video1 = new PlayerView(getContext());
        video2 = new PlayerView(getContext());
        video3 = new PlayerView(getContext());
        video4 = new PlayerView(getContext());
        ButterKnife.bind(this, v);
        this.id = getArguments().getInt("id");
        return v;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
        presenter.getProfile(id);
        /*if(currCountVideos == 0){
            container2.setVisibility(View.INVISIBLE);
            container3.setVisibility(View.INVISIBLE);
            container4.setVisibility(View.INVISIBLE);
        }else if(currCountVideos == 1){
            container3.setVisibility(View.INVISIBLE);
            container4.setVisibility(View.INVISIBLE);
        }else if(currCountVideos == 2){
            container4.setVisibility(View.INVISIBLE);
        }*/
        description.setEnabled(false);
    }


    @Override
    public void setDataProfile(PublicProfileDTO person) {
        if(person.getPhoto() == null){
            Glide.with(getView())
                    .load(R.drawable.empty_profile_icon)
                    .circleCrop()
                    .into(profileImage);
        }else{
            Glide.with(getView())
                    .load(SERVER_NAME + "/api/profile/photo/get/" + person.getPhoto())
                    .circleCrop()
                    .into(profileImage);
        }

        name.setText(person.getName());
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
        playerView.setPlayer(player);
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(appContext,
                Util.getUserAgent(appContext, "com.avatar.ava"));
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(uri);
        // Prepare the player with the source.
        player.prepare(videoSource);



        //player.setPlayWhenReady(true);
    }

}
