package com.avatar.ava.domain.repository;

public interface ISharedPreferencesRepository {
    void exitAcc();
    boolean checkAuth();
    void saveName(String name);
    String getName();
}
