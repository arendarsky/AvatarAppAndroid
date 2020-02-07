package com.example.talentshow.presentation.star.rating.viewpager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.talentshow.R;
import com.example.talentshow.domain.entities.PersonDTO;

import java.util.ArrayList;
import java.util.List;

public class StarRatingAdapter extends RecyclerView.Adapter<StarRatingAdapter.ViewHolder> {

    private List<PersonDTO> data = new ArrayList<>();

    @NonNull
    @Override
    public StarRatingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.star_rating_recycler_item_star, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PersonDTO personDTO = data.get(position);
        String name = personDTO.getName() + " " + personDTO.getSurname();
        holder.personName.setText(name);
        holder.personNumber.setText(position + 1);
//        Glide.with(holder.itemView.getContext())
//                .load(personDTO.getPhoto())
//                .circleCrop()
//                .into(holder.personAvatar);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setItems(List<PersonDTO> newData){
        data.addAll(newData);
        notifyDataSetChanged();
    }

    public void addItem(PersonDTO p){
        data.add(p);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView personNumber;
        ImageView personAvatar;
        TextView personName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            personNumber = itemView.findViewById(R.id.star_rating_recycler_item_number);
            personAvatar = itemView.findViewById(R.id.star_rating_recycler_item_avatar);
            personName = itemView.findViewById(R.id.star_rating_recycler_item_name);
        }
    }
}
