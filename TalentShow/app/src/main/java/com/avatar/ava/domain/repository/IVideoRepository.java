package com.avatar.ava.domain.repository;

import android.net.Uri;

import com.avatar.ava.domain.entities.PersonDTO;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IVideoRepository {
    Completable uploadVideo(Uri videoUri);
    Single<PersonDTO> getNewVideoLink();
    Single<ArrayList<PersonDTO>> getUnwatchedVideos(int number);
    Completable setLiked(boolean liked);
    Completable setInterval(String fileName, int startTime, int endTime);
    Single<PersonDTO> getVideoLinkOnCreate();
}
