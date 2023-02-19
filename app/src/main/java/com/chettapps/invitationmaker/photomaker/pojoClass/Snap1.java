package com.chettapps.invitationmaker.photomaker.pojoClass;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public class Snap1 {
    int cat_id;
    private int mGravity;
    private String mText;
    private ArrayList<PosterThumbFull> posterThumbFulls;
    private String ratio;

    public String getRatio() {
        return this.ratio;
    }

    public void setRatio(String str) {
        this.ratio = str;
    }

    public Snap1(int i, String str, ArrayList<PosterThumbFull> arrayList, int i2, String str2) {
        this.mGravity = i;
        this.mText = str;
        this.posterThumbFulls = arrayList;
        this.cat_id = i2;
        this.ratio = str2;
    }

    public String getText() {
        return this.mText;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public ArrayList<PosterThumbFull> getPosterThumbFulls() {
        return this.posterThumbFulls;
    }

    public int getCat_id() {
        return this.cat_id;
    }
}
