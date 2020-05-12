package com.avatar.ava;

import android.app.Application;

import com.amplitude.api.Amplitude;
import com.google.firebase.analytics.FirebaseAnalytics;

import toothpick.Scope;
import toothpick.Toothpick;

public class App extends Application {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Amplitude.getInstance().initialize(
                getApplicationContext(),
                "95e3f78b9110135a6302acccfccd4a3b")
                .enableForegroundTracking(this);
        Scope appScope = Toothpick.openScope(App.class);
        appScope.installModules(new PresentationModule(getApplicationContext(), mFirebaseAnalytics));
        appScope.installModules(new DataModule());
        appScope.installModules(new NavigationModule());
    }

}
