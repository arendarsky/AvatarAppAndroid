package com.avatar.ava.presentation.main.fragments.casting;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.avatar.ava.R;
import com.avatar.ava.domain.entities.PersonDTO;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ClippingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import static com.avatar.ava.DataModule.SERVER_NAME;

@Layout(R.layout.casting_card_view)
public class CastingCard {

    @View(R.id.activity_casting_video)
    public PlayerView playerView;

    @View(R.id.casting_activity_avatar)
    public ImageView ava;

    @View(R.id.activity_casting_name)
    public TextView name;

    @View(R.id.activity_casting_description)
    public TextView description;



    private PersonDTO mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;
    private DataSource.Factory mDataSourceFactory;
    private SimpleExoPlayer mPlayer;
    private Callback mCallback;
    private PersonDTO prevPerson ;

    public CastingCard(Context context, PersonDTO profile, SwipePlaceHolderView swipeView,
                       SimpleExoPlayer exoPlayer, DataSource.Factory dataSourceFactory,
                       Callback callback) {
        mContext = context;
        prevPerson = mProfile;
        mProfile = profile;
        mSwipeView = swipeView;
        mDataSourceFactory = dataSourceFactory;
        mPlayer = exoPlayer;
        mCallback = callback;
    }

    @Resolve
    public void onResolved(){
        //init

        if(prevPerson != mProfile){
            Log.d("CastingSwipe", "OnResolved " + mProfile.getName());
            if(mProfile.getPhoto() == null){
                Glide.with(mContext)
                        .load(R.drawable.empty_profile_icon)
                        .circleCrop()
                        .into(ava);
            }
            else{
                Glide.with(mContext)
                        .load(SERVER_NAME + "/api/profile/photo/get/" + mProfile.getPhoto())
                        .circleCrop()
                        .into(ava);
            }
            name.setText(mProfile.getName());
            description.setText(mProfile.getDescription());
            playerView.setPlayer(mPlayer);

            String videoLink = SERVER_NAME + "/api/video/" + mProfile.getVideo().getName();
            int start = (int)mProfile.getVideo().getStartTime() * 1000;
            int end = (int)mProfile.getVideo().getEndTime() * 1000;
            mPlayer.stop();
            MediaSource videoSource = new ProgressiveMediaSource.Factory(mDataSourceFactory)
                    .createMediaSource(Uri.parse(videoLink));
            ClippingMediaSource clippingMediaSource =  new ClippingMediaSource(videoSource, start, end);
            mPlayer.prepare(clippingMediaSource);

            mPlayer.setPlayWhenReady(true);
        }

    }

    @SwipeOut
    public void onSwipedOut(){
        Log.d("EventCard", "onSwipedOut");
        //mSwipeView.addView(this);
        mCallback.onSwipeDisLike();
    }

    @SwipeCancelState
    public void onSwipeCancelState(){
        Log.d("EventCard", "onSwipeCancelState");
    }

    @SwipeIn
    public void onSwipeIn(){
        Log.d("EventCard", "onSwipedIn");
        mCallback.onSwipeLike();
    }

    @SwipeInState
    public void onSwipeInState(){
        Log.d("EventCard", "onSwipeInState");
    }

    @SwipeOutState
    public void onSwipeOutState(){
        Log.d("EventCard", "onSwipeOutState");
    }

    @Click(R.id.casting_activity_avatar)
    public void avatartClicked(){
        mCallback.openProfile();
    }
    @Click(R.id.activity_casting_name)
    public void nameClicked(){
        mCallback.openProfile();
    }

    interface Callback {
        void onSwipeLike();
        void onSwipeDisLike();
        void openProfile();
        void restartVideo();
    }
}
