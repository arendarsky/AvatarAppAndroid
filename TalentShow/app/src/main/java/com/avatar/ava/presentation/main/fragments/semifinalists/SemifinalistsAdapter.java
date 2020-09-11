package com.avatar.ava.presentation.main.fragments.semifinalists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avatar.ava.R;
import com.avatar.ava.domain.entities.ProfileSemifinalistsDTO;
import com.avatar.ava.presentation.main.fragments.rating.RatingSemifinalistsAdapter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.avatar.ava.DataModule.SERVER_NAME;

public class SemifinalistsAdapter extends RecyclerView.Adapter<SemifinalistsAdapter.SemifinalistsViewHolder> {

    Context context;
    ArrayList<ProfileSemifinalistsDTO> data;

    public SemifinalistsAdapter(Context context) {
        super();
        this.context = context;
    }

    @NonNull
    @Override
    public SemifinalistsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_recycler_semifinalists_item, parent, false);
        SemifinalistsViewHolder viewHolder = new SemifinalistsViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SemifinalistsViewHolder holder, int position) {
        ProfileSemifinalistsDTO profileSemifinalistsDTO = data.get(position);

        holder.userName.setText(profileSemifinalistsDTO.getName());
        holder.userLikes.setText("" + profileSemifinalistsDTO.getLikes());
        holder.bg.setVisibility(View.INVISIBLE);


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

    public void setItems(ArrayList<ProfileSemifinalistsDTO> data){
        this.data = data;
    }

    public class SemifinalistsViewHolder extends RecyclerView.ViewHolder{
        ImageView userAva;
        TextView userName;
        TextView userLikes;
        ImageView bg;


        public SemifinalistsViewHolder(@NonNull View itemView) {
            super(itemView);
            userAva = (ImageView) itemView.findViewById(R.id.rating_recycler_semifinalists_ava_item);
            userName = (TextView) itemView.findViewById(R.id.rating_recycler_semifinalists_name_item);
            userLikes = (TextView) itemView.findViewById(R.id.rating_recycler_semifinalists_likes_item);
            bg = (ImageView) itemView.findViewById(R.id.rating_recycler_semifinalists_ava_item_bg);
        }
    }
}
