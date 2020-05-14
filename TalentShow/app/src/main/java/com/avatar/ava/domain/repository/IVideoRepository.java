package com.avatar.ava.domain.repository;

import android.net.Uri;

import com.avatar.ava.domain.entities.PersonDTO;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IVideoRepository {
    Single<PersonDTO> getNewVideoLink();
    Completable setLiked(boolean liked);
    Completable uploadAndSetInterval(Uri fileUri, Float beginTime, Float endTime);
    Single<PersonDTO> getVideoLinkOnCreate();
    Completable setActive(String fileName);
    Uri getLoadingVideo();

    void clearVideoList();
}
