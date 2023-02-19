package com.chettapps.invitationmaker.photomaker.listener;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.chettapps.invitationmaker.photomaker.view.StickerView;

/* loaded from: classes2.dex */
public class MultiTouchListener implements View.OnTouchListener {
    private static final int INVALID_POINTER_ID = -1;
    Bitmap bitmap;
    private float mPrevX;
    private float mPrevY;
    boolean bt = false;
    int currentMode = 0;
    GestureDetector gd = null;
    public boolean isRotateEnabled = true;
    public boolean isRotationEnabled = false;
    public boolean isTranslateEnabled = true;
    private TouchCallbackListener listener = null;
    private int mActivePointerId = -1;
    private ScaleGestureDetector mScaleGestureDetector = new ScaleGestureDetector(new ScaleGestureListener());
    public float maximumScale = 8.0f;
    public float minimumScale = 0.5f;

    /* loaded from: classes2.dex */
    public interface TouchCallbackListener {
        void onTouchCallback(View view);

        void onTouchMoveCallback(View view);

        void onTouchUpCallback(View view);

        void onTouchUpClick(View view);
    }

    private static float adjustAngle(float f) {
        return f > 180.0f ? f - 360.0f : f < -180.0f ? f + 360.0f : f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class TransformInfo {
        public float deltaAngle;
        public float deltaScale;
        public float deltaX;
        public float deltaY;
        public float maximumScale;
        public float minimumScale;
        public float pivotX;
        public float pivotY;

        private TransformInfo() {
        }
    }

    /* loaded from: classes2.dex */
    private class ScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private float mPivotX;
        private float mPivotY;
        private Vector2D mPrevSpanVector;

        private ScaleGestureListener() {
            this.mPrevSpanVector = new Vector2D();
        }

        @Override // com.chettapps.invitationmaker.photomaker.listener.ScaleGestureDetector.SimpleOnScaleGestureListener, com.chettapps.invitationmaker.photomaker.listener.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(View view, ScaleGestureDetector scaleGestureDetector) {
            this.mPivotX = scaleGestureDetector.getFocusX();
            this.mPivotY = scaleGestureDetector.getFocusY();
            this.mPrevSpanVector.set(scaleGestureDetector.getCurrentSpanVector());
            return true;
        }

        @Override // com.chettapps.invitationmaker.photomaker.listener.ScaleGestureDetector.SimpleOnScaleGestureListener, com.chettapps.invitationmaker.photomaker.listener.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(View view, ScaleGestureDetector scaleGestureDetector) {
            TransformInfo transformInfo = new TransformInfo();
            float f = 0.0f;
            transformInfo.deltaAngle = MultiTouchListener.this.isRotateEnabled ? Vector2D.getAngle(this.mPrevSpanVector, scaleGestureDetector.getCurrentSpanVector()) : 0.0f;
            transformInfo.deltaX = MultiTouchListener.this.isTranslateEnabled ? scaleGestureDetector.getFocusX() - this.mPivotX : 0.0f;
            if (MultiTouchListener.this.isTranslateEnabled) {
                f = scaleGestureDetector.getFocusY() - this.mPivotY;
            }
            transformInfo.deltaY = f;
            transformInfo.pivotX = this.mPivotX;
            transformInfo.pivotY = this.mPivotY;
            transformInfo.minimumScale = MultiTouchListener.this.minimumScale;
            transformInfo.maximumScale = MultiTouchListener.this.maximumScale;
            MultiTouchListener.this.move(view, transformInfo);
            return false;
        }
    }

    private static void adjustTranslation(View view, float f, float f2) {
        float[] fArr = {f, f2};
        view.getMatrix().mapVectors(fArr);
        view.setTranslationX(view.getTranslationX() + fArr[0]);
        view.setTranslationY(view.getTranslationY() + fArr[1]);
    }

    private static void computeRenderOffset(View view, float f, float f2) {
        if (view.getPivotX() != f || view.getPivotY() != f2) {
            float[] fArr = {0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr);
            view.setPivotX(f);
            view.setPivotY(f2);
            float[] fArr2 = {0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr2);
            float f3 = fArr2[1] - fArr[1];
            view.setTranslationX(view.getTranslationX() - (fArr2[0] - fArr[0]));
            view.setTranslationY(view.getTranslationY() - f3);
        }
    }

    public MultiTouchListener setGestureListener(GestureDetector gestureDetector) {
        this.gd = gestureDetector;
        return this;
    }

    public MultiTouchListener setOnTouchCallbackListener(TouchCallbackListener touchCallbackListener) {
        this.listener = touchCallbackListener;
        return this;
    }

    public MultiTouchListener enableRotation(boolean z) {
        this.isRotationEnabled = z;
        return this;
    }

    public MultiTouchListener setMinScale(float f) {
        this.minimumScale = f;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void move(View view, TransformInfo transformInfo) {
        if (this.isRotationEnabled) {
            view.setRotation(adjustAngle(view.getRotation() + transformInfo.deltaAngle));
        }
    }

    public boolean handleTransparency(View view, MotionEvent motionEvent) {
        boolean z = false;
        try {
            z = true;
        } catch (Exception unused) {
        }
        if (motionEvent.getAction() == 2 && this.bt) {
            return true;
        }
        if (motionEvent.getAction() == 1 && this.bt) {
            this.bt = false;
            Bitmap bitmap = this.bitmap;
            if (bitmap == null) {
                return true;
            }
            bitmap.recycle();
            return true;
        }
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        float rotation = view.getRotation();
        Matrix matrix = new Matrix();
        matrix.postRotate(-rotation);
        float[] fArr = {(int) (motionEvent.getRawX() - iArr[0]), (int) (motionEvent.getRawY() - iArr[1])};
        matrix.mapPoints(fArr);
        int i = (int) fArr[0];
        int i2 = (int) fArr[1];
        if (motionEvent.getAction() == 0) {
            this.bt = false;
            view.setDrawingCacheEnabled(true);
            Bitmap createBitmap = Bitmap.createBitmap(view.getDrawingCache());
            this.bitmap = createBitmap;
            i = (int) (i * (createBitmap.getWidth() / (this.bitmap.getWidth() * view.getScaleX())));
            i2 = (int) (i2 * (this.bitmap.getWidth() / (this.bitmap.getHeight() * view.getScaleX())));
            view.setDrawingCacheEnabled(false);
        }
        if (i >= 0 && i2 >= 0 && i <= this.bitmap.getWidth() && i2 <= this.bitmap.getHeight()) {
            if (this.bitmap.getPixel(i, i2) != 0) {
                z = false;
            }
            if (motionEvent.getAction() != 0) {
                return z;
            }
            this.bt = z;
            return z;
        }
        return false;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        TouchCallbackListener touchCallbackListener;
        this.mScaleGestureDetector.onTouchEvent(view, motionEvent);
        RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
        GestureDetector gestureDetector = this.gd;
        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(motionEvent);
        }
        if (this.isTranslateEnabled) {
            int action = motionEvent.getAction();
            int actionMasked = motionEvent.getActionMasked() & action;
            int i = 0;
            if (actionMasked == 0) {
                this.currentMode = 0;
                if (relativeLayout != null) {
                    relativeLayout.requestDisallowInterceptTouchEvent(true);
                }
                TouchCallbackListener touchCallbackListener2 = this.listener;
                if (touchCallbackListener2 != null) {
                    touchCallbackListener2.onTouchCallback(view);
                }
                view.bringToFront();
                if (view instanceof StickerView) {
                    ((StickerView) view).setBorderVisibility(true);
                }
                this.mPrevX = motionEvent.getX();
                this.mPrevY = motionEvent.getY();
                this.mActivePointerId = motionEvent.getPointerId(0);
            } else if (actionMasked == 1) {
                this.mActivePointerId = -1;
                TouchCallbackListener touchCallbackListener3 = this.listener;
                if (touchCallbackListener3 != null) {
                    touchCallbackListener3.onTouchUpCallback(view);
                }
                if (this.currentMode == 2 && (touchCallbackListener = this.listener) != null) {
                    touchCallbackListener.onTouchUpClick(view);
                }
                this.currentMode = 1;
                float rotation = view.getRotation();
                if (Math.abs(90.0f - Math.abs(rotation)) <= 5.0f) {
                    rotation = rotation > 0.0f ? 90.0f : -90.0f;
                }
                if (Math.abs(0.0f - Math.abs(rotation)) <= 5.0f) {
                    rotation = rotation > 0.0f ? 0.0f : -0.0f;
                }
                if (Math.abs(180.0f - Math.abs(rotation)) <= 5.0f) {
                    rotation = rotation > 0.0f ? 180.0f : -180.0f;
                }
                view.setRotation(rotation);
                Log.i("testing", "Final Rotation : " + rotation);
            } else if (actionMasked == 2) {
                this.currentMode = 2;
                if (relativeLayout != null) {
                    relativeLayout.requestDisallowInterceptTouchEvent(true);
                }
                TouchCallbackListener touchCallbackListener4 = this.listener;
                if (touchCallbackListener4 != null) {
                    touchCallbackListener4.onTouchMoveCallback(view);
                }
                int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                if (findPointerIndex != -1) {
                    float x = motionEvent.getX(findPointerIndex);
                    float y = motionEvent.getY(findPointerIndex);
                    if (!this.mScaleGestureDetector.isInProgress()) {
                        adjustTranslation(view, x - this.mPrevX, y - this.mPrevY);
                    }
                }
            } else if (actionMasked == 3) {
                this.mActivePointerId = -1;
            } else if (actionMasked == 6) {
                int i2 = (65280 & action) >> 8;
                if (motionEvent.getPointerId(i2) == this.mActivePointerId) {
                    if (i2 == 0) {
                        i = 1;
                    }
                    this.mPrevX = motionEvent.getX(i);
                    this.mPrevY = motionEvent.getY(i);
                    this.mActivePointerId = motionEvent.getPointerId(i);
                }
            }
        }
        return true;
    }
}
