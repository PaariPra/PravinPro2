package com.chettapps.invitationmaker.photomaker.pojoClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/* loaded from: classes.dex */
public class Advertise {
    @SerializedName("admob_banner")
    @Expose
    private String admobBanner;
    @SerializedName("admob_intertial")
    @Expose
    private String admobIntertial;
    @SerializedName("admob_native")
    @Expose
    private String admobNative;
    @SerializedName("admob_reward")
    @Expose
    private String admobReward;
    @SerializedName("facebook_banner")
    @Expose
    private String facebookBanner;
    @SerializedName("facebook_intertial")
    @Expose
    private String facebookIntertial;
    @SerializedName("facebook_native")
    @Expose
    private String facebookNative;
    @SerializedName("facebook_small")
    @Expose
    private String facebookSmall;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("isAppUpdate")
    @Expose
    private String isAppUpdate;
    @SerializedName("isShutDown")
    @Expose
    private String isShutDown;
    @SerializedName("newAppLink")
    @Expose
    private String newAppLink;
    @SerializedName("notifyUpdate")
    @Expose
    private String notifyUpdate;

    public String getIsAppUpdate() {
        return this.isAppUpdate;
    }

    public void setIsAppUpdate(String str) {
        this.isAppUpdate = str;
    }

    public String getNotifyUpdate() {
        return this.notifyUpdate;
    }

    public void setNotifyUpdate(String str) {
        this.notifyUpdate = str;
    }

    public String getIsShutDown() {
        return this.isShutDown;
    }

    public void setIsShutDown(String str) {
        this.isShutDown = str;
    }

    public String getNewAppLink() {
        return this.newAppLink;
    }

    public void setNewAppLink(String str) {
        this.newAppLink = str;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String str) {
        this.flag = str;
    }

    public String getFacebookIntertial() {
        return this.facebookIntertial;
    }

    public void setFacebookIntertial(String str) {
        this.facebookIntertial = str;
    }

    public String getFacebookNative() {
        return this.facebookNative;
    }

    public void setFacebookNative(String str) {
        this.facebookNative = str;
    }

    public String getFacebookBanner() {
        return this.facebookBanner;
    }

    public void setFacebookBanner(String str) {
        this.facebookBanner = str;
    }

    public String getFacebookSmall() {
        return this.facebookSmall;
    }

    public void setFacebookSmall(String str) {
        this.facebookSmall = str;
    }

    public String getAdmobIntertial() {
        return this.admobIntertial;
    }

    public void setAdmobIntertial(String str) {
        this.admobIntertial = str;
    }

    public String getAdmobNative() {
        return this.admobNative;
    }

    public void setAdmobNative(String str) {
        this.admobNative = str;
    }

    public String getAdmobBanner() {
        return this.admobBanner;
    }

    public void setAdmobBanner(String str) {
        this.admobBanner = str;
    }

    public String getAdmobReward() {
        return this.admobReward;
    }

    public void setAdmobReward(String str) {
        this.admobReward = str;
    }

    public String toString() {
        return "Advertise{flag='" + this.flag + "', facebookIntertial='" + this.facebookIntertial + "', facebookNative='" + this.facebookNative + "', facebookBanner='" + this.facebookBanner + "', facebookSmall='" + this.facebookSmall + "', admobIntertial='" + this.admobIntertial + "', admobNative='" + this.admobNative + "', admobBanner='" + this.admobBanner + "', admobReward='" + this.admobReward + "', isAppUpdate='" + this.isAppUpdate + "', notifyUpdate='" + this.notifyUpdate + "', isShutDown='" + this.isShutDown + "', newAppLink='" + this.newAppLink + "'}";
    }
}
