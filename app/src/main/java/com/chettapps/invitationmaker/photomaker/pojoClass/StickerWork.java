package com.chettapps.invitationmaker.photomaker.pojoClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class StickerWork {
    @SerializedName("Background")
    @Expose
    private Backgrounds background = new Backgrounds();
    @SerializedName("Fonts")
    @Expose
    private List<String> fonts = new ArrayList();
    @SerializedName("Sticker")
    @Expose
    private Sticker sticker = new Sticker();

    public Backgrounds getBackground() {
        return this.background;
    }

    public void setBackground(Backgrounds backgrounds) {
        this.background = backgrounds;
    }

    public List<String> getFonts() {
        return this.fonts;
    }

    public void setFonts(List<String> list) {
        this.fonts = list;
    }

    public Sticker getSticker() {
        return this.sticker;
    }

    public void setSticker(Sticker sticker) {
        this.sticker = sticker;
    }
}
