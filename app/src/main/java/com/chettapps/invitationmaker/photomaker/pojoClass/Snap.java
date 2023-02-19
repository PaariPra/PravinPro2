package com.chettapps.invitationmaker.photomaker.pojoClass;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public class Snap {
    private ArrayList<BackgroundImage> backgroundImages;
    private int mGravity;
    private String mText;

    public Snap(int i, String str, ArrayList<BackgroundImage> arrayList) {
        this.mGravity = i;
        this.mText = str;
        this.backgroundImages = arrayList;
    }

    public String getText() {
        return this.mText;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public ArrayList<BackgroundImage> getBackgroundImages() {
        return this.backgroundImages;
    }
}
