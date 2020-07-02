package com.avatar.ava.presentation.main.fragments.profile.publicProfile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.avatar.ava.presentation.main.fragments.casting.CircleProgressBar;
import com.avatar.ava.presentation.main.videoCardView.VideoCardView;
import com.avatar.ava.presentation.main.videoCardView.VideoPublicCardView;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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
    private BottomSheetDialog bottomSheetDialog;
    private boolean videoDownloading = false;

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



    @BindView(R.id.fragment_public_profile_container1)
    VideoPublicCardView videoPublicCardView1;

    @BindView(R.id.fragment_public_profile_container2)
    VideoPublicCardView videoPublicCardView2;

    @BindView(R.id.fragment_public_profile_container3)
    VideoPublicCardView videoPublicCardView3;

    @BindView(R.id.fragment_public_profile_container4)
    VideoPublicCardView videoPublicCardView4;

    @BindView(R.id.fragment_public_profile_container5)
    VideoPublicCardView videoPublicCardView5;

    @BindView(R.id.fragment_profile_parent)
    ConstraintLayout parent;

    @BindView(R.id.profile_public_loading)
    ConstraintLayout videoLoading;

    @BindView(R.id.profile_public_loading_progbar)
    CircleProgressBar loadingProgbar;

    private Activity activity;

    String instName = null;


    ArrayList<VideoPublicCardView> videoPublicCardViews = new ArrayList<>();

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

    /*@OnClick(R.id.fragment_public_profile_container1)
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

    @OnClick(R.id.fragment_public_profile_container5)
    void container5Clicked(){
        toFullscreen(4);
    }*/
    private String shareVideoName = "";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
        presenter.getProfile(id);
        description.setEnabled(false);

        videoPublicCardViews.add(videoPublicCardView1);
        videoPublicCardViews.add(videoPublicCardView2);
        videoPublicCardViews.add(videoPublicCardView3);
        videoPublicCardViews.add(videoPublicCardView4);
        videoPublicCardViews.add(videoPublicCardView5);

        bottomSheetDialog = new BottomSheetDialog(activity);
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.share_bottomsheet, null);
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.setCanceledOnTouchOutside(true);

        for(VideoPublicCardView videoPublicCardView : videoPublicCardViews){
            videoPublicCardView.setActivity(activity);
            videoPublicCardView.setBottomSheetDialog(bottomSheetDialog);
        }


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
            for(VideoPublicCardView videoPublicCardView : videoPublicCardViews){
                if(videoPublicCardView.isShare()){
                    shareVideoName = videoPublicCardView.getVideoDTO().getName();
                    videoPublicCardView.setShare(false);
                }
            }
            presenter.downloadVideo(shareVideoName);
        });

        text.setOnClickListener(v -> {
            for(VideoPublicCardView videoPublicCardView : videoPublicCardViews){
                if(videoPublicCardView.isShare()){
                    shareVideoName = videoPublicCardView.getVideoDTO().getName();
                    videoPublicCardView.setShare(false);
                }
            }
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            //sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String shareBody = "https://web.xce-factor.ru/#/video/" + shareVideoName;
            //String shareSub = link;
            //sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        });
    }


    @Override
    public void setDataProfile(PublicProfileDTO person) {
        if(person.getPhoto() == null){
            Glide.with(this)
                    .load(R.drawable.empty_profile)
                    .centerCrop()
                    .into(profileImage);
        }else{
            Glide.with(this)
                    .load(SERVER_NAME + "/api/profile/photo/get/" + person.getPhoto())
                    .circleCrop()
                    .into(profileImage);
        }
        //if(instName != null && !instName.equals(""))
        instName = person.getInstagramLogin();
        Log.d("PublicProfileLog", instName);

        if(person.getName() != null) {
            name.setVisibility(View.VISIBLE);
            name.setText(person.getName());
        }
        else name.setVisibility(View.VISIBLE);
        if(person.getDescription() != null){
            description.setTextColor(getResources().getColor(R.color.white));
            description.setText(person.getDescription());
        } else {
            description.setText(getResources().getString(R.string.no_description));
        }
        videos = person.getVideos();
        if(videos != null){
            currCountVideos = videos.size();
        }else{
            currCountVideos = 0;
        }


        for(int i = 0; i < videos.size(); i++){
            videoPublicCardViews.get(i).setVideoDTO(videos.get(i));
        }
        for(int i = videos.size(); i < videoPublicCardViews.size(); i++){
            videoPublicCardViews.get(i).setVideoDTO(null);
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

    @Override
    public void showLoadingProgBar() {
        videoLoading.setVisibility(View.VISIBLE);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void changeLoadingState(Float aFloat) {
        loadingProgbar.setProgress(aFloat);
    }

    @Override
    public void loadingComplete(Uri uri) {
        for(VideoPublicCardView videoPublicCardView : videoPublicCardViews){
            videoPublicCardView.setShare(false);
        }

        videoDownloading = false;
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
        for(VideoPublicCardView videoPublicCardView : videoPublicCardViews){
            videoPublicCardView.setShare(false);
        }

        videoDownloading = false;
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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

    private void showVideos(){
        for(VideoPublicCardView videoPublicCardView : videoPublicCardViews){
            if(videoPublicCardView.getVideoDTO() != null)
            videoPublicCardView.showVideo();
        }
    }

    @OnClick(R.id.fragment_public_profile_inst)
    void instIconClicked(){
        if(instName != null){
            Uri address = Uri.parse("https://www.instagram.com/" + instName + "/");
            Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, address);
            startActivity(openLinkIntent);
        }
        else{
            Toast.makeText(appContext, "Не указан профиль в Instagram", Toast.LENGTH_LONG).show();
        }

    }

    /*private void showImage(int id, ImageView iv){
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

            case 5:
                showImage(4, video5);
                break;
        }
    }

    private void shareVideo(int num){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        //sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String shareBody = "https://web.xce-factor.ru/#/video/" + videos.get(num).getName();
        //String shareSub = link;
        //sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    @OnClick(R.id.rating_item_share_btn1)
    public void onShare1Clicked(){
        shareVideo(0);
    }

    @OnClick(R.id.rating_item_share_btn2)
    public void onShare2Clicked(){
        shareVideo(1);
    }

    @OnClick(R.id.rating_item_share_btn3)
    public void onShare3Clicked(){
        shareVideo(2);
    }

    @OnClick(R.id.rating_item_share_btn4)
    public void onShare4Clicked(){
        shareVideo(3);
    }

    @OnClick(R.id.rating_item_share_btn5)
    public void onShare5Clicked(){
        shareVideo(4);
    }*/
}
