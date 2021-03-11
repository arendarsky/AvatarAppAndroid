package com.avatar.ava.di;


import android.content.Context;
import android.content.SharedPreferences;


import com.google.firebase.analytics.FirebaseAnalytics;

import toothpick.config.Module;

import static android.content.Context.MODE_PRIVATE;

public class PresentationModule extends Module {

    public PresentationModule(Context appContext, FirebaseAnalytics mFirebaseAnalytics){

        bind(FirebaseAnalytics.class).toInstance(mFirebaseAnalytics);

        bind(Context.class).toInstance(appContext);
        SharedPreferences sharedPreferences = appContext
                .getSharedPreferences("com.example.talentshow.prefs", MODE_PRIVATE);
        bind(SharedPreferences.class).toInstance(sharedPreferences);
    }


}
