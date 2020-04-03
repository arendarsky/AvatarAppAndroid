package com.avatar.ava.presentation.main.fragments.profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.domain.entities.ProfileDTO;
import com.avatar.ava.domain.entities.VideoDTO;
import com.avatar.ava.presentation.main.BottomSheetFragments.ProfileVideoBottomSheet;
import com.avatar.ava.presentation.main.MainScreenActivity;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;

import static android.app.Activity.RESULT_OK;
import static com.avatar.ava.DataModule.SERVER_NAME;


public class ProfileFragment extends MvpAppCompatFragment implements ProfileView {

    public static int ProfileID;
    private final int LOAD_NEW_VIDEO_SCREEN = 4;
    private int currCountVideos = 0;

    ArrayList<VideoDTO> videos = new ArrayList<VideoDTO>();

    @Inject
    Context appContext;

    @InjectPresenter
    ProfilePresenter presenter;

    @BindView(R.id.fragment_profile_image)
    ImageView profileImage;

    @BindView(R.id.fragment_profile_name)
    EditText name;

    @BindView(R.id.fragment_profile_likes)
    TextView likes;

    @BindView(R.id.fragment_profile_description)
    EditText description;


    @BindView(R.id.fragment_profile_edit_photo)
    TextView editPhoto;

    @BindView(R.id.fragment_profile_btn_edit)
    TextView editProfile;

    @BindView(R.id.fragment_profile_video_1)
    PlayerView video1;

    @BindView(R.id.fragment_profile_video_2)
    PlayerView video2;

    @BindView(R.id.fragment_profile_video_3)
    PlayerView video3;

    @BindView(R.id.fragment_profile_video_4)
    PlayerView video4;

    @BindView(R.id.fragment_profile_add_video_btn_1)
    ImageButton addVideoBtn1;

    @BindView(R.id.fragment_profile_add_video_btn_2)
    ImageButton addVideoBtn2;

    @BindView(R.id.fragment_profile_add_video_btn_3)
    ImageButton addVideoBtn3;

    @BindView(R.id.fragment_profile_add_video_btn_4)
    ImageButton addVideoBtn4;

    @BindView(R.id.fragment_profile_container1)
    ConstraintLayout container1;

    @BindView(R.id.fragment_profile_container2)
    ConstraintLayout container2;

    @BindView(R.id.fragment_profile_container3)
    ConstraintLayout container3;

    @BindView(R.id.fragment_profile_container4)
    ConstraintLayout container4;

    @BindView(R.id.fragment_profile_settings1)
    View settings1;

    @BindView(R.id.fragment_profile_settings2)
    View settings2;

    @BindView(R.id.fragment_profile_settings3)
    View settings3;

    @BindView(R.id.fragment_profile_settings4)
    View settings4;

    @BindView(R.id.fragment_profile_in_casting_1)
    TextView videoHint1;

    @BindView(R.id.fragment_profile_in_casting_2)
    TextView videoHint2;

    @BindView(R.id.fragment_profile_in_casting_3)
    TextView videoHint3;

    @BindView(R.id.fragment_profile_in_casting_4)
    TextView videoHint4;

    @ProvidePresenter
    ProfilePresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(ProfilePresenter.class);
    }


    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    MainScreenActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ProfileID = this.getId();
        //if(!this.isAdded())getActivity().getSupportFragmentManager().beginTransaction().add(this, "ProfileFragment1").commit();
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        video1 = new PlayerView(getContext());
        video2 = new PlayerView(getContext());
        video3 = new PlayerView(getContext());
        video4 = new PlayerView(getContext());
        ButterKnife.bind(this, v);
        return v;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
        description.setEnabled(false);
        name.setEnabled(false);
        presenter.getProfile();
        activity = (MainScreenActivity) getActivity();
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("ProfileLog", "detach");
        if(player1 != null)
        player1.release();
        if(player2 != null)
        player2.release();
        if(player3 != null)
        player3.release();
        if(player4 != null)
        player4.release();
    }

    String delNameVideo = "";
    public void deleteVideo(){
        Log.d("ProfileLog", "delName: " + delNameVideo + " cast " + castingVideoName);
        presenter.removeVideo(delNameVideo);
    }
    String castingVideoName = "";
    boolean castingIsApproved = false;
    public void setCastingVideo(){
        if(castingIsApproved){
            presenter.setActive(castingVideoName);
        }else{
            Toast.makeText(getContext(), "Видео ещё не прошло модерацию", Toast.LENGTH_LONG).show();
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


    @Override
    public void setDataProfile(ProfileDTO person) {
        //set Data
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
        likes.setText(person.getLikesNumber() + " Лайков");
        description.setText(person.getDescription());
        videos = person.getVideos();
        currCountVideos = videos.size();
        showContainers();
        //Log.d("ProfileLog", "videos " + currCountVideos + " size " + videos.size() + " aprov " + videos.get(0).isApproved());

        showHints();
        showVideos();
        //showBottomSheet();
    }
    String moderation = "На модерации";
    String casting = "В кастинге";
    private void showHints(){
        if(videos.size() >= 1){
            if(videos.get(0).isApproved() == false){
                videoHint1.setText(moderation);
                videoHint1.setVisibility(View.VISIBLE);
            }
            if(videos.get(0).isActive() == true && videos.get(0).isApproved()){
                videoHint1.setText(casting);
                videoHint1.setVisibility(View.VISIBLE);
            }
            if(videos.size() >= 2){
                if(videos.get(1).isApproved() == false){
                    videoHint2.setText(moderation);
                    videoHint2.setVisibility(View.VISIBLE);
                }
                if(videos.get(1).isActive() == true && videos.get(1).isApproved()){
                    videoHint2.setText(casting);
                    videoHint2.setVisibility(View.VISIBLE);
                }
                if(videos.size() >= 3){
                    if(videos.get(2).isApproved() == false){
                        videoHint3.setText(moderation);
                        videoHint3.setVisibility(View.VISIBLE);
                    }
                    if(videos.get(2).isActive() == true && videos.get(2).isApproved()){
                        videoHint3.setText(casting);
                        videoHint3.setVisibility(View.VISIBLE);
                    }
                    if(videos.size() >= 4){
                        if(videos.get(3).isApproved() == false){
                            videoHint4.setText(moderation);
                            videoHint4.setVisibility(View.VISIBLE);
                        }
                        if(videos.get(3).isActive() == true && videos.get(3).isApproved()){
                            videoHint4.setText(casting);
                            videoHint4.setVisibility(View.VISIBLE);
                        }

                    }
                }
            }
        }
    }

    private void showBottomSheet(){
        ProfileVideoBottomSheet profileVideoBottomSheet =
                ProfileVideoBottomSheet.newInstance();
        profileVideoBottomSheet.show(getParentFragmentManager(),
                ProfileVideoBottomSheet.TAG);
    }


    SimpleExoPlayer player1, player2, player3, player4;
    private void setupVideo(int num, Uri uri){
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(appContext,
                Util.getUserAgent(appContext, getResources().getString(R.string.app_name)));
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(uri);
        switch (num){
            case 1:
                player1 = new SimpleExoPlayer.Builder(appContext).build();
                video1.setPlayer(player1);
                // Prepare the player with the source.
                player1.prepare(videoSource);
                //player.setPlayWhenReady(true);
                break;

            case 2:
                player2 = new SimpleExoPlayer.Builder(appContext).build();
                video2.setPlayer(player2);
                // Prepare the player with the source.
                player2.prepare(videoSource);
                //player.setPlayWhenReady(true);
                break;

            case 3:
                player3 = new SimpleExoPlayer.Builder(appContext).build();
                video3.setPlayer(player3);
                // Prepare the player with the source.
                player3.prepare(videoSource);
                //player.setPlayWhenReady(true);
                break;

            case 4:
                player4 = new SimpleExoPlayer.Builder(appContext).build();
                video4.setPlayer(player4);
                // Prepare the player with the source.
                player4.prepare(videoSource);
                //player.setPlayWhenReady(true);
                break;
        }
    }
    /*private void setupVideo(int num, Uri uri){
        PlayerView playerView;
        SimpleExoPlayer player;
        switch (num){
            case 1:
                playerView = video1;
                player = player1;
                break;
            case 2:
                playerView = video2;
                player = player2;
                break;
            case 3:
                playerView = video3;
                player = player3;
                break;
            case 4:
                playerView = video4;
                player = player4;
                break;
            default:
                playerView = null;
        }
        player = new SimpleExoPlayer.Builder(appContext).build();
        playerView.setPlayer(player);
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(appContext,
                Util.getUserAgent(appContext, "Talent Show"));
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(uri);
        // Prepare the player with the source.
        player.prepare(videoSource);
        //player.setPlayWhenReady(true);
    }*/


    private void showVideos(){
        if(currCountVideos >= 1){
            setupVideo(1, Uri.parse(SERVER_NAME + "/api/video/" + videos.get(0).getName()));
            video1.setVisibility(View.VISIBLE);
            addVideoBtn1.setVisibility(View.INVISIBLE);
            settings1.setVisibility(View.VISIBLE);
            if(currCountVideos >= 2){
                setupVideo(2, Uri.parse(SERVER_NAME + "/api/video/" + videos.get(1).getName()));
                video2.setVisibility(View.VISIBLE);
                addVideoBtn2.setVisibility(View.INVISIBLE);
                settings2.setVisibility(View.VISIBLE);
                if(currCountVideos >= 3){
                    setupVideo(3, Uri.parse(SERVER_NAME + "/api/video/" + videos.get(2).getName()));
                    video3.setVisibility(View.VISIBLE);
                    addVideoBtn3.setVisibility(View.INVISIBLE);
                    settings3.setVisibility(View.VISIBLE);
                }
                if(currCountVideos == 4){
                    setupVideo(4, Uri.parse(SERVER_NAME + "/api/video/" + videos.get(3).getName()));
                    video4.setVisibility(View.VISIBLE);
                    addVideoBtn4.setVisibility(View.INVISIBLE);
                    settings4.setVisibility(View.VISIBLE);
                }
            }
        }
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    //profileImage.setImageURI(selectedImage);
                    presenter.uploadPhoto(selectedImage);
                    Log.d("ProfileFragment", "setImage");
                }
        }
    }

    private boolean edit = false;

    @OnClick(R.id.fragment_profile_btn_edit)
    public void editProfile(){
        if(!edit){
            edit = true;
            description.setEnabled(true);
            description.setBackgroundResource(R.drawable.profile_fragment_edit_bg);
            name.setEnabled(true);
            name.setBackgroundResource(R.drawable.profile_fragment_edit_bg);
            editPhoto.setVisibility(View.VISIBLE);
            editProfile.setText("Применить");
        }else{
            edit = false;
            presenter.setDescription(description.getText().toString());
            presenter.setName(name.getText().toString());
            description.setEnabled(false);
            description.setBackground(null);
            name.setEnabled(false);
            name.setBackground(null);
            editPhoto.setVisibility(View.GONE);
            editProfile.setText("Редактировать");

            showContainers();
            showHints();
            showVideos();
        }

    }
    @Override
    public void update(){
        video1.setVisibility(View.INVISIBLE);
        video2.setVisibility(View.INVISIBLE);
        video3.setVisibility(View.INVISIBLE);
        video4.setVisibility(View.INVISIBLE);

        videoHint1.setVisibility(View.INVISIBLE);
        videoHint2.setVisibility(View.INVISIBLE);
        videoHint3.setVisibility(View.INVISIBLE);
        videoHint4.setVisibility(View.INVISIBLE);

        container1.setVisibility(View.VISIBLE);
        container2.setVisibility(View.VISIBLE);
        container3.setVisibility(View.VISIBLE);
        container4.setVisibility(View.VISIBLE);

        addVideoBtn1.setVisibility(View.VISIBLE);
        settings1.setVisibility(View.INVISIBLE);
        addVideoBtn2.setVisibility(View.VISIBLE);
        settings2.setVisibility(View.INVISIBLE);
        addVideoBtn3.setVisibility(View.VISIBLE);
        settings3.setVisibility(View.INVISIBLE);
        addVideoBtn4.setVisibility(View.VISIBLE);
        settings4.setVisibility(View.INVISIBLE);



        showContainers();
        showHints();
        showVideos();
    }

    @OnClick(R.id.fragment_profile_edit_photo)
    void changePhoto(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }


    @OnClick(R.id.fragment_profile_add_video_btn_1)
    public void addVideo1(){
        activity.fragmentAction(LOAD_NEW_VIDEO_SCREEN);
    }
    @OnClick(R.id.fragment_profile_add_video_btn_2)
    public void addVideo2(){
        activity.fragmentAction(LOAD_NEW_VIDEO_SCREEN);
    }
    @OnClick(R.id.fragment_profile_add_video_btn_3)
    public void addVideo3(){
        activity.fragmentAction(LOAD_NEW_VIDEO_SCREEN);
    }
    @OnClick(R.id.fragment_profile_add_video_btn_4)
    public void addVideo4(){
        activity.fragmentAction(LOAD_NEW_VIDEO_SCREEN);
    }

    @OnClick(R.id.fragment_profile_settings1)
    public void showSettings1(){
        if(videos.size() >= 1){
            castingVideoName = videos.get(0).getName();
            delNameVideo = videos.get(0).getName();
            castingIsApproved = videos.get(0).isApproved();
        }

        showBottomSheet();
    }

    @OnClick(R.id.fragment_profile_settings2)
    public void showSettings2(){
        if(videos.size() >= 2){
            delNameVideo = videos.get(1).getName();
            castingVideoName = videos.get(1).getName();
            castingIsApproved = videos.get(1).isApproved();
        }

        showBottomSheet();
    }

    @OnClick(R.id.fragment_profile_settings3)
    public void showSettings3(){
        if(videos.size() >= 3){
            delNameVideo = videos.get(2).getName();
            castingVideoName = videos.get(2).getName();
            castingIsApproved = videos.get(2).isApproved();
        }

        showBottomSheet();
    }

    @OnClick(R.id.fragment_profile_settings4)
    public void showSettings4(){
        if(videos.size() >= 4){
            delNameVideo = videos.get(3).getName();
            castingVideoName = videos.get(3).getName();
            castingIsApproved = videos.get(3).isApproved();
        }

        showBottomSheet();
    }


}
