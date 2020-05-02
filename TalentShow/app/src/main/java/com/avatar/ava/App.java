package com.avatar.ava;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;

import toothpick.Scope;
import toothpick.Toothpick;

public class App extends Application {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Scope appScope = Toothpick.openScope(App.class);
        appScope.installModules(new PresentationModule(getApplicationContext(), mFirebaseAnalytics));
        appScope.installModules(new DataModule());
        appScope.installModules(new NavigationModule());
    }

}
