package com.chettapps.invitationmaker.photomaker.pojoClass;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public class ThumbBG implements Parcelable {
    public static final Creator<ThumbBG> CREATOR = new Creator<ThumbBG>() { // from class: com.chettapps.invitationmaker.photomaker.pojoClass.ThumbBG.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ThumbBG createFromParcel(Parcel parcel) {
            return new ThumbBG(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ThumbBG[] newArray(int i) {
            return new ThumbBG[i];
        }
    };
    String category_name;
    ArrayList<MainBG> thumbnail_bg = new ArrayList<>();

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected ThumbBG(Parcel parcel) {
        this.category_name = parcel.readString();
    }

    public String getCategory_name() {
        return this.category_name;
    }

    public void setCategory_name(String str) {
        this.category_name = str;
    }

    public ArrayList<MainBG> getThumbnail_bg() {
        return this.thumbnail_bg;
    }

    public void setThumbnail_bg(ArrayList<MainBG> arrayList) {
        this.thumbnail_bg = arrayList;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.category_name);
    }
}
