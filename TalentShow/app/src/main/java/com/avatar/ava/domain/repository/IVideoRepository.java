package com.avatar.ava.domain.repository;

import android.net.Uri;

import com.avatar.ava.domain.entities.PersonDTO;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface IVideoRepository {
    Single<PersonDTO> getNewVideoLink();
    Completable setLiked(boolean liked);
    Completable uploadAndSetInterval(Uri fileUri, Float beginTime, Float endTime);
    Completable setInterval(String fileName, Float beginTime, Float endTime);
    Single<PersonDTO> getVideoLinkOnCreate();
    Completable setActive(String fileName);
    Uri getLoadingVideo();
    Observable<Float> downloadVideo(String videoToDownload, Uri fileToWrite);
    void clearVideoList();
}
