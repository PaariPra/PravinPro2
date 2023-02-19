package com.chettapps.invitationmaker.photomaker.main;

import android.app.Activity;

import com.chettapps.invitationmaker.photomaker.pojoClass.BackgroundImage;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public interface OnClickCallback<T, P, A, V, Q> {
    void onClickCallBack(ArrayList<String> arrayList, ArrayList<BackgroundImage> arrayList2, String str, Activity activity, String str2);
}
