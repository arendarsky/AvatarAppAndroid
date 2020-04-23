package com.avatar.ava.presentation.main.fragments.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
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

    private ArrayList<VideoDTO> videos = new ArrayList<>();

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
    ImageView video1;

    @BindView(R.id.fragment_profile_video_2)
    ImageView video2;

    @BindView(R.id.fragment_profile_video_3)
    ImageView video3;

    @BindView(R.id.fragment_profile_video_4)
    ImageView video4;

    @BindView(R.id.fragment_profile_add_video_btn_1)
    ImageButton addVideoBtn1;

    @BindView(R.id.fragment_profile_add_video_btn_2)
    ImageButton addVideoBtn2;

    @BindView(R.id.fragment_profile_add_video_btn_3)
    ImageButton addVideoBtn3;

    @BindView(R.id.fragment_profile_add_video_btn_4)
    ImageButton addVideoBtn4;

    @BindView(R.id.fragment_profile_container1)
    CardView container1;

    @BindView(R.id.fragment_profile_container2)
    CardView container2;

    @BindView(R.id.fragment_profile_container3)
    CardView container3;

    @BindView(R.id.fragment_profile_container4)
    CardView container4;

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

    @BindView(R.id.profile_progress_bar)
    ProgressBar progressBar;



    @ProvidePresenter
    ProfilePresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(ProfilePresenter.class);
    }


    public ProfileFragment() {
        // Required empty public constructor
    }



    private SimpleExoPlayer exoPlayer;

    @BindView(R.id.fragment_profile_close_fullscreen)
    ImageButton closeBtn;

    @BindView(R.id.fragment_profile_fullscreen)
    PlayerView playerView;

    private DataSource.Factory dataSourceFactory;
    private MediaSource videoSource;

    @OnClick(R.id.fragment_profile_close_fullscreen)
    void closeFullscreen(){
        //update();
        exoPlayer.stop();
        playerView.setVisibility(View.INVISIBLE);
        closeBtn.setVisibility(View.INVISIBLE);
    }

    private void toFullscreen(int id){
        if(currCountVideos > id){
            videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(SERVER_NAME + "/api/video/" + videos.get(id).getName()));
            playerView.setPlayer(exoPlayer);
            exoPlayer.prepare(videoSource);

            playerView.setVisibility(View.VISIBLE);
            closeBtn.setVisibility(View.VISIBLE);

        /*container1.setVisibility(View.INVISIBLE);
        container2.setVisibility(View.INVISIBLE);
        container3.setVisibility(View.INVISIBLE);
        container4.setVisibility(View.INVISIBLE);*/

            exoPlayer.setPlayWhenReady(true);
        }

    }


    @OnClick(R.id.fragment_profile_container1)
    void container1Clicked(){
        toFullscreen(0);
    }

    @OnClick(R.id.fragment_profile_container2)
    void container2Clicked(){
        toFullscreen(1);
    }

    @OnClick(R.id.fragment_profile_container3)
    void container3Clicked(){
        toFullscreen(2);
    }

    @OnClick(R.id.fragment_profile_container4)
    void container4Clicked(){
        toFullscreen(3);
    }

    private MainScreenActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ProfileID = this.getId();
        Toothpick.inject(this, Toothpick.openScope(App.class));

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        description.setEnabled(false);
        name.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        presenter.getProfile();
        activity = (MainScreenActivity) getActivity();
        if (activity != null) activity.showExit();
        //playerView = new PlayerView(appContext);
        exoPlayer = new SimpleExoPlayer.Builder(appContext).build();
        dataSourceFactory = new DefaultDataSourceFactory(appContext,
                Util.getUserAgent(appContext, "XCE FACTOR"));
    }

    @Override
    public void onDetach() {
        super.onDetach();


        exoPlayer.release();
    }

    private String delNameVideo = "";

    public void deleteVideo(){
        progressBar.setVisibility(View.VISIBLE);
        presenter.removeVideo(delNameVideo);
    }

    private String castingVideoName = "";
    private boolean castingIsApproved = false;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void setDataProfile(ProfileDTO person) {
        //set Data
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
        likes.setText(person.getLikesNumber() + " Лайков");
        description.setText(person.getDescription());
        videos = person.getVideos();
        currCountVideos = videos.size();
        showContainers();
        showHints();
        showVideos();



    }

    private void showHints(){
        if(videos.size() >= 1){
            String moderation = "На модерации";
            if(!videos.get(0).isApproved()){
                videoHint1.setText(moderation);
                videoHint1.setVisibility(View.VISIBLE);
            }
            String casting = "В кастинге";
            if(videos.get(0).isActive() && videos.get(0).isApproved()){
                videoHint1.setText(casting);
                videoHint1.setVisibility(View.VISIBLE);
            }
            if(videos.size() >= 2){
                if(!videos.get(1).isApproved()){
                    videoHint2.setText(moderation);
                    videoHint2.setVisibility(View.VISIBLE);
                }
                if(videos.get(1).isActive() && videos.get(1).isApproved()){
                    videoHint2.setText(casting);
                    videoHint2.setVisibility(View.VISIBLE);
                }
                if(videos.size() >= 3){
                    if(!videos.get(2).isApproved()){
                        videoHint3.setText(moderation);
                        videoHint3.setVisibility(View.VISIBLE);
                    }
                    if(videos.get(2).isActive() && videos.get(2).isApproved()){
                        videoHint3.setText(casting);
                        videoHint3.setVisibility(View.VISIBLE);
                    }
                    if(videos.size() >= 4){
                        if(!videos.get(3).isApproved()){
                            videoHint4.setText(moderation);
                            videoHint4.setVisibility(View.VISIBLE);
                        }
                        if(videos.get(3).isActive() && videos.get(3).isApproved()){
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


    private void showImage(int id, ImageView iv){
        Glide.with(this)
                .load(SERVER_NAME + "/api/video/" + videos.get(id).getName())
                .centerCrop()
                .into(iv);
    }

    private void setupVideo(int num, Uri uri){

        switch (num){
            case 1:
                showImage(0, video1);
                break;

            case 2:
                showImage(1, video2);
                break;

            case 3:
                showImage(2, video3);
                break;

            case 4:
                showImage(30, video4);
                break;
        }
    }

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

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = null;
                if (data != null) {
                    selectedImage = data.getData();
                }
                progressBar.setVisibility(View.VISIBLE);
                presenter.uploadPhoto(selectedImage);
            }
        }
    }

    private boolean edit = false;

    private String nameBack = "", descriptionBack = "";

    @OnClick(R.id.fragment_profile_btn_edit)
    public void editProfile(){
        if(!edit){
            edit = true;
            description.setEnabled(true);
            description.setBackgroundResource(R.drawable.profile_field_edit_back);
            name.setEnabled(true);
            name.setBackgroundResource(R.drawable.profile_field_edit_back);
            editPhoto.setVisibility(View.VISIBLE);
            editProfile.setText("Применить");
            nameBack = name.getText().toString();
            descriptionBack = description.getText().toString();
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

    public void backEdit(){
        edit = false;
        description.setEnabled(false);
        description.setBackground(null);
        name.setEnabled(false);
        name.setBackground(null);
        editPhoto.setVisibility(View.GONE);
        editProfile.setText("Редактировать");

        name.setText(nameBack);
        description.setText(descriptionBack);

        showContainers();
        showHints();
        showVideos();
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

    @Override
    public void showMessage(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.fragment_profile_edit_photo)
    void changePhoto(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }


    @OnClick(R.id.fragment_profile_add_video_btn_1)
    void addVideo1(){
        activity.fragmentAction(LOAD_NEW_VIDEO_SCREEN);
    }
    @OnClick(R.id.fragment_profile_add_video_btn_2)
    void addVideo2(){
        activity.fragmentAction(LOAD_NEW_VIDEO_SCREEN);
    }
    @OnClick(R.id.fragment_profile_add_video_btn_3)
    void addVideo3(){
        activity.fragmentAction(LOAD_NEW_VIDEO_SCREEN);
    }
    @OnClick(R.id.fragment_profile_add_video_btn_4)
    void addVideo4(){
        activity.fragmentAction(LOAD_NEW_VIDEO_SCREEN);
    }

    @OnClick(R.id.fragment_profile_settings1)
    void showSettings1(){
        if(videos.size() >= 1){
            castingVideoName = videos.get(0).getName();
            delNameVideo = videos.get(0).getName();
            castingIsApproved = videos.get(0).isApproved();
        }

        showBottomSheet();
    }

    @OnClick(R.id.fragment_profile_settings2)
    void showSettings2(){
        if(videos.size() >= 2){
            delNameVideo = videos.get(1).getName();
            castingVideoName = videos.get(1).getName();
            castingIsApproved = videos.get(1).isApproved();
        }

        showBottomSheet();
    }

    @OnClick(R.id.fragment_profile_settings3)
    void showSettings3(){
        if(videos.size() >= 3){
            delNameVideo = videos.get(2).getName();
            castingVideoName = videos.get(2).getName();
            castingIsApproved = videos.get(2).isApproved();
        }

        showBottomSheet();
    }

    @OnClick(R.id.fragment_profile_settings4)
    void showSettings4(){
        if(videos.size() >= 4){
            delNameVideo = videos.get(3).getName();
            castingVideoName = videos.get(3).getName();
            castingIsApproved = videos.get(3).isApproved();
        }

        showBottomSheet();
    }
}
