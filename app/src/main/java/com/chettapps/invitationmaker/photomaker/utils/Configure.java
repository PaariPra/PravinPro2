package com.chettapps.invitationmaker.photomaker.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/* loaded from: classes2.dex */
public class Configure {
    public static File GetFileDir(Context context) {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        }
        return context.getFilesDir();
    }

    public static File GetTemplate(Context context) {
        File GetFileDir = GetFileDir(context);
        return new File(GetFileDir.getPath() + File.separator + ".InviResorces");
    }
}
