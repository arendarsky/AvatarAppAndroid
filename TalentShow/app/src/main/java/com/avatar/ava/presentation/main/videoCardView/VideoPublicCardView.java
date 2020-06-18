package com.avatar.ava.presentation.main.videoCardView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.domain.entities.VideoDTO;
import com.avatar.ava.presentation.main.MainScreenActivity;
import com.avatar.ava.presentation.main.MainScreenPostman;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;

import static com.avatar.ava.DataModule.SERVER_NAME;

public class VideoPublicCardView extends ConstraintLayout {

    @Inject
    Context appContext;

    ImageView video;
    CardView container;

    VideoDTO videoDTO;
    Activity activity;

    BottomSheetDialog bottomSheetDialog;

    private boolean isShare = false;

    public void setBottomSheetDialog(BottomSheetDialog bottomSheetDialog){
        this.bottomSheetDialog = bottomSheetDialog;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public void setVideoDTO(VideoDTO videoDTO){
        this.videoDTO = videoDTO;
    }

    public VideoDTO getVideoDTO(){
        return this.videoDTO;
    }

    public VideoPublicCardView(Context context) {
        super(context);
        init(context);
    }

    public VideoPublicCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoPublicCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        View v = LayoutInflater.from(context).inflate(R.layout.video_public_card_view, this, true);
        ButterKnife.bind(this, v);
        Toothpick.inject(this, Toothpick.openScope(App.class));
        video = v.findViewById(R.id.video_public_card_view_video);
        container = v.findViewById(R.id.video_public_card_view);
    }

    private void shareVideo(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        //sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String shareBody = "https://web.xce-factor.ru/#/video/" + videoDTO.getName();
        //String shareSub = link;
        //sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        activity.startActivity(Intent.createChooser(sharingIntent, "Share using"));


    }

    @OnClick(R.id.video_public_card_view_share_btn)
    public void onShareClicked(){
        //shareVideo();
        isShare = true;
        bottomSheetDialog.show();

    }

    @OnClick(R.id.video_public_card_view)
    void containerClicked(){
        try {
            ((MainScreenPostman) activity).openFullScreen(videoDTO.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showVideo(){
        container.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(SERVER_NAME + "/api/video/" + videoDTO.getName())
                .placeholder(appContext.getResources().getDrawable(R.drawable.activity_add_new_video_bg))
                .centerCrop()
                .into(video);
        video.setVisibility(View.VISIBLE);
    }

    public boolean isShare() {
        return isShare;
    }

    public void setShare(boolean share) {
        isShare = share;
    }
}
