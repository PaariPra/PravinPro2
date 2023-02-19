package com.chettapps.invitationmaker.photomaker.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.widget.EditText;

/* loaded from: classes2.dex */
public class FitEditText extends EditText {
    private static final int NO_LINE_LIMIT = -1;
    private final RectF _availableSpaceRect;
    private boolean _enableSizeCache;
    private boolean _initiallized;
    private int _maxLines;
    private float _maxTextSize;
    private Float _minTextSize;
    private SizeTester _sizeTester;
    private float _spacingAdd;
    private float _spacingMult;
    private final SparseIntArray _textCachedSizes;
    private int _widthLimit;
    private TextPaint paint;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public interface SizeTester {
        int onTestSize(int i, RectF rectF);
    }

    public FitEditText(Context context) {
        this(context, null, 0);
    }

    public FitEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FitEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this._availableSpaceRect = new RectF();
        this._textCachedSizes = new SparseIntArray();
        this._spacingMult = 1.0f;
        this._spacingAdd = 0.0f;
        this._enableSizeCache = true;
        this._initiallized = false;
        try {
            this._minTextSize = Float.valueOf(TypedValue.applyDimension(2, 12.0f, getResources().getDisplayMetrics()));
            this._maxTextSize = getTextSize();
            if (this._maxLines == 0) {
                this._maxLines = -1;
            }
            this._sizeTester = new TextSize();
            this._initiallized = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.widget.TextView
    public void setTypeface(Typeface typeface) {
        if (this.paint == null) {
            this.paint = new TextPaint(getPaint());
        }
        this.paint.setTypeface(typeface);
        super.setTypeface(typeface);
    }

    @Override // android.widget.TextView
    public void setTextSize(float f) {
        this._maxTextSize = f;
        this._textCachedSizes.clear();
        adjustTextSize();
    }

    @Override // android.widget.TextView
    public int getMaxLines() {
        return this._maxLines;
    }

    @Override // android.widget.TextView
    public void setMaxLines(int i) {
        super.setMaxLines(i);
        this._maxLines = i;
        reAdjust();
    }

    @Override // android.widget.TextView
    public void setSingleLine() {
        super.setSingleLine();
        this._maxLines = 1;
        reAdjust();
    }

    @Override // android.widget.TextView
    public void setSingleLine(boolean z) {
        super.setSingleLine(z);
        if (z) {
            this._maxLines = 1;
        } else {
            this._maxLines = -1;
        }
        reAdjust();
    }

    @Override // android.widget.TextView
    public void setLines(int i) {
        super.setLines(i);
        this._maxLines = i;
        reAdjust();
    }

    @Override // android.widget.TextView
    public void setTextSize(int i, float f) {
        Resources resources;
        Context context = getContext();
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        this._maxTextSize = TypedValue.applyDimension(i, f, resources.getDisplayMetrics());
        this._textCachedSizes.clear();
        adjustTextSize();
    }

    @Override // android.widget.TextView
    public void setLineSpacing(float f, float f2) {
        super.setLineSpacing(f, f2);
        this._spacingMult = f2;
        this._spacingAdd = f;
    }

    public void setMinTextSize(Float f) {
        this._minTextSize = f;
        reAdjust();
    }

    public Float get_minTextSize() {
        return this._minTextSize;
    }

    private void reAdjust() {
        adjustTextSize();
    }

    private void adjustTextSize() {
        try {
            if (this._initiallized) {
                int round = Math.round(this._minTextSize.floatValue());
                int measuredHeight = (getMeasuredHeight() - getCompoundPaddingBottom()) - getCompoundPaddingTop();
                int measuredWidth = (getMeasuredWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight();
                this._widthLimit = measuredWidth;
                if (measuredWidth > 0) {
                    this._availableSpaceRect.right = measuredWidth;
                    this._availableSpaceRect.bottom = measuredHeight;
                    super.setTextSize(0, efficientTextSizeSearch(round, (int) this._maxTextSize, this._sizeTester, this._availableSpaceRect));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setEnableSizeCache(boolean z) {
        this._enableSizeCache = z;
        this._textCachedSizes.clear();
        adjustTextSize();
    }

    private int efficientTextSizeSearch(int i, int i2, SizeTester sizeTester, RectF rectF) {
        if (!this._enableSizeCache) {
            return binarySearch(i, i2, sizeTester, rectF);
        }
        String obj = getText().toString();
        int length = obj == null ? 0 : obj.length();
        int i3 = this._textCachedSizes.get(length);
        if (i3 != 0) {
            return i3;
        }
        int binarySearch = binarySearch(i, i2, sizeTester, rectF);
        this._textCachedSizes.put(length, binarySearch);
        return binarySearch;
    }

    private int binarySearch(int i, int i2, SizeTester sizeTester, RectF rectF) {
        int i3 = i2 - 1;
        int i4 = i;
        while (i <= i3) {
            int i5 = (i + i3) >>> 1;
            try {
                int onTestSize = sizeTester.onTestSize(i5, rectF);
                if (onTestSize < 0) {
                    i = i5 + 1;
                    i3 = i3;
                    i4 = i;
                } else if (onTestSize <= 0) {
                    return i5;
                } else {
                    i4 = i5 - 1;
                    i3 = i4;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return i4;
    }

    @Override // android.widget.TextView
    protected void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        reAdjust();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        this._textCachedSizes.clear();
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 || i2 != i4) {
            reAdjust();
        }
    }

    /* loaded from: classes2.dex */
    class TextSize implements SizeTester {
        final RectF textRect = new RectF();

        TextSize() {
        }

        @Override // com.chettapps.invitationmaker.photomaker.view.FitEditText.SizeTester
        public int onTestSize(int i, RectF rectF) {
            FitEditText.this.paint.setTextSize(i);
            String obj = FitEditText.this.getText().toString();
            if (FitEditText.this.getMaxLines() == 1) {
                this.textRect.bottom = FitEditText.this.paint.getFontSpacing();
                this.textRect.right = FitEditText.this.paint.measureText(obj);
            } else {
                StaticLayout staticLayout = new StaticLayout(obj, FitEditText.this.paint, FitEditText.this._widthLimit, Layout.Alignment.ALIGN_NORMAL, FitEditText.this._spacingMult, FitEditText.this._spacingAdd, true);
                if (FitEditText.this.getMaxLines() != -1 && staticLayout.getLineCount() > FitEditText.this.getMaxLines()) {
                    return 1;
                }
                this.textRect.bottom = staticLayout.getHeight();
                int i2 = -1;
                for (int i3 = 0; i3 < staticLayout.getLineCount(); i3++) {
                    if (i2 < staticLayout.getLineWidth(i3)) {
                        i2 = (int) staticLayout.getLineWidth(i3);
                    }
                }
                this.textRect.right = i2;
            }
            this.textRect.offsetTo(0.0f, 0.0f);
            return rectF.contains(this.textRect) ? -1 : 1;
        }
    }
}
