package com.chettapps.invitationmaker.photomaker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/* loaded from: classes2.dex */
public class PreferenceClass {
    private String DEFAULT_APP_IMAGEDATA_DIRECTORY;
    private String lastImagePath = "";
    private SharedPreferences preferences;

    public PreferenceClass(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SharedPreferences getPrefernce() {
        SharedPreferences sharedPreferences = this.preferences;
        if (sharedPreferences != null) {
            return sharedPreferences;
        }
        return null;
    }

    public int getInt(String str, int i) {
        return this.preferences.getInt(str, i);
    }

    public String getString(String str) {
        return this.preferences.getString(str, "");
    }

    public boolean getBoolean(String str, boolean z) {
        return this.preferences.getBoolean(str, z);
    }

    public void putInt(String str, int i) {
        this.preferences.edit().putInt(str, i).apply();
    }

    public void putString(String str, String str2) {
        this.preferences.edit().putString(str, str2).apply();
    }

    public void remove(String str) {
        this.preferences.edit().remove(str).apply();
    }
}
