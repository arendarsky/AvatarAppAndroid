package com.avatar.ava.presentation.main.videoCardView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avatar.ava.R;
import com.avatar.ava.domain.entities.VideoDTO;
import com.avatar.ava.presentation.main.BottomSheetFragments.ProfileVideoBottomSheet;
import com.avatar.ava.presentation.main.MainScreenActivity;
import com.avatar.ava.presentation.main.MainScreenPostman;
import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.avatar.ava.DataModule.SERVER_NAME;

public class VideoCardView extends ConstraintLayout{

    ImageView video;
    TextView addVideoBtn;
    ImageView addVideoIcon;
    View settings;
    TextView videoHint;
    CardView cardView;

    VideoDTO videoDTO;
    MainScreenActivity activity;

    private final int LOAD_NEW_VIDEO_SCREEN = 4;

    public void setActivity(MainScreenActivity activity){
        this.activity = activity;
    }

    public void setVideoDTO(VideoDTO videoDTO){
        this.videoDTO = videoDTO;
    }

    public VideoDTO getVideoDTO(){
        return this.videoDTO;
    }



    public VideoCardView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public VideoCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.video_card_view, this, true);
        ButterKnife.bind(this, v);
        video = v.findViewById(R.id.video_card_view_video);
        addVideoBtn = v.findViewById(R.id.video_card_view_add_video_btn);
        addVideoIcon = v.findViewById(R.id.video_card_view_add_icon);
        settings = v.findViewById(R.id.video_card_view_settings);
        videoHint = v.findViewById(R.id.video_card_view_in_casting);
        cardView = v.findViewById(R.id.video_card_view);


    }

    @OnClick(R.id.video_card_view)
    void toFullscreen(){
        ((MainScreenPostman) activity).openFullScreen(videoDTO.getName());
    }

    public void setVis(int code){
        cardView.setVisibility(code);
    }


    public void showImage(){
        if (!(videoDTO.getStartTime() == -1))
            Glide.with(this)
                    .load(SERVER_NAME + "/api/video/" + videoDTO.getName())
                    .centerCrop()
                    .into(video);
        else
            Glide.with(this)
                    .load(R.drawable.video_background)
                    .centerCrop()
                    .into(video);

    }

    public void setupVideo(){
        showImage();
        video.setVisibility(View.VISIBLE);
        addVideoBtn.setVisibility(View.INVISIBLE);
        addVideoIcon.setVisibility(View.INVISIBLE);
        settings.setVisibility(View.VISIBLE);
    }

    public void showHint(){
        String loading = "Загружается";
        String moderation = "На модерации";
        String casting = "В кастинге";
        String failModeration = "Не прошло проверку";

        if(videoDTO.getStartTime()==-1) {
            videoHint.setVisibility(View.VISIBLE);
            settings.setVisibility(View.INVISIBLE);
            //loadingTextView = videoHint;
            videoHint.setText(loading);
        }
        else {
            if(videoDTO.isApproved() == null){
                videoHint.setVisibility(View.VISIBLE);
                videoHint.setText(moderation);
            }
            else{
                if (!videoDTO.isApproved()) {
                    videoHint.setVisibility(View.VISIBLE);
                    videoHint.setText(failModeration);
                }
                if (videoDTO.isActive() && videoDTO.isApproved()) {
                    videoHint.setText(casting);
                    videoHint.setVisibility(View.VISIBLE);
                }
            }

        }
    }


    private void showBottomSheet(){
        ProfileVideoBottomSheet profileVideoBottomSheet =
                ProfileVideoBottomSheet.newInstance();
        profileVideoBottomSheet.show(activity.getSupportFragmentManager(),
                ProfileVideoBottomSheet.TAG);
    }

    public static String castingVideoName = "", delNameVideo = "";
    public static Boolean castingIsApproved = false;

    @OnClick(R.id.video_card_view_settings)
    void showSettings(){
        castingVideoName = videoDTO.getName();
        delNameVideo = videoDTO.getName();
        castingIsApproved = videoDTO.isApproved();


        showBottomSheet();

    }

    @OnClick(R.id.video_card_view_add_video_btn)
    void addVideo1(){
        activity.fragmentAction(LOAD_NEW_VIDEO_SCREEN);
    }

    public void update(){
        video.setVisibility(View.INVISIBLE);
        videoHint.setVisibility(View.INVISIBLE);
        cardView.setVisibility(View.VISIBLE);
        addVideoBtn.setVisibility(View.VISIBLE);
        addVideoIcon.setVisibility(View.VISIBLE);
        settings.setVisibility(View.INVISIBLE);

    }



    interface Callback{
        public void toFullscreen();
    }

}
