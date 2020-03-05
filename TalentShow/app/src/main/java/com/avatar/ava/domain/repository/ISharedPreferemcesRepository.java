package com.avatar.ava.domain.repository;

public interface ISharedPreferemcesRepository {
    void setAuthed();
    boolean checkAuth();
    void saveRole(String role);
    String getRole();
    void saveName(String name);
    String getName();
}
