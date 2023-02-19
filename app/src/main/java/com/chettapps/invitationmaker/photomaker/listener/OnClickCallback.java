package com.chettapps.invitationmaker.photomaker.listener;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.chettapps.invitationmaker.photomaker.pojoClass.BackgroundImage;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public interface OnClickCallback {


    void onClickCallBack(ArrayList<BackgroundImage> backgroundImages, String str, Context context, String s);
}
