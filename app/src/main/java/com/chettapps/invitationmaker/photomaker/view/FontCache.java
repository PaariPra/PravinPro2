package com.chettapps.invitationmaker.photomaker.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.HashMap;

/* loaded from: classes2.dex */
public class FontCache {
    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(String str, Context context) {
        Typeface typeface = fontCache.get(str);
        if (typeface != null) {
            return typeface;
        }
        try {
            AssetManager assets = context.getAssets();
            Typeface createFromAsset = Typeface.createFromAsset(assets, "font/" + str);
            fontCache.put(str, createFromAsset);
            return createFromAsset;
        } catch (Exception unused) {
            return null;
        }
    }
}
