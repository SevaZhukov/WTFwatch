package com.mrswimmer.shiftwatch.domain.interactor;

import android.content.SharedPreferences;

public class SettingsService {
    SharedPreferences sharedPreferences;

    public SettingsService(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void setUserId(String id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", id);
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString("id", "error");
    }
}
