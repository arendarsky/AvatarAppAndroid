package com.avatar.ava.domain.repository;

import com.avatar.ava.domain.entities.NotificationsDTO;
import com.avatar.ava.domain.entities.PersonDTO;
import com.avatar.ava.domain.entities.PersonRatingDTO;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface IRatingRepository {
    Single<ArrayList<PersonRatingDTO>> getRating(int number);
    Observable<NotificationsDTO> getLikes(int number);
}
