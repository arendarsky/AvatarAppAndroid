package com.avatar.ava.domain.repository;

import android.net.Uri;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IVideoRepository {
    Completable uploadVideo(Uri videoUri);
    String getNewVideoLink();
    Single<ArrayList<String>> getUnwatchedVideos(int number);
    Completable setLiked(boolean liked);
    Completable setInterval(String fileName, int startTime, int endTime);
    Single<ArrayList<String>> getVideoLinkOnCreate();
}
