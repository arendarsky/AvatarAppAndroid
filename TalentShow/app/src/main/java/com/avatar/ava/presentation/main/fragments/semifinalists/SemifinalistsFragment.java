package com.avatar.ava.presentation.main.fragments.semifinalists;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplitude.api.Amplitude;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.domain.entities.BattleDTO;
import com.avatar.ava.domain.entities.BattleParticipant;
import com.avatar.ava.domain.entities.ProfileSemifinalistsDTO;
import com.avatar.ava.domain.entities.PublicProfileDTO;
import com.avatar.ava.domain.entities.VideoDTO;
import com.avatar.ava.presentation.main.MainScreenPostman;
import com.avatar.ava.presentation.main.fragments.RecyclerClickListener;
import com.avatar.ava.presentation.main.fragments.rating.RatingPresenter;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Toothpick;

import static com.avatar.ava.DataModule.SERVER_NAME;
import static com.avatar.ava.presentation.main.fragments.semifinalists.SemifinalistsAdapter.currCountVotes;


public class SemifinalistsFragment extends MvpAppCompatFragment implements SemifinalistsView {


    @Inject
    Context appContext;

    @InjectPresenter
    SemifinalistsPresenter presenter;

    @ProvidePresenter
    SemifinalistsPresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(SemifinalistsPresenter.class);
    }

    @BindView(R.id.fragment_semifinalists_time)
    TextView time;

    @BindView(R.id.fragment_semifinalists_list)
    RecyclerView recyclerView;

    @BindView(R.id.rating_item_video)
    PlayerView video;

    @BindView(R.id.rating_item_name)
    TextView name;

    @BindView(R.id.rating_item_description)
    TextView description;

    @BindView(R.id.rating_item_ava)
    ImageView ava;

    @BindView(R.id.rating_item_likes)
    TextView likes;

    SemifinalistsAdapter adapter;

    private SimpleExoPlayer player;
    private MediaSource mediaSource;
    private DataSource.Factory factory;

    ArrayList<BattleParticipant> data;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd : hh : mm");



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_semifinalists, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));

        adapter = new SemifinalistsAdapter(appContext, (v, position) -> {
            try {
                Log.d("SFLog", "clicked");
                Log.d("SALog", "clicked " + currCountVotes);
                //likes.setText(data.get(position). + "");
                presenter.getProfile(data.get(position).getId());
            } catch (Exception e) {
                e.printStackTrace();
            }}
            );

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);

        //presenter.getSemifinalists();
        presenter.getBattles();

        player = new SimpleExoPlayer.Builder(appContext).build();
        factory = new DefaultDataSourceFactory(appContext,
                Util.getUserAgent(appContext, "XCE FACTOR"));


        try {
            Log.d("SFLog", "here");
            Date date1 = simpleDateFormat.parse("21 : 14 : 10");
            Calendar calendar = Calendar.getInstance();
            Date date2 = simpleDateFormat.parse(calendar.get(Calendar.DAY_OF_MONTH) + " : " + calendar.get(Calendar.HOUR) + " : " + calendar.get(Calendar.MINUTE));
            printDifference(date2, date1);
        } catch (ParseException e) {
            Log.d("SFLog", "error");
            e.printStackTrace();
        }

    }

    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        Log.d("SFLOG","startDate : " + startDate);
        Log.d("SFLOG","endDate : "+ endDate);
        Log.d("SFLOG","different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        Log.d("SFLOG", elapsedDays + " " +  elapsedHours + " " + elapsedMinutes + " " + elapsedSeconds);
        time.setText(elapsedDays + " : " + elapsedHours + " : " + elapsedMinutes);
    }

    @Override
    public void setSemifinalists(ArrayList<BattleParticipant> data) {
        this.data = data;
        adapter.setItems(data);
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(appContext, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setDataProfile(PublicProfileDTO person) {
        if(person.getPhoto() != null)
            Glide.with(appContext)
                    .load(SERVER_NAME + "/api/profile/photo/get/" + person.getPhoto())
                    .circleCrop()
                    .into(ava);
        else
            Glide.with(appContext)
                    .load(R.drawable.empty_profile)
                    .centerCrop()
                    .into(ava);

        name.setText(person.getName());
        description.setText(person.getDescription());



        String videoName = "";
        for(VideoDTO video: person.getVideos()){
            if(video.isActive()) {
                videoName = video.getName();
                break;
            }
        }
        mediaSource = new ProgressiveMediaSource.Factory(factory)
                .createMediaSource(Uri.parse(SERVER_NAME + "/api/video/" + videoName));


        video.setPlayer(player);
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }

    @Override
    public void setBattles(ArrayList<BattleDTO> battles) {
        for(BattleDTO battleDTO: battles){
            Log.d("SFLog", battleDTO.getEndDate());
        }
        this.data = (ArrayList<BattleParticipant>) battles.get(0).getBattleParticipants();
        adapter.setItems(this.data);
    }

    @Override
    public void onStop() {
        super.onStop();
        player.stop();
    }


}