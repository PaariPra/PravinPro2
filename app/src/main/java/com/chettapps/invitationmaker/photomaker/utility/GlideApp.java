package com.chettapps.invitationmaker.photomaker.utility;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestManager;

import java.io.File;

/* loaded from: classes2.dex */
public final class GlideApp {
    private GlideApp() {
    }

    public static File getPhotoCacheDir(Context context) {
        return Glide.getPhotoCacheDir(context);
    }

    public static File getPhotoCacheDir(Context context, String str) {
        return Glide.getPhotoCacheDir(context, str);
    }

    public static Glide get(Context context) {
        return Glide.get(context);
    }

    @Deprecated
    public static void init(Glide glide) {
        Glide.init(glide);
    }

    public static void init(Context context, GlideBuilder glideBuilder) {
        Glide.init(context, glideBuilder);
    }

    public static void enableHardwareBitmaps() {
        Glide.enableHardwareBitmaps();
    }

    public static void tearDown() {
        Glide.tearDown();
    }

    public static RequestManager with(Context context) {
        return Glide.with(context);
    }

    public static RequestManager with(Activity activity) {
        return Glide.with(activity);
    }

    public static RequestManager with(FragmentActivity fragmentActivity) {
        return Glide.with(fragmentActivity);
    }

    public static RequestManager with(Fragment fragment) {
        return Glide.with(fragment);
    }

    @Deprecated
    public static RequestManager with(android.app.Fragment fragment) {
        return Glide.with(fragment);
    }

    public static RequestManager with(View view) {
        return (GlideRequests) Glide.with(view);
    }
}
