package com.avatar.ava;


import android.content.Context;
import android.content.SharedPreferences;


import toothpick.config.Module;

import static android.content.Context.MODE_PRIVATE;

class PresentationModule extends Module {

    PresentationModule(Context appContext){
        bind(Context.class).toInstance(appContext);
        SharedPreferences sharedPreferences = appContext
                .getSharedPreferences("com.example.talentshow.prefs", MODE_PRIVATE);
        bind(SharedPreferences.class).toInstance(sharedPreferences);
    }


}
