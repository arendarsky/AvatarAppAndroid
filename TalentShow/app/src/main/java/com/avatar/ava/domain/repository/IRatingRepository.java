package com.avatar.ava.domain.repository;

import java.util.ArrayList;

import io.reactivex.Single;

public interface IRatingRepository {
    Single<ArrayList<String>> getRating(int number);
}
