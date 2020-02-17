package com.example.talentshow.data;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.talentshow.domain.repository.ISharedPreferemcesRepository;

import javax.inject.Inject;

public class SharedPreferencesRepository implements ISharedPreferemcesRepository {

    private final static String token = "token";
    private final static String authed = "authed";

    SharedPreferences sharedPreferences;

    @Inject
    SharedPreferencesRepository(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    void saveToken(String personToken){
        Log.d("Token", personToken);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(token, personToken);
        editor.apply();
    }

    public void setAuthed(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(authed, true);
        editor.apply();
    }

    public boolean checkAuth(){
        return sharedPreferences.getBoolean(authed, false);
    }

    String getToken(){
        return sharedPreferences.getString(token, null);
    }
}
