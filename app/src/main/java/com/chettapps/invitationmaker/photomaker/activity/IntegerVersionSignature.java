package com.chettapps.invitationmaker.photomaker.activity;

import com.bumptech.glide.load.Key;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* loaded from: classes2.dex */
public class IntegerVersionSignature implements Key {
    private int currentVersion;

    public IntegerVersionSignature(int i) {
        this.currentVersion = i;
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        return (obj instanceof IntegerVersionSignature) && this.currentVersion == ((IntegerVersionSignature) obj).currentVersion;
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        return this.currentVersion;
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ByteBuffer.allocate(32).putInt(this.currentVersion).array());
    }
}
