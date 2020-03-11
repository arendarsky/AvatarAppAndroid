package com.avatar.ava.presentation.main.fragments.rating;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avatar.ava.domain.entities.PersonDTO;
import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.bumptech.glide.Glide;
import com.avatar.ava.R;



import java.util.ArrayList;
import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {

    private List<PersonRatingDTO> data = new ArrayList<>();

    @NonNull
    @Override
    public RatingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.star_rating_recycler_item_star, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PersonRatingDTO personRatingDTO = data.get(position);
        String name = personRatingDTO.getPersonDTO().getName();
        holder.name.setText(name);
        holder.pos.setText(String.valueOf(position + 1) + " место");
        Glide.with(holder.itemView.getContext())
                .load(personRatingDTO.getPersonDTO().getPhoto())
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
        TextView pos, name, description;
        ImageView ava;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            video = itemView.findViewById(R.id.rating_item_video);
            pos = itemView.findViewById(R.id.rating_item_pos);
            name = itemView.findViewById(R.id.rating_item_name);
            description = itemView.findViewById(R.id.rating_item_description);
            ava = itemView.findViewById(R.id.rating_item_ava);
        }
    }
}
