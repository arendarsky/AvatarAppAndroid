package com.avatar.ava.presentation.main.fragments.rating;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avatar.ava.R;
import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.avatar.ava.DataModule.SERVER_NAME;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {

    private List<PersonRatingDTO> data = new ArrayList<>();

    @NonNull
    @Override
    public RatingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.star_rating_recycler_item_star, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PersonRatingDTO personRatingDTO = data.get(position);
        holder.video.setVideoURI(Uri.parse(SERVER_NAME + "/api/video/"
                + personRatingDTO.getVideo().getName()));
        holder.video.setOnPreparedListener(mp -> mp.setOnVideoSizeChangedListener(
                (mp12, width, height) -> {
                    mp.setLooping(true);
                }));

        holder.likes.setText("     " + String.valueOf(personRatingDTO.getLikesNumber()));

        holder.video.start();
        holder.description.setText(personRatingDTO.getDescription());
        String name = personRatingDTO.getName();
        holder.name.setText(name);
        holder.pos.setText(String.valueOf(position + 1) + " место");
        Glide.with(holder.itemView.getContext())
                .load(SERVER_NAME + "/api/profile/photo/get/"
                        + personRatingDTO.getPhoto())
                .circleCrop()
                .into(holder.ava);

    }

    @Override
    public int getItemCount() {
        return data.size();
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

        VideoView video;
        TextView pos, name, description, likes;
        ImageView ava;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            video = itemView.findViewById(R.id.rating_item_video);
            pos = itemView.findViewById(R.id.rating_item_pos);
            name = itemView.findViewById(R.id.rating_item_name);
            description = itemView.findViewById(R.id.rating_item_description);
            ava = itemView.findViewById(R.id.rating_item_ava);
            likes = itemView.findViewById(R.id.rating_item_likes);
        }
    }
}
