package com.avatar.ava.domain.repository;

import android.net.Uri;

import com.avatar.ava.domain.entities.NotificationsDTO;
import com.avatar.ava.domain.entities.ProfileDTO;
import com.avatar.ava.domain.entities.PublicProfileDTO;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IProfileRepository {
    Single<ProfileDTO> getProfile();
    Completable setDescription(String description);
    Completable setName(String name);
    Single<Boolean> setPassword(String oldPassword, String newPassword);
    Completable uploadPhoto(Uri photoUri);
    Single<List<NotificationsDTO>> getNotifications(int number, int skipNumber);
    Completable removeVideo(String name);
    Single<PublicProfileDTO> getPublicProfile(int id);
    Completable updateProfile(String name, String description, String instagramLogin);
}
