package com.chettapps.invitationmaker.photomaker.listener;

import com.chettapps.invitationmaker.photomaker.pojoClass.BackgroundImage;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public interface GetSnapListenerData {
    void onSnapFilter(int i, int i2, String str, String str2);

    void onSnapFilter(ArrayList<BackgroundImage> arrayList, int i, String str);
}
