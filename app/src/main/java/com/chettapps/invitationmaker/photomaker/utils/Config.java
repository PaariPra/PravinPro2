package com.chettapps.invitationmaker.photomaker.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;



public class Config {
    public static int interstitalAdStatus;
    public static SharedPreferences sharedPreferences;

    public static void SaveInt(String str, int i, Activity activity) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        sharedPreferences = defaultSharedPreferences;
        SharedPreferences.Editor edit = defaultSharedPreferences.edit();
        edit.putInt(str, i);
        edit.commit();
    }

    public static int loadIntForFlow(Activity activity) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        sharedPreferences = defaultSharedPreferences;
        return defaultSharedPreferences.getInt("flow", 1);
    }

//    public static int loadIntForAd(Activity activity) {
//        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
//        sharedPreferences = defaultSharedPreferences;
//        return defaultSharedPreferences.getInt(FirebaseAnalytics.Event.APP_OPEN, 0);
//    }

    public static void SaveBool(String str, Boolean bool, Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sharedPreferences = defaultSharedPreferences;
        SharedPreferences.Editor edit = defaultSharedPreferences.edit();
        edit.putBoolean(str, bool.booleanValue());
        edit.commit();
    }

    public static boolean loadBoolPurchase(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sharedPreferences = defaultSharedPreferences;
        return defaultSharedPreferences.getBoolean("in_app_purchase", false);
    }

    public static boolean loadBoolTempVideoAd(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sharedPreferences = defaultSharedPreferences;
        return defaultSharedPreferences.getBoolean("video_ad", false);
    }

    public static boolean loadBoolPosterVideoAd(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sharedPreferences = defaultSharedPreferences;
        return defaultSharedPreferences.getBoolean("video_ad_poster", false);
    }

    public static String loadString(Activity activity, String str) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        sharedPreferences = defaultSharedPreferences;
        return defaultSharedPreferences.getString(str, "");
    }

    public static String loadString(Context context, String str) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sharedPreferences = defaultSharedPreferences;
        return defaultSharedPreferences.getString(str, "");
    }

    public static boolean loadBoolRate(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sharedPreferences = defaultSharedPreferences;
        return defaultSharedPreferences.getBoolean("rate_us", false);
    }

    public static boolean loadBoolRateLater(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sharedPreferences = defaultSharedPreferences;
        return defaultSharedPreferences.getBoolean("flag_rate_later", false);
    }

    public static int LoadInt(Activity activity) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        sharedPreferences = defaultSharedPreferences;
        return defaultSharedPreferences.getInt("count", 0);
    }

    public static int LoadIntPo(Activity activity) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        sharedPreferences = defaultSharedPreferences;
        return defaultSharedPreferences.getInt("isAccepted", 0);
    }

    public static void SaveString(String str, String str2, Activity activity) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        sharedPreferences = defaultSharedPreferences;
        SharedPreferences.Editor edit = defaultSharedPreferences.edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static void SaveString(String str, String str2, Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sharedPreferences = defaultSharedPreferences;
        SharedPreferences.Editor edit = defaultSharedPreferences.edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static String loadDateString(Activity activity) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        sharedPreferences = defaultSharedPreferences;
        return defaultSharedPreferences.getString("date", "date");
    }

    public static String LoadFireBaseString(Activity activity) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        sharedPreferences = defaultSharedPreferences;
        return defaultSharedPreferences.getString("token", "abc");
    }

    public static boolean isNetworkAvailableContex(Context context) {
        NetworkInfo[] allNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (!(connectivityManager == null || (allNetworkInfo = connectivityManager.getAllNetworkInfo()) == null)) {
            for (NetworkInfo networkInfo : allNetworkInfo) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
