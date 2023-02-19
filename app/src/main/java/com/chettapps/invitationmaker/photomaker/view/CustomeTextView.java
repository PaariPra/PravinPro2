package com.chettapps.invitationmaker.photomaker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/* loaded from: classes2.dex */
public class CustomeTextView extends TextView {
    public CustomeTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomeTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        applyCustomFont(context);
    }

    public CustomeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        setTypeface(FontCache.getTypeface("Montserrat-Medium.ttf", context));
    }
}
