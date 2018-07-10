package com.mrswimmer.shiftwatch;

import android.app.Application;

import com.mrswimmer.shiftwatch.di.AppComponent;
import com.mrswimmer.shiftwatch.di.DaggerAppComponent;
import com.mrswimmer.shiftwatch.di.module.SharedPreferencesModule;

public class App extends Application {
    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .sharedPreferencesModule(new SharedPreferencesModule(getApplicationContext()))
                .build();
    }
}
