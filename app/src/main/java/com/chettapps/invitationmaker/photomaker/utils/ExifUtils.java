package com.chettapps.invitationmaker.photomaker.utils;

import android.media.ExifInterface;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public class ExifUtils {
    private ExifUtils() {
    }

    public static int getExifRotation(String str) {
        try {
            String attribute = new ExifInterface(str).getAttribute(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION);
            if (TextUtils.isEmpty(attribute)) {
                return 0;
            }
            int parseInt = Integer.parseInt(attribute);
            if (parseInt == 3) {
                return 180;
            }
            if (parseInt == 6) {
                return 90;
            }
            return parseInt != 8 ? 0 : 270;
        } catch (Exception unused) {
            return 0;
        }
    }
}
