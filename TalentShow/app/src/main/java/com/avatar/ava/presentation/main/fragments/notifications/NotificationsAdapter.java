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
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private List<NotificationsDTO> data = new ArrayList<>();
    private final String likeText = " хочет видеть тебя в финале XCE FACTOR 2020";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationsDTO notification = data.get(position);
        Glide.with(holder.itemView.getContext())
                .load("http://avatarapp.yambr.ru/api/profile/photo/get/"
                        + notification.getUser().getPhoto())
                .circleCrop()
                .into(holder.avatar);
        holder.text.setText(notification.getUser().getName() + this.likeText);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItems(List<NotificationsDTO> newData){
        this.data.addAll(newData);
        notifyDataSetChanged();
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

    public void addItem(NotificationsDTO notificationsDTO) {
        data.add(notificationsDTO);
        notifyDataSetChanged();
    }
}
