package com.avatar.ava.presentation.main.fragments.notifications;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.domain.entities.NotificationsDTO;
import com.avatar.ava.presentation.main.MainScreenPostman;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Toothpick;

public class FragmentNotifications extends MvpAppCompatFragment implements NotificationsView {

    private Activity activity;

    @BindView(R.id.notifications_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.notification_no_notification_text)
    TextView noNotificationsText;

    @Inject
    Context appContext;

    @InjectPresenter
    NotificationsPresenter presenter;

    @ProvidePresenter
    NotificationsPresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(NotificationsPresenter.class);
    }

    private NotificationsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new NotificationsAdapter((v, position) -> {
            try {
                ((MainScreenPostman) activity).openPublicProfile(adapter.getPersonId(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        noNotificationsText.setVisibility(View.INVISIBLE);
        presenter.getLikes();
    }

    @Override
    public void addLike(List<NotificationsDTO> notificationsDTO) {
        noNotificationsText.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter.addItems(notificationsDTO);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(appContext, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoNotifText() {
        recyclerView.setVisibility(View.INVISIBLE);
        noNotificationsText.setVisibility(View.VISIBLE);
    }


}
