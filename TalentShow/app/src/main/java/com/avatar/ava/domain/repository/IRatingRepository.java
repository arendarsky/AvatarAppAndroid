package com.avatar.ava.domain.repository;

import com.avatar.ava.domain.entities.PersonDTO;

import java.util.ArrayList;

import io.reactivex.Single;

public interface IRatingRepository {
    Single<ArrayList<PersonDTO>> getRating(int number);
}
