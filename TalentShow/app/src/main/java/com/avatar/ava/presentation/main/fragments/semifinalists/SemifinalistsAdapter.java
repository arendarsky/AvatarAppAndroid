package com.avatar.ava.presentation.main.fragments.semifinalists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.avatar.ava.R;
import com.avatar.ava.domain.entities.ProfileSemifinalistsDTO;
import com.avatar.ava.presentation.main.fragments.RecyclerClickListener;
import com.avatar.ava.presentation.main.fragments.rating.RatingSemifinalistsAdapter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.avatar.ava.DataModule.SERVER_NAME;

public class SemifinalistsAdapter extends RecyclerView.Adapter<SemifinalistsAdapter.SemifinalistsViewHolder> {

    Context context;
    ArrayList<ProfileSemifinalistsDTO> data = new ArrayList<>();
    private RecyclerClickListener clickListener;

    public SemifinalistsAdapter(Context context, RecyclerClickListener clickListener) {
        super();
        this.context = context;
        this.clickListener = clickListener;
    }



    @NonNull
    @Override
    public SemifinalistsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_recycler_semifinalists_item, parent, false);
        SemifinalistsViewHolder viewHolder = new SemifinalistsViewHolder(view);
        viewHolder.layout.setOnClickListener(v1 -> {
                    clickListener.itemClicked(v1, viewHolder.getAdapterPosition());
                    if(!viewHolder.isChoose()){
                        viewHolder.bg.setVisibility(View.VISIBLE);
                        viewHolder.setChoose(true);
                    }else{
                        viewHolder.bg.setVisibility(View.INVISIBLE);
                        viewHolder.setChoose(false);
                    }
                });
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull SemifinalistsViewHolder holder, int position) {
        ProfileSemifinalistsDTO profileSemifinalistsDTO = data.get(position);

        holder.userName.setText(profileSemifinalistsDTO.getName());
        holder.userLikes.setText("" + profileSemifinalistsDTO.getLikes());
        if(!holder.isChoose())
            holder.bg.setVisibility(View.INVISIBLE);

        /*holder.userAva.setOnClickListener(v -> {
            if(!holder.isChoose()){
                holder.bg.setVisibility(View.VISIBLE);
                holder.setChoose(true);
            }else{
                holder.bg.setVisibility(View.INVISIBLE);
                holder.setChoose(false);
            }
        });*/


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
        if(data != null)
            return data.size();
        return 0;
    }

    public void setItems(ArrayList<ProfileSemifinalistsDTO> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public class SemifinalistsViewHolder extends RecyclerView.ViewHolder{
        ImageView userAva;
        TextView userName;
        TextView userLikes;
        ImageView bg;
        ConstraintLayout layout;
        private boolean choose = false;


        public SemifinalistsViewHolder(@NonNull View itemView) {
            super(itemView);
            userAva = (ImageView) itemView.findViewById(R.id.rating_recycler_semifinalists_ava_item);
            userName = (TextView) itemView.findViewById(R.id.rating_recycler_semifinalists_name_item);
            userLikes = (TextView) itemView.findViewById(R.id.rating_recycler_semifinalists_likes_item);
            bg = (ImageView) itemView.findViewById(R.id.rating_recycler_semifinalists_ava_item_bg);
            layout = (ConstraintLayout) itemView.findViewById(R.id.rating_recycler_semifinalists_layout);
        }

        public boolean isChoose() {
            return choose;
        }

        public void setChoose(boolean choose) {
            this.choose = choose;
        }
    }
}
