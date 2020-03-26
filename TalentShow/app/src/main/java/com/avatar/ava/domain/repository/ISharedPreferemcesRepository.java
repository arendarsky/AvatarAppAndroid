package com.avatar.ava.domain.repository;

public interface ISharedPreferemcesRepository {
    void setAuthed();
    void exitAcc();
    boolean checkAuth();
    void saveRole(String role);
    String getRole();
    void saveName(String name);
    String getName();
}
