package com.chettapps.invitationmaker.photomaker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/* loaded from: classes2.dex */
public class SquareImageView extends ImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(i, i);
    }
}
