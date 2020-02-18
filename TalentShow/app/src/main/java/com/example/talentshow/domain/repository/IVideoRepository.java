package com.example.talentshow.domain.repository;

import android.net.Uri;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IVideoRepository {
    Completable uploadVideo(Uri videoUri);

    Single<ArrayList<String>> getUnwatcedVideos(int number);
}
