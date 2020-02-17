package com.example.talentshow.domain.repository;

import android.net.Uri;

import io.reactivex.Completable;

public interface IVideoRepository {
    Completable uploadVideo(Uri videoUri);
}
