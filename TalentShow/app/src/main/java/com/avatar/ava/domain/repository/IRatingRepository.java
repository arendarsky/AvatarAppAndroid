package com.avatar.ava.domain.repository;

import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.avatar.ava.domain.entities.ProfileSemifinalistsDTO;

import java.util.ArrayList;

import io.reactivex.Single;

public interface IRatingRepository {
    Single<ArrayList<PersonRatingDTO>> getRating(int number);
    Single<ArrayList<ProfileSemifinalistsDTO>> getSemifinalists();
}
