package com.avatar.ava.data;

import android.content.SharedPreferences;
import android.util.Log;

import com.avatar.ava.domain.repository.ISharedPreferemcesRepository;

import javax.inject.Inject;

public class SharedPreferencesRepository implements ISharedPreferemcesRepository {

    private final static String token = "token";
    private final static String authed = "authed";
    private final static String role = "role";
    private final static String name = "name";


    SharedPreferences sharedPreferences;

    @Inject
    SharedPreferencesRepository(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    void saveToken(String personToken){
        Log.d("Token", personToken);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(token, "Bearer " + personToken);
        editor.putBoolean(authed, true);

        editor.apply();
    }

    public void setAuthed(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(authed, true);
        editor.apply();
    }

    public void exitAcc(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(authed, false);
        editor.apply();
    }

    public boolean checkAuth(){
        return sharedPreferences.getBoolean(authed, false);
    }

    @Override
    public void saveRole(String personRole) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(role, personRole);
        editor.apply();
    }

    @Override
    public String getRole() {
        return sharedPreferences.getString(role, null);
    }

    @Override
    public String getName() {
        return sharedPreferences.getString(name, null);
    }

    @Override
    public void saveName(String personName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, personName);
        editor.apply();

    }

    String getToken(){
        return sharedPreferences.getString(token, null);
    }
}
