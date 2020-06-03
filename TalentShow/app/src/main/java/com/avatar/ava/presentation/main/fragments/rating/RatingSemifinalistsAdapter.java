package com.avatar.ava.presentation.main.fragments.rating;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avatar.ava.BuildConfig;
import com.avatar.ava.R;
import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.avatar.ava.domain.entities.ProfileSemifinalistsDTO;
import com.avatar.ava.presentation.main.fragments.RecyclerClickListener;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import static com.avatar.ava.DataModule.SERVER_NAME;

public class RatingSemifinalistsAdapter extends RecyclerView.Adapter<RatingSemifinalistsAdapter.SemiViewHolder> {

    private List<ProfileSemifinalistsDTO> data = new ArrayList<>();
    private RecyclerClickListener clickListener;

    RatingSemifinalistsAdapter(Context context, RecyclerClickListener clickListener) {
        super();
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SemiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_recycler_semifinalists_item, parent, false);
        SemiViewHolder viewHolder = new SemiViewHolder(v);
        viewHolder.userAva.setOnClickListener(v1 ->
                clickListener.itemClicked(v1, viewHolder.getAdapterPosition()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SemiViewHolder holder, int position) {
        ProfileSemifinalistsDTO profileSemifinalistsDTO = data.get(position);

        holder.userName.setText(profileSemifinalistsDTO.getName());

        if(profileSemifinalistsDTO.getPhoto() != null)
        Glide.with(holder.itemView.getContext())
                .load(SERVER_NAME + "/api/profile/photo/get/" + profileSemifinalistsDTO.getPhoto())
                .circleCrop()
                .into(holder.userAva);
        else
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.empty_profile)
                    .centerCrop()
                    .into(holder.userAva);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    int getPersonId(int index){
        return data.get(index).getId();
    }

    void setItems(List<ProfileSemifinalistsDTO> newData){
        if (!newData.isEmpty())
        {
            data.clear();
            data.addAll(newData);

        }
        notifyDataSetChanged();
    }

    static class SemiViewHolder extends RecyclerView.ViewHolder{
        ImageView userAva;
        TextView userName;

        public SemiViewHolder(@NonNull View itemView) {
            super(itemView);
            userAva = (ImageView) itemView.findViewById(R.id.rating_recycler_semifinalists_ava_item);
            userName = (TextView) itemView.findViewById(R.id.rating_recycler_semifinalists_name_item);
        }
    }
}
