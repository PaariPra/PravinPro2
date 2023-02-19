package com.chettapps.invitationmaker.photomaker.main;

import android.os.Build;
import android.view.View;

import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class ViewIdGenerator {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int generateViewId() {
        AtomicInteger atomicInteger;
        int i;
        int i2;
        if (Build.VERSION.SDK_INT >= 17) {
            return View.generateViewId();
        }
        do {
            atomicInteger = sNextGeneratedId;
            i = atomicInteger.get();
            i2 = i + 1;
            if (i2 > 16777215) {
                i2 = 1;
            }
        } while (!atomicInteger.compareAndSet(i, i2));
        return i;
    }
}
