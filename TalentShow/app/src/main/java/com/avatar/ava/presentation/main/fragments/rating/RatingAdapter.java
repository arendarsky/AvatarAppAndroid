package com.avatar.ava.presentation.main.fragments.rating;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avatar.ava.R;
import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.avatar.ava.presentation.main.fragments.RecyclerClickListener;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
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


    public RatingAdapter(RecyclerClickListener clickListener) {
        super();
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PersonRatingDTO personRatingDTO = data.get(position);

        if(personRatingDTO.getVideo() != null){
            holder.player = new SimpleExoPlayer.Builder(holder.itemView.getContext()).build();
            holder.video.setPlayer(holder.player);
            holder.setVideoName(personRatingDTO.getVideo().getName());


            //holder.setVideoSource();
            // Prepare the player with the source.
            //holder.player.prepare(holder.videoSource);

            //holder.player.setPlayWhenReady(true);
        }


        holder.likes.setText("     " + String.valueOf(personRatingDTO.getLikesNumber()));
        holder.description.setText(personRatingDTO.getDescription());
        String name = personRatingDTO.getName();
        holder.name.setText(name);
        holder.pos.setText(String.valueOf(position + 1) + " место");
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


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        Log.d("RatingAdapterLog", "viewRecycled " + holder.name.getText().toString());
        //holder.player.release();
        //holder.player.stop();

    }

    int getPersonId(int index){
        return data.get(index).getId();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        Log.d("RatingAdapterLog", "viewAttach " + holder.name.getText().toString() + " " + holder.player.getPlaybackState());
        if(holder.player != null){
            //Log.d("RatingAdapterLog", "viewAttach " + holder.name.getText().toString() + " " + holder.videoSource.toString() + " " + holder.videoName);
           // holder.player.setPlayWhenReady(true);
            //holder.player.retry();
            holder.player.seekTo(0);
            holder.setVideoSource();
            holder.player.prepare(holder.videoSource);
            //holder.player.prepare(holder.videoSource);
        }

    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Log.d("RatingAdapterLog", "viewDetached " + holder.name.getText().toString());
        if(holder.player != null){
            Log.d("RatingAdapterLog", "viewDetached " + holder.name.getText().toString());
            holder.player.stop(true);
            //holder.player.retry();
            //holder.player.release();
        }


    }

    public void setItems(List<PersonRatingDTO> newData){
        data.addAll(newData);
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
        SimpleExoPlayer player;
        DataSource.Factory dataSourceFactory;
        MediaSource videoSource;
        String videoName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            video = new PlayerView(itemView.getContext());
            video = itemView.findViewById(R.id.rating_item_video);
            pos = itemView.findViewById(R.id.rating_item_pos);
            name = itemView.findViewById(R.id.rating_item_name);
            description = itemView.findViewById(R.id.rating_item_description);
            ava = itemView.findViewById(R.id.rating_item_ava);
            likes = itemView.findViewById(R.id.rating_item_likes);

            DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter.Builder(itemView.getContext()).build();

            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            // Produces DataSource instances through which media data is loaded.
            dataSourceFactory = new DefaultDataSourceFactory(itemView.getContext(),
                    Util.getUserAgent(itemView.getContext(), "XCE FACTOR"), defaultBandwidthMeter);




        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public void setVideoSource() {
            this.videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(SERVER_NAME + "/api/video/" + videoName));
        }
    }
}
