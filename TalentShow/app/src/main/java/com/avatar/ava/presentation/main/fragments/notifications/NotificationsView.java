package com.avatar.ava.presentation.main.fragments.notifications;

import com.arellomobile.mvp.MvpView;
import com.avatar.ava.domain.entities.NotificationsDTO;

public interface NotificationsView extends MvpView {

    void addLike(NotificationsDTO notificationsDTO);
    void showError(String error);
    void showNoNotifText();
}
