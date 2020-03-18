package com.avatar.ava.presentation.main.fragments.notifications;

import com.arellomobile.mvp.MvpView;
import com.avatar.ava.domain.entities.NotificationsDTO;

import java.util.List;

public interface NotificationsView extends MvpView {

    void addLike(List<NotificationsDTO> notificationsDTO);
    void showError(String error);
    void showNoNotifText();
}
