package com.chettapps.invitationmaker.photomaker.handler;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/* loaded from: classes2.dex */
public class RepeatListener implements View.OnTouchListener {
    private final View.OnClickListener clickListener;
    private View downView;
    private ImageView guideline;
    private Handler handler = new Handler();
    private Runnable handlerRunnable = new Runnable() { // from class: com.invitationmaker.lss.photomaker.handler.RepeatListener.1
        @Override // java.lang.Runnable
        public void run() {
            RepeatListener.this.handler.postDelayed(this, RepeatListener.this.normalInterval);
            RepeatListener.this.clickListener.onClick(RepeatListener.this.downView);
        }
    };
    private int initialInterval;
    private final int normalInterval;

    public RepeatListener(int i, int i2, ImageView imageView, View.OnClickListener onClickListener) {
        if (onClickListener == null) {
            throw new IllegalArgumentException("null runnable");
        } else if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("negative interval");
        } else {
            this.initialInterval = i;
            this.normalInterval = i2;
            this.clickListener = onClickListener;
            this.guideline = imageView;
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action == 1) {
                this.guideline.setVisibility(8);
            } else if (action != 3) {
                return false;
            }
            this.handler.removeCallbacks(this.handlerRunnable);
            this.downView.setPressed(false);
            this.downView = null;
            return true;
        }
        if (this.guideline.getVisibility() == 8) {
            this.guideline.setVisibility(0);
        }
        this.handler.removeCallbacks(this.handlerRunnable);
        this.handler.postDelayed(this.handlerRunnable, this.initialInterval);
        this.downView = view;
        view.setPressed(true);
        this.clickListener.onClick(view);
        return true;
    }
}
