package com.example.talentshow.domain.repository;

import android.net.Uri;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IVideoRepository {
    Completable uploadVideo(Uri videoUri);
    String getNewVideoLink();
    Single<ArrayList<String>> getUnwatchedVideos(int number);
    Completable setLiked(boolean liked);
}
