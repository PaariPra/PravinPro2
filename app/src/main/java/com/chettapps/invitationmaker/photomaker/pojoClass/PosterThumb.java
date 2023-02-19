package com.chettapps.invitationmaker.photomaker.pojoClass;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public class PosterThumb {
    private ArrayList<PosterThumbList> data;
    private String error;
    private String message;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String str) {
        this.error = str;
    }

    public ArrayList<PosterThumbList> getData() {
        return this.data;
    }

    public void setData(ArrayList<PosterThumbList> arrayList) {
        this.data = arrayList;
    }

    public String toString() {
        return "ClassPojo [message = " + this.message + ", error = " + this.error + ", data = " + this.data + "]";
    }
}
