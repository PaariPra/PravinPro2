package com.chettapps.invitationmaker.photomaker.main;

import android.graphics.Bitmap;

import com.android.volley.Response;

import java.io.File;

/* loaded from: classes2.dex */
public final class GetPositionResponseListener implements Response.Listener {
    public final BGImageActivity bgImageActivity;
    public final File f$1;

    public GetPositionResponseListener(BGImageActivity bGImageActivity, File file) {
        this.bgImageActivity = bGImageActivity;
        this.f$1 = file;
    }

    @Override // com.android.volley.Response.Listener
    public final void onResponse(Object obj) {
        this.bgImageActivity.onGetPositionResponseReceived(this.f$1, (Bitmap) obj);
    }
}
