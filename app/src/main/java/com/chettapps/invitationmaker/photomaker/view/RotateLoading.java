package com.chettapps.invitationmaker.photomaker.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.chettapps.invitationmaker.R;

/* loaded from: classes2.dex */
public class RotateLoading extends View {
    private static final int DEFAULT_SHADOW_POSITION = 2;
    private static final int DEFAULT_SPEED_OF_DEGREE = 10;
    private static final int DEFAULT_WIDTH = 6;
    private float arc;
    private int color;
    private RectF loadingRectF;
    private Paint mPaint;
    private int shadowPosition;
    private RectF shadowRectF;
    private float speedOfArc;
    private int speedOfDegree;
    private int width;
    private int bottomDegree = 190;
    private boolean changeBigger = true;
    private boolean isStart = false;
    private int topDegree = 10;

    public RotateLoading(Context context) {
        super(context);
        initView(context, null);
    }

    public RotateLoading(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context, attributeSet);
    }

    public RotateLoading(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

    @SuppressLint("ResourceType")
    private void initView(Context context, AttributeSet attributeSet) {
        this.color = -1;
        this.width = dpToPx(context, 6.0f);
        this.shadowPosition = dpToPx(getContext(), 2.0f);
        this.speedOfDegree = 10;
        if (attributeSet != null) {



//            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RotateLoading);
//
//
//
//            this.color = obtainStyledAttributes.getColor(0, -1);
//            this.width = obtainStyledAttributes.getDimensionPixelSize(2, dpToPx(context, 6.0f));
//            this.shadowPosition = obtainStyledAttributes.getInt(3, 2);
//            this.speedOfDegree = obtainStyledAttributes.getInt(1, 10);
//            obtainStyledAttributes.recycle();
        }
        this.speedOfArc = this.speedOfDegree / 4;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(this.color);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(this.width);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        int i5 = 0;
        int i6=0;
        super.onSizeChanged(i, i2, i3, i4);
        this.arc = 10.0f;
        float f = this.width * 2;
        this.loadingRectF = new RectF(f, f, i - i5, i2 - i5);
        int i7 = this.width;
        int i8 = i7 * 2;
        float f2 = i8 + this.shadowPosition;
        this.shadowRectF = new RectF(f2, f2, (i - i8) + i6, (i2 - i8) + i6);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isStart) {
            this.mPaint.setColor(Color.parseColor("#1a000000"));
            canvas.drawArc(this.shadowRectF, this.topDegree, this.arc, false, this.mPaint);
            canvas.drawArc(this.shadowRectF, this.bottomDegree, this.arc, false, this.mPaint);
            this.mPaint.setColor(this.color);
            canvas.drawArc(this.loadingRectF, this.topDegree, this.arc, false, this.mPaint);
            canvas.drawArc(this.loadingRectF, this.bottomDegree, this.arc, false, this.mPaint);
            int i = this.topDegree;
            int i2 = this.speedOfDegree;
            int i3 = i + i2;
            this.topDegree = i3;
            int i4 = this.bottomDegree + i2;
            this.bottomDegree = i4;
            if (i3 > 360) {
                this.topDegree = i3 - 360;
            }
            if (i4 > 360) {
                this.bottomDegree = i4 - 360;
            }
            if (this.changeBigger) {
                float f = this.arc;
                if (f < 160.0f) {
                    this.arc = f + this.speedOfArc;
                    invalidate();
                }
            } else {
                float f2 = this.arc;
                if (f2 > i2) {
                    this.arc = f2 - (this.speedOfArc * 2.0f);
                    invalidate();
                }
            }
            float f3 = this.arc;
            if (f3 >= 160.0f || f3 <= 10.0f) {
                this.changeBigger = !this.changeBigger;
                invalidate();
            }
        }
    }

    public void setLoadingColor(int i) {
        this.color = i;
    }

    public int getLoadingColor() {
        return this.color;
    }

    public void start() {
        startAnimator();
        this.isStart = true;
        invalidate();
    }

    public void stop() {
        stopAnimator();
        invalidate();
    }

    public boolean isStart() {
        return this.isStart;
    }

    private void startAnimator() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "scaleX", 0.0f, 1.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, "scaleY", 0.0f, 1.0f);
        ofFloat.setDuration(300L);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat2.setDuration(300L);
        ofFloat2.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.start();
    }

    private void stopAnimator() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, 0.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, 0.0f);
        ofFloat.setDuration(300L);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat2.setDuration(300L);
        ofFloat2.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.chettapps.invitationmaker.photomaker.view.RotateLoading.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                RotateLoading.this.isStart = false;
            }
        });
        animatorSet.start();
    }

    public int dpToPx(Context context, float f) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, f, context.getResources().getDisplayMetrics());
    }
}
