package com.avatar.ava.presentation.main.fragments.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avatar.ava.R;
import com.avatar.ava.domain.entities.NotificationsDTO;
import com.avatar.ava.presentation.main.fragments.RecyclerClickListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.avatar.ava.DataModule.SERVER_NAME;

@SuppressWarnings("FieldCanBeLocal")
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private List<NotificationsDTO> data = new ArrayList<>();
    private final String likeText = " хочет видеть тебя в финале XCE FACTOR 2020";

    private RecyclerClickListener clickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.avatar.setOnClickListener(v1 ->
                clickListener.itemClicked(v1, viewHolder.getAdapterPosition()));
        return viewHolder;
    }

    NotificationsAdapter(RecyclerClickListener clickListener) {
        super();
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationsDTO notification = data.get(position);
        if (notification.getPhoto() != null){
            Glide.with(holder.itemView.getContext())
                    .load(SERVER_NAME + "/api/profile/photo/get/"
                            + notification.getPhoto())
                    .circleCrop()
                    .into(holder.avatar);
        }else{
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.empty_profile)
                    .centerCrop()
                    .into(holder.avatar);
        }

        holder.text.setText(String.format("%s%s", notification.getName(), this.likeText));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    void addItems(List<NotificationsDTO> newData){
        this.data.addAll(newData);
        notifyDataSetChanged();
    }

    int getPersonId(int index){
        return data.get(index).getId();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView text;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.notifications_item_avatar);
            text = itemView.findViewById(R.id.notifications_item_text);
        }
    }

    @SuppressWarnings("unused")
    public void addItem(NotificationsDTO notificationsDTO) {
        data.add(notificationsDTO);
        notifyDataSetChanged();
    }
}
