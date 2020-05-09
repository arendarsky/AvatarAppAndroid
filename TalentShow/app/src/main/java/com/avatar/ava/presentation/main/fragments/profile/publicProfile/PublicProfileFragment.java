package com.avatar.ava.presentation.main.fragments.profile.publicProfile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @BindView(R.id.public_profile_progressbar)
    ProgressBar progressBar;

    @BindView(R.id.fragment_public_profile_name)
    TextView name;

    @BindView(R.id.fragment_public_profile_description)
    TextView description;

    @BindView(R.id.fragment_public_profile_video_1)
    ImageView video1;

    @BindView(R.id.fragment_public_profile_video_2)
    ImageView video2;

    @BindView(R.id.fragment_public_profile_video_3)
    ImageView video3;

    @BindView(R.id.fragment_public_profile_video_4)
    ImageView video4;

    @BindView(R.id.fragment_public_profile_container1)
    CardView container1;

    @BindView(R.id.fragment_public_profile_container2)
    CardView container2;

    @BindView(R.id.fragment_public_profile_container3)
    CardView container3;

    @BindView(R.id.fragment_public_profile_container4)
    CardView container4;

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
        if (getContext() == null){
            try {
                ((MainScreenPostman) activity).closeFragment();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        ButterKnife.bind(this, v);

        if (getArguments() == null) {
            try {
                ((MainScreenPostman) activity).closeFragment();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
            this.id = getArguments().getInt("id");

        return v;
    }


    private void toFullscreen(int id){
        if(currCountVideos > id){
            try {
                ((MainScreenPostman) activity).openFullScreen(videos.get(id).getName());
            } catch (Exception e) {
                e.printStackTrace();
            }}

    }

    @OnClick(R.id.fragment_public_profile_container1)
    void container1Clicked(){
        toFullscreen(0);
    }

    @OnClick(R.id.fragment_public_profile_container2)
    void container2Clicked(){
        toFullscreen(1);
    }

    @OnClick(R.id.fragment_public_profile_container3)
    void container3Clicked(){
        toFullscreen(2);
    }

    @OnClick(R.id.fragment_public_profile_container4)
    void container4Clicked(){
        toFullscreen(3);
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
        if(person.getName() != null) {
            name.setVisibility(View.VISIBLE);
            name.setText(person.getName());
        }
        else name.setVisibility(View.VISIBLE);
        description.setVisibility(View.VISIBLE);
        if(person.getDescription() != null)
            description.setText(person.getDescription());
        videos = person.getVideos();
        if(videos != null){
            currCountVideos = videos.size();
        }else{
            currCountVideos = 0;
        }

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

    private void showVideos(){
        if(currCountVideos >= 1){
            container1.setVisibility(View.VISIBLE);
            setupVideo(1);
            video1.setVisibility(View.VISIBLE);
            if(currCountVideos >= 2){
                container2.setVisibility(View.VISIBLE);
                setupVideo(2);
                video2.setVisibility(View.VISIBLE);
                if(currCountVideos >= 3){
                    container3.setVisibility(View.VISIBLE);
                    setupVideo(3);
                    video3.setVisibility(View.VISIBLE);
                }
                if(currCountVideos == 4){
                    container4.setVisibility(View.VISIBLE);
                    setupVideo(4);
                    video4.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void showImage(int id, ImageView iv){
        Glide.with(this)
                .load(SERVER_NAME + "/api/video/" + videos.get(id).getName())
                .placeholder(appContext.getResources().getDrawable(R.drawable.activity_add_new_video_bg))
                .centerCrop()
                .into(iv);
    }

    private void setupVideo(int num){

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
                showImage(3, video4);
                break;
        }
    }

}
