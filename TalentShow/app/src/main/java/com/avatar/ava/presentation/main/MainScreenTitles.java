package com.avatar.ava.presentation.main;

import androidx.annotation.NonNull;

public enum MainScreenTitles {
    NEW_VIDEO("Новое видео"),
    CASTING("Кастинг"),
    RATING("Рейтинг"),
    PROFILE("Профиль"),
    PUBLIC_PROFILE("Профиль"),
    NOTIFICATIONS("Уведомления"),
    BEST30("Лучшие 30"),
    SETTINGS("Настройки"),
    CHANGE_PASSWORD("Изм. пароля"),
    CHANGE_PROFILE("Ред. профиля"),
    SEMIFINALISTS("Полуфиналисты");


    MainScreenTitles(String title) {
        this.title = title;
    }

    private String title;

    @NonNull
    @Override
    public String toString() {
        return title;
    }
}
