package com.chettapps.invitationmaker.photomaker.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatTextView;

/* loaded from: classes2.dex */
public class ResizeTextView extends AppCompatTextView {
    public static float _minTextSize;
    private final RectF _availableSpaceRect;
    private boolean _initialized;
    private int _maxLines;
    private float _maxTextSize;
    private TextPaint _paint;
    private final SizeTester _sizeTester;
    private float _spacingAdd;
    private float _spacingMult;
    private int _widthLimit;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public interface SizeTester {
        int onTestSize(int i, RectF rectF);
    }

    public boolean isValidWordWrap(char c, char c2) {
        return c == ' ' || c == '-';
    }

    /* loaded from: classes2.dex */
    class TextSize implements SizeTester {
        final RectF textRect = new RectF();

        TextSize() {
        }

        @Override // com.chettapps.invitationmaker.photomaker.view.ResizeTextView.SizeTester
        public int onTestSize(int i, RectF rectF) {
            String str;
            ResizeTextView.this._paint.setTextSize(i);
            TransformationMethod transformationMethod = ResizeTextView.this.getTransformationMethod();
            if (transformationMethod != null) {
                str = transformationMethod.getTransformation(ResizeTextView.this.getText(), ResizeTextView.this).toString();
            } else {
                str = ResizeTextView.this.getText().toString();
            }
            if (ResizeTextView.this.getMaxLines() == 1) {
                this.textRect.bottom = ResizeTextView.this._paint.getFontSpacing();
                this.textRect.right = ResizeTextView.this._paint.measureText(str);
            } else {
                StaticLayout staticLayout = new StaticLayout(str, ResizeTextView.this._paint, ResizeTextView.this._widthLimit, Layout.Alignment.ALIGN_NORMAL, ResizeTextView.this._spacingMult, ResizeTextView.this._spacingAdd, true);
                if (ResizeTextView.this.getMaxLines() != -1 && staticLayout.getLineCount() > ResizeTextView.this.getMaxLines()) {
                    return 1;
                }
                this.textRect.bottom = staticLayout.getHeight();
                int lineCount = staticLayout.getLineCount();
                int i2 = -1;
                for (int i3 = 0; i3 < lineCount; i3++) {
                    int lineEnd = staticLayout.getLineEnd(i3);
                    if (i3 < lineCount - 1 && lineEnd > 0 && !ResizeTextView.this.isValidWordWrap(str.charAt(lineEnd - 1), str.charAt(lineEnd))) {
                        return 1;
                    }
                    if (i2 < staticLayout.getLineRight(i3) - staticLayout.getLineLeft(i3)) {
                        i2 = ((int) staticLayout.getLineRight(i3)) - ((int) staticLayout.getLineLeft(i3));
                    }
                }
                this.textRect.right = i2;
            }
            this.textRect.offsetTo(0.0f, 0.0f);
            return rectF.contains(this.textRect) ? -1 : 1;
        }
    }

    public ResizeTextView(Context context) {
        this(context, null, 16842884);
    }

    public ResizeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public ResizeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this._availableSpaceRect = new RectF();
        this._spacingMult = 1.0f;
        this._spacingAdd = 0.0f;
        this._initialized = false;
        _minTextSize = TypedValue.applyDimension(2, 12.0f, getResources().getDisplayMetrics());
        this._maxTextSize = getTextSize();
        this._paint = new TextPaint(getPaint());
        if (this._maxLines == 0) {
            this._maxLines = -1;
        }
        this._sizeTester = new TextSize();
        this._initialized = true;
    }

    public void setJustify() {
        if (Build.VERSION.SDK_INT >= 26) {
            setJustificationMode(1);
        }
    }

    @Override // android.widget.TextView
    public void setAllCaps(boolean z) {
        super.setAllCaps(z);
        adjustTextSize();
    }

    @Override // android.widget.TextView
    public void setTypeface(Typeface typeface) {
        super.setTypeface(typeface);
        adjustTextSize();
    }

    @Override // android.widget.TextView
    public void setTextSize(float f) {
        this._maxTextSize = f;
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
        adjustTextSize();
    }

    @Override // android.widget.TextView
    public void setSingleLine() {
        super.setSingleLine();
        this._maxLines = 1;
        adjustTextSize();
    }

    @Override // android.widget.TextView
    public void setSingleLine(boolean z) {
        super.setSingleLine(z);
        if (z) {
            this._maxLines = 1;
        } else {
            this._maxLines = -1;
        }
        adjustTextSize();
    }

    @Override // android.widget.TextView
    public void setLines(int i) {
        super.setLines(i);
        this._maxLines = i;
        adjustTextSize();
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView
    public void setTextSize(int i, float f) {
        Resources resources;
        Context context = getContext();
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        this._maxTextSize = TypedValue.applyDimension(i, f, resources.getDisplayMetrics());
        adjustTextSize();
    }

    @Override // android.widget.TextView
    public void setLineSpacing(float f, float f2) {
        super.setLineSpacing(f, f2);
        this._spacingMult = f2;
        this._spacingAdd = f;
    }

    public void setMinTextSize(float f) {
        _minTextSize = f;
        adjustTextSize();
    }

    private void adjustTextSize() {
        if (this._initialized) {
            int i = (int) _minTextSize;
            int measuredHeight = (getMeasuredHeight() - getCompoundPaddingBottom()) - getCompoundPaddingTop();
            int measuredWidth = (getMeasuredWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight();
            this._widthLimit = measuredWidth;
            if (measuredWidth > 0) {
                this._paint = new TextPaint(getPaint());
                this._availableSpaceRect.right = this._widthLimit;
                this._availableSpaceRect.bottom = measuredHeight;
                superSetTextSize(i);
            }
        }
    }

    private void superSetTextSize(int i) {
        super.setTextSize(0, binarySearch(i, (int) this._maxTextSize, this._sizeTester, this._availableSpaceRect));
    }

    private int binarySearch(int i, int i2, SizeTester sizeTester, RectF rectF) {
        int i3 = i2 - 1;
        int i4 = i;
        while (i <= i3) {
            int i5 = (i + i3) >>> 1;
            int onTestSize = sizeTester.onTestSize(i5, rectF);
            if (onTestSize < 0) {
                i = i5 + 1;
                i4 = i;
            } else if (onTestSize <= 0) {
                return i5;
            } else {
                i4 = i5 - 1;
                i3 = i4;
            }
        }
        return i4;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        adjustTextSize();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 || i2 != i4) {
            adjustTextSize();
        }
    }
}
