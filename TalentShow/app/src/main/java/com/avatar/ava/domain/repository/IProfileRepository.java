package com.avatar.ava.domain.repository;

import android.net.Uri;

import com.avatar.ava.domain.entities.PersonRatingDTO;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IProfileRepository {
    Single<PersonRatingDTO> getProfile();
    Completable setDescription(String description);
    Completable setName(String name);
    Completable setPassword(String oldPassword, String newPassword);
    Completable uploadPhoto(Uri photoUri);
}
