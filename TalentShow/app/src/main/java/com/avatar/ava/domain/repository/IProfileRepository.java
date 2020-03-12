package com.avatar.ava.domain.repository;

import com.avatar.ava.domain.entities.PersonRatingDTO;

import io.reactivex.Single;

public interface IProfileRepository {
    Single<PersonRatingDTO> getProfile();
}
