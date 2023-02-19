package com.chettapps.invitationmaker.photomaker.utils;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

/* loaded from: classes2.dex */
public class CustomTypefaceSpan extends MetricAffectingSpan {
    private final Typeface typeface;

    public CustomTypefaceSpan(Typeface typeface) {
        this.typeface = typeface;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        apply(textPaint);
    }

    @Override // android.text.style.MetricAffectingSpan
    public void updateMeasureState(TextPaint textPaint) {
        apply(textPaint);
    }

    private void apply(Paint paint) {
        paint.setFakeBoldText(true);
        paint.setTypeface(this.typeface);
    }
}
