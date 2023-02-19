package com.chettapps.invitationmaker.photomaker.pojoClass;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class PosterThumbFull implements Parcelable, Comparable, Cloneable {
    public static final Creator<PosterThumbFull> CREATOR = new Creator<PosterThumbFull>() { // from class: com.chettapps.invitationmaker.photomaker.pojoClass.PosterThumbFull.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PosterThumbFull createFromParcel(Parcel parcel) {
            return new PosterThumbFull(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PosterThumbFull[] newArray(int i) {
            return new PosterThumbFull[i];
        }
    };
    int post_id;
    String post_thumb;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PosterThumbFull() {
    }

    public PosterThumbFull(Parcel parcel) {
        this.post_id = parcel.readInt();
        this.post_thumb = parcel.readString();
    }

    public int getPost_id() {
        return this.post_id;
    }

    public void setPost_id(int i) {
        this.post_id = i;
    }

    public String getPost_thumb() {
        return this.post_thumb;
    }

    public void setPost_thumb(String str) {
        this.post_thumb = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.post_id);
        parcel.writeString(this.post_thumb);
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        PosterThumbFull posterThumbFull = (PosterThumbFull) obj;
        return (posterThumbFull.post_id != this.post_id || !posterThumbFull.post_thumb.equals(this.post_thumb)) ? 1 : 0;
    }

    public PosterThumbFull clone() {
        try {
            return (PosterThumbFull) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
