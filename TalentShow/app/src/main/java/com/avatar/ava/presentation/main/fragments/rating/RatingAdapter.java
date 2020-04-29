package com.avatar.ava.presentation.main.fragments.rating;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avatar.ava.R;
import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.avatar.ava.presentation.main.fragments.RecyclerClickListener;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import static com.avatar.ava.DataModule.SERVER_NAME;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {

    private List<PersonRatingDTO> data = new ArrayList<>();
    private RecyclerClickListener clickListener;
    private SimpleExoPlayer player;
    private ArrayList<MediaSource> mediaSources = new ArrayList<MediaSource>();
    private DataSource.Factory factory;

    @NonNull
    @Override
    public RatingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_recycler_item_star, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.ava.setOnClickListener(v1 ->
                clickListener.itemClicked(v1, viewHolder.getAdapterPosition()));
        return viewHolder;
    }


    RatingAdapter(Context context, RecyclerClickListener clickListener) {
        super();
        this.clickListener = clickListener;
        player = new SimpleExoPlayer.Builder(context).build();
        player.addListener(new Player.EventListener() {

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.d("RatingPlayer", "changeState" + playbackState);
                clickable = true;
                if(playbackState == Player.STATE_BUFFERING){

                }
            }
        });
        factory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "XCE FACTOR"));
    }

    private boolean clickable = true;
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PersonRatingDTO personRatingDTO = data.get(position);
        holder.start.setVisibility(View.VISIBLE);
        if(personRatingDTO.getVideo() != null){

            player.addAnalyticsListener(new AnalyticsListener() {
                @Override
                public void onPlayerStateChanged(EventTime eventTime, boolean playWhenReady, int playbackState) {
                    if(playWhenReady && playbackState == Player.STATE_ENDED){
                        if(holder.video.getPlayer() == null){
                            holder.start.setVisibility(View.VISIBLE);
                            holder.image.setVisibility(View.VISIBLE);
                            holder.restartButton.setVisibility(View.VISIBLE);
                        }
                    }
                    else if (playWhenReady && playbackState == Player.STATE_READY){
                        holder.progressBar.setVisibility(View.INVISIBLE);
                        holder.start.setVisibility(View.GONE);
                        holder.image.setVisibility(View.INVISIBLE);

                    }
                    else {
                        if(holder.video.getPlayer() == null){
                            holder.image.setVisibility(View.VISIBLE);
                            holder.start.setVisibility(View.VISIBLE);
                            holder.progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                }});


            holder.start.setOnClickListener(v -> {
                if(clickable){
                    clickable = false;
                    holder.video.setPlayer(null);
                    holder.video.setPlayer(player);

                    /*holder.setVideoName(personRatingDTO.getVideo().getName());
                    holder.setVideoSource();
                    player.prepare(holder.videoSource);*/

                    player.prepare(mediaSources.get(position));

                    player.setPlayWhenReady(true);
                    holder.start.setVisibility(View.GONE);
                    holder.image.setVisibility(View.INVISIBLE);
                }


            });


        }

        holder.restartButton.setOnClickListener(v -> {
            holder.restartButton.setVisibility(View.INVISIBLE);
            player.seekTo((long) personRatingDTO.getVideo().getStartTime());
            player.setPlayWhenReady(true);
        });

        holder.likes.setText("" + personRatingDTO.getLikesNumber());
        holder.description.setText(personRatingDTO.getDescription());
        String name = personRatingDTO.getName();
        holder.name.setText(name);
        holder.pos.setText("#" + (position + 1));
        if(personRatingDTO.getPhoto() == null){
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.empty_profile_icon)
                    .circleCrop()
                    .into(holder.ava);
        }else{
            Glide.with(holder.itemView.getContext())
                    .load(SERVER_NAME + "/api/profile/photo/get/"
                            + personRatingDTO.getPhoto())
                    .circleCrop()
                    .into(holder.ava);
        }

        Log.d("RatingAdapterLog", "onBindViewHolder " + holder.name.getText().toString());

        Glide.with(holder.itemView.getContext())
                .load(SERVER_NAME + "/api/video/" + personRatingDTO.getVideo().getName())
                .into(holder.image);


        //player.seekTo(0);

        //holder.setVideoSource();
        //player.prepare(holder.videoSource);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        Log.d("RatingAdapterLog", "viewRecycled " + holder.name.getText().toString());
    }

    int getPersonId(int index){
        return data.get(index).getId();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        Log.d("RatingAdapterLog", "viewAttach " + holder.name.getText().toString() + " " + player.getPlaybackState());

    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Log.d("RatingAdapterLog", "viewDetached " + holder.name.getText().toString());
        if(player != null){
            Log.d("RatingAdapterLog", "viewDetached " + holder.name.getText().toString());
            //player.stop(true);
            //player.release();

        }


    }

    void setItems(List<PersonRatingDTO> newData){
        data.addAll(newData);
        for(PersonRatingDTO person : data){
            String videoName = person.getVideo().getName();
            mediaSources.add(new ProgressiveMediaSource.Factory(factory)
                    .createMediaSource(Uri.parse(SERVER_NAME + "/api/video/" + videoName)));
        }
        notifyDataSetChanged();
    }

    public void addItem(PersonRatingDTO p){
        data.add(p);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        PlayerView video;
        TextView pos, name, description, likes;
        ImageView ava;

        DataSource.Factory dataSourceFactory;
        MediaSource videoSource;
        String videoName;
        ProgressBar progressBar;
        View restartButton;
        ImageButton start;
        ImageView image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            video = new PlayerView(itemView.getContext());
            video = itemView.findViewById(R.id.rating_item_video);
            //video.setUseArtwork(true);
            //video.setDefaultArtwork(itemView.getContext().getResources().getDrawable(R.drawable.logo));
            image = itemView.findViewById(R.id.rating_item_image);

            pos = itemView.findViewById(R.id.rating_item_pos);
            name = itemView.findViewById(R.id.rating_item_name);
            description = itemView.findViewById(R.id.rating_item_description);
            ava = itemView.findViewById(R.id.rating_item_ava);
            likes = itemView.findViewById(R.id.rating_item_likes);
            progressBar = itemView.findViewById(R.id.rating_item_progressbar);
            restartButton = itemView.findViewById(R.id.rating_item_restart);

            start = itemView.findViewById(R.id.rating_item_start);

            /*DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter.Builder(itemView.getContext()).build();

            // Produces DataSource instances through which media data is loaded.
            dataSourceFactory = new DefaultDataSourceFactory(itemView.getContext(),
                    Util.getUserAgent(itemView.getContext(), "XCE FACTOR"), defaultBandwidthMeter);*/




        }

        void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        void setVideoSource() {
            this.videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(SERVER_NAME + "/api/video/" + videoName));
        }
    }

    void stopPlayer(){
        if (player.isPlaying())
            player.stop();

    }
}
