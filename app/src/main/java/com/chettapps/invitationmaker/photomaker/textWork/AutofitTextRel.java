package com.chettapps.invitationmaker.photomaker.textWork;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;


import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.view.ResizeTextView;

import cz.msebera.android.httpclient.client.config.CookieSpecs;

/* loaded from: classes2.dex */
public class AutofitTextRel extends RelativeLayout implements MultiTouchListener.TouchCallbackListener {
    private static final String TAG = "AutofitTextRel";
    double angle;
    private ImageView background_iv;
    int baseh;
    int basew;
    int basex;
    int basey;
    private int bgAlpha;
    private int bgColor;
    private String bgDrawable;
    private ImageView border_iv;
    float cX;
    float cY;
    private int capitalFlage;
    private Context context;
    int currentState;
    double dAngle;
    private ImageView delete_iv;
    private int f27s;
    private String field_four;
    private int field_one;
    private String field_three;
    private String field_two;
    private String fontName;
    private GestureDetector gd;
    private int he;
    int height;
    private boolean isBold;
    private boolean isBorderVisible;
    boolean isInit;
    private boolean isItalic;
    public boolean isMultiTouchEnabled;
    private boolean isUnderLine;
    boolean isUndoRedo;
    private int leftMargin;
    private float leftRightShadow;
    private TouchEventListener listener;
    private OnTouchListener mTouchListener1;
    int margl;
    int margt;
    private int progress;
    private OnTouchListener rTouchListener;
    float ratio;
    private ImageView rotate_iv;
    private float rotation;
    Animation scale;
    private ImageView scale_iv;
    int sh;
    private int shadowColor;
    private int shadowColorProgress;
    private int shadowProg;
    int sw;
    private int tAlpha;
    double tAngle;
    private int tColor;
    private String text;
    private Path textPath;
    private ResizeTextView text_iv;
    private float topBottomShadow;
    private int topMargin;
    double vAngle;
    private int wi;
    int width;
    private int xRotateProg;
    private int yRotateProg;
    private int zRotateProg;
    Animation zoomInScale;
    Animation zoomOutScale;

    /* loaded from: classes2.dex */
    public interface TouchEventListener {
        void onDelete();

        void onDoubleTap();

        void onEdit(View view, Uri uri);

        void onRotateDown(View view);

        void onRotateMove(View view);

        void onRotateUp(View view);

        void onScaleDown(View view);

        void onScaleMove(View view);

        void onScaleUp(View view);

        void onTouchDown(View view);

        void onTouchMove(View view);

        void onTouchMoveUpClick(View view);

        void onTouchUp(View view);
    }

    public AutofitTextRel(Context context, boolean z) {
        super(context);
        this.angle = 0.0d;
        this.bgAlpha = 255;
        this.bgColor = 0;
        this.bgDrawable = "0";
        this.cX = 0.0f;
        this.cY = 0.0f;
        this.currentState = 0;
        this.dAngle = 0.0d;
        this.field_four = "";
        this.field_one = 0;
        this.field_three = "";
        this.field_two = "0,0";
        this.fontName = "";
        this.gd = null;
        this.isBorderVisible = false;
        this.isInit = true;
        this.isItalic = false;
        this.isMultiTouchEnabled = true;
        this.isUnderLine = false;
        this.isUndoRedo = false;
        this.leftMargin = 0;
        this.leftRightShadow = 0.0f;
        this.listener = null;
        this.mTouchListener1 = new C07891();
        this.progress = 0;
        this.rTouchListener = new OnTouchListener() { // from class: com.chettapps.invitationmaker.photomaker.textWork.AutofitTextRel.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) view.getParent();
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onRotateDown(AutofitTextRel.this);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    AutofitTextRel.this.cX = rect.exactCenterX();
                    AutofitTextRel.this.cY = rect.exactCenterY();
                    AutofitTextRel.this.vAngle = ((View) view.getParent()).getRotation();
                    AutofitTextRel autofitTextRel2 = AutofitTextRel.this;
                    autofitTextRel2.tAngle = (Math.atan2(autofitTextRel2.cY - motionEvent.getRawY(), AutofitTextRel.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                    AutofitTextRel autofitTextRel3 = AutofitTextRel.this;
                    autofitTextRel3.dAngle = autofitTextRel3.vAngle - AutofitTextRel.this.tAngle;
                    AutofitTextRel.this.currentState = 1;
                } else if (action != 1) {
                    if (action == 2) {
                        if (autofitTextRel != null) {
                            autofitTextRel.requestDisallowInterceptTouchEvent(true);
                        }
                        if (AutofitTextRel.this.listener != null) {
                            AutofitTextRel.this.listener.onRotateMove(AutofitTextRel.this);
                        }
                        AutofitTextRel autofitTextRel4 = AutofitTextRel.this;
                        autofitTextRel4.angle = (Math.atan2(autofitTextRel4.cY - motionEvent.getRawY(), AutofitTextRel.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                        ((View) view.getParent()).setRotation((float) (AutofitTextRel.this.angle + AutofitTextRel.this.dAngle));
                        ((View) view.getParent()).invalidate();
                        ((View) view.getParent()).requestLayout();
                        AutofitTextRel.this.currentState = 3;
                    }
                } else if (AutofitTextRel.this.listener != null) {
                    AutofitTextRel.this.listener.onRotateUp(AutofitTextRel.this);
                    if (AutofitTextRel.this.currentState == 3) {
                        AutofitTextRel.this.clickToSaveWork();
                    }
                    AutofitTextRel.this.currentState = 2;
                }
                return true;
            }
        };
        this.sh = 1794;
        this.shadowColor = 0;
        this.shadowColorProgress = 255;
        this.shadowProg = 0;
        this.sw = 1080;
        this.tAlpha = 100;
        this.tAngle = 0.0d;
        this.tColor = ViewCompat.MEASURED_STATE_MASK;
        this.text = "";
        this.topBottomShadow = 0.0f;
        this.topMargin = 0;
        this.vAngle = 0.0d;
        this.xRotateProg = 0;
        this.yRotateProg = 0;
        this.zRotateProg = 0;
        this.isUndoRedo = z;
        init(context);
    }

    public AutofitTextRel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.angle = 0.0d;
        this.bgAlpha = 255;
        this.bgColor = 0;
        this.bgDrawable = "0";
        this.cX = 0.0f;
        this.cY = 0.0f;
        this.currentState = 0;
        this.dAngle = 0.0d;
        this.field_four = "";
        this.field_one = 0;
        this.field_three = "";
        this.field_two = "0,0";
        this.fontName = "";
        this.gd = null;
        this.isBorderVisible = false;
        this.isInit = true;
        this.isItalic = false;
        this.isMultiTouchEnabled = true;
        this.isUnderLine = false;
        this.isUndoRedo = false;
        this.leftMargin = 0;
        this.leftRightShadow = 0.0f;
        this.listener = null;
        this.mTouchListener1 = new C07891();
        this.progress = 0;
        this.rTouchListener = new OnTouchListener() { // from class: com.chettapps.invitationmaker.photomaker.textWork.AutofitTextRel.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) view.getParent();
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onRotateDown(AutofitTextRel.this);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    AutofitTextRel.this.cX = rect.exactCenterX();
                    AutofitTextRel.this.cY = rect.exactCenterY();
                    AutofitTextRel.this.vAngle = ((View) view.getParent()).getRotation();
                    AutofitTextRel autofitTextRel2 = AutofitTextRel.this;
                    autofitTextRel2.tAngle = (Math.atan2(autofitTextRel2.cY - motionEvent.getRawY(), AutofitTextRel.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                    AutofitTextRel autofitTextRel3 = AutofitTextRel.this;
                    autofitTextRel3.dAngle = autofitTextRel3.vAngle - AutofitTextRel.this.tAngle;
                    AutofitTextRel.this.currentState = 1;
                } else if (action != 1) {
                    if (action == 2) {
                        if (autofitTextRel != null) {
                            autofitTextRel.requestDisallowInterceptTouchEvent(true);
                        }
                        if (AutofitTextRel.this.listener != null) {
                            AutofitTextRel.this.listener.onRotateMove(AutofitTextRel.this);
                        }
                        AutofitTextRel autofitTextRel4 = AutofitTextRel.this;
                        autofitTextRel4.angle = (Math.atan2(autofitTextRel4.cY - motionEvent.getRawY(), AutofitTextRel.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                        ((View) view.getParent()).setRotation((float) (AutofitTextRel.this.angle + AutofitTextRel.this.dAngle));
                        ((View) view.getParent()).invalidate();
                        ((View) view.getParent()).requestLayout();
                        AutofitTextRel.this.currentState = 3;
                    }
                } else if (AutofitTextRel.this.listener != null) {
                    AutofitTextRel.this.listener.onRotateUp(AutofitTextRel.this);
                    if (AutofitTextRel.this.currentState == 3) {
                        AutofitTextRel.this.clickToSaveWork();
                    }
                    AutofitTextRel.this.currentState = 2;
                }
                return true;
            }
        };
        this.sh = 1794;
        this.shadowColor = 0;
        this.shadowColorProgress = 255;
        this.shadowProg = 0;
        this.sw = 1080;
        this.tAlpha = 100;
        this.tAngle = 0.0d;
        this.tColor = ViewCompat.MEASURED_STATE_MASK;
        this.text = "";
        this.topBottomShadow = 0.0f;
        this.topMargin = 0;
        this.vAngle = 0.0d;
        this.xRotateProg = 0;
        this.yRotateProg = 0;
        this.zRotateProg = 0;
        init(context);
    }

    public AutofitTextRel(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.angle = 0.0d;
        this.bgAlpha = 255;
        this.bgColor = 0;
        this.bgDrawable = "0";
        this.cX = 0.0f;
        this.cY = 0.0f;
        this.currentState = 0;
        this.dAngle = 0.0d;
        this.field_four = "";
        this.field_one = 0;
        this.field_three = "";
        this.field_two = "0,0";
        this.fontName = "";
        this.gd = null;
        this.isBorderVisible = false;
        this.isInit = true;
        this.isItalic = false;
        this.isMultiTouchEnabled = true;
        this.isUnderLine = false;
        this.isUndoRedo = false;
        this.leftMargin = 0;
        this.leftRightShadow = 0.0f;
        this.listener = null;
        this.mTouchListener1 = new C07891();
        this.progress = 0;
        this.rTouchListener = new OnTouchListener() { // from class: com.chettapps.invitationmaker.photomaker.textWork.AutofitTextRel.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) view.getParent();
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onRotateDown(AutofitTextRel.this);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    AutofitTextRel.this.cX = rect.exactCenterX();
                    AutofitTextRel.this.cY = rect.exactCenterY();
                    AutofitTextRel.this.vAngle = ((View) view.getParent()).getRotation();
                    AutofitTextRel autofitTextRel2 = AutofitTextRel.this;
                    autofitTextRel2.tAngle = (Math.atan2(autofitTextRel2.cY - motionEvent.getRawY(), AutofitTextRel.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                    AutofitTextRel autofitTextRel3 = AutofitTextRel.this;
                    autofitTextRel3.dAngle = autofitTextRel3.vAngle - AutofitTextRel.this.tAngle;
                    AutofitTextRel.this.currentState = 1;
                } else if (action != 1) {
                    if (action == 2) {
                        if (autofitTextRel != null) {
                            autofitTextRel.requestDisallowInterceptTouchEvent(true);
                        }
                        if (AutofitTextRel.this.listener != null) {
                            AutofitTextRel.this.listener.onRotateMove(AutofitTextRel.this);
                        }
                        AutofitTextRel autofitTextRel4 = AutofitTextRel.this;
                        autofitTextRel4.angle = (Math.atan2(autofitTextRel4.cY - motionEvent.getRawY(), AutofitTextRel.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                        ((View) view.getParent()).setRotation((float) (AutofitTextRel.this.angle + AutofitTextRel.this.dAngle));
                        ((View) view.getParent()).invalidate();
                        ((View) view.getParent()).requestLayout();
                        AutofitTextRel.this.currentState = 3;
                    }
                } else if (AutofitTextRel.this.listener != null) {
                    AutofitTextRel.this.listener.onRotateUp(AutofitTextRel.this);
                    if (AutofitTextRel.this.currentState == 3) {
                        AutofitTextRel.this.clickToSaveWork();
                    }
                    AutofitTextRel.this.currentState = 2;
                }
                return true;
            }
        };
        this.sh = 1794;
        this.shadowColor = 0;
        this.shadowColorProgress = 255;
        this.shadowProg = 0;
        this.sw = 1080;
        this.tAlpha = 100;
        this.tAngle = 0.0d;
        this.tColor = ViewCompat.MEASURED_STATE_MASK;
        this.text = "";
        this.topBottomShadow = 0.0f;
        this.topMargin = 0;
        this.vAngle = 0.0d;
        this.xRotateProg = 0;
        this.yRotateProg = 0;
        this.zRotateProg = 0;
        init(context);
    }

    public AutofitTextRel setOnTouchCallbackListener(TouchEventListener touchEventListener) {
        this.listener = touchEventListener;
        return this;
    }

    public void setDrawParams() {
        invalidate();
    }

    public void init(Context context) {
        this.context = context;
        Display defaultDisplay = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        this.width = point.x;
        int i = point.y;
        this.height = i;
        this.ratio = this.width / i;
        this.text_iv = new ResizeTextView(this.context);
        this.scale_iv = new ImageView(this.context);
        this.border_iv = new ImageView(this.context);
        this.background_iv = new ImageView(this.context);
        this.delete_iv = new ImageView(this.context);
        this.rotate_iv = new ImageView(this.context);
        this.f27s = dpToPx(this.context, 30);
        this.wi = dpToPx(this.context, 200);
        this.he = dpToPx(this.context, 200);
        this.scale_iv.setImageResource(R.drawable.sticker_scale);
        this.background_iv.setImageResource(0);
        this.rotate_iv.setImageResource(R.drawable.sticker_rotate);
        this.delete_iv.setImageResource(R.drawable.sticker_delete1);
        LayoutParams layoutParams = new LayoutParams(this.wi, this.he);
        int i2 = this.f27s;
        LayoutParams layoutParams2 = new LayoutParams(i2, i2);
        layoutParams2.addRule(12);
        layoutParams2.addRule(11);
        int i3 = this.f27s;
        LayoutParams layoutParams3 = new LayoutParams(i3, i3);
        layoutParams3.addRule(12);
        layoutParams3.addRule(9);
        LayoutParams layoutParams4 = new LayoutParams(-1, -1);
        if (Build.VERSION.SDK_INT >= 17) {
            layoutParams4.addRule(17);
        } else {
            layoutParams4.addRule(1);
        }
        int i4 = this.f27s;
        LayoutParams layoutParams5 = new LayoutParams(i4, i4);
        layoutParams5.addRule(10);
        layoutParams5.addRule(9);
        LayoutParams layoutParams6 = new LayoutParams(-1, -1);
        LayoutParams layoutParams7 = new LayoutParams(-1, -1);
        setLayoutParams(layoutParams);
        setBackgroundResource(R.drawable.sticker_border_gray);
        addView(this.background_iv);
        this.background_iv.setLayoutParams(layoutParams7);
        this.background_iv.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(this.border_iv);
        this.border_iv.setLayoutParams(layoutParams6);
        this.border_iv.setTag("border_iv");
        addView(this.text_iv);
        this.text_iv.setText(this.text);
        this.text_iv.setTextColor(this.tColor);
        this.text_iv.setTextSize(400.0f);
        this.text_iv.setLayoutParams(layoutParams4);
        this.text_iv.setGravity(17);
        this.text_iv.setMinTextSize(10.0f);
        addView(this.delete_iv);
        this.delete_iv.setLayoutParams(layoutParams5);
        this.delete_iv.setOnClickListener(new OnClickListener() { // from class: com.invitationmaker.lss.photomaker.textWork.AutofitTextRel.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                final ViewGroup viewGroup = (ViewGroup) AutofitTextRel.this.getParent();
                AutofitTextRel.this.zoomInScale.setAnimationListener(new Animation.AnimationListener() { // from class: com.invitationmaker.lss.photomaker.textWork.AutofitTextRel.2.1
                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationEnd(Animation animation) {
                        viewGroup.removeView(AutofitTextRel.this);
                    }
                });
                AutofitTextRel.this.text_iv.startAnimation(AutofitTextRel.this.zoomInScale);
                AutofitTextRel.this.background_iv.startAnimation(AutofitTextRel.this.zoomInScale);
                if (AutofitTextRel.this.listener != null) {
                    AutofitTextRel.this.listener.onDelete();
                }
                AutofitTextRel.this.setBorderVisibility(false);
            }
        });
        addView(this.rotate_iv);
        this.rotate_iv.setLayoutParams(layoutParams3);
        this.rotate_iv.setOnTouchListener(this.rTouchListener);
        addView(this.scale_iv);
        this.scale_iv.setLayoutParams(layoutParams2);
        this.scale_iv.setTag("scale_iv");
        this.scale_iv.setOnTouchListener(this.mTouchListener1);
        this.rotation = getRotation();
        this.scale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_anim_view);
        this.zoomOutScale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_zoom_out_view);
        this.zoomInScale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_zoom_in_view);
        initGD();
        this.isMultiTouchEnabled = setDefaultTouchListener(true);
        this.isInit = false;
    }

    public void applyLetterSpacing(float f) {
        if (this.text != null) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < this.text.length()) {
                sb.append("" + this.text.charAt(i));
                i++;
                if (i < this.text.length()) {
                    sb.append(" ");
                }
            }
            SpannableString spannableString = new SpannableString(sb.toString());
            if (sb.toString().length() > 1) {
                for (int i2 = 1; i2 < sb.toString().length(); i2 += 2) {
                    spannableString.setSpan(new ScaleXSpan((1.0f + f) / 10.0f), i2, i2 + 1, 33);
                }
            }
            this.text_iv.setText(spannableString, TextView.BufferType.SPANNABLE);
        }
    }

    public void applyLineSpacing(float f) {
        this.text_iv.setLineSpacing(f, 1.0f);
    }

    public void setBoldFont() {
        if (this.isBold) {
            this.isBold = false;
            this.text_iv.setTypeface(Typeface.DEFAULT);
            return;
        }
        this.isBold = true;
        this.text_iv.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public void setCapitalFont() {
        if (this.capitalFlage == 0) {
            this.capitalFlage = 1;
            ResizeTextView resizeTextView = this.text_iv;
            resizeTextView.setText(resizeTextView.getText().toString().toUpperCase());
            return;
        }
        this.capitalFlage = 0;
        ResizeTextView resizeTextView2 = this.text_iv;
        resizeTextView2.setText(resizeTextView2.getText().toString().toLowerCase());
    }

    public void setUnderLineFont() {
        if (this.isUnderLine) {
            this.isUnderLine = false;
            this.text_iv.setText(Html.fromHtml(this.text.replace("<u>", "").replace("</u>", "")));
            return;
        }
        this.isUnderLine = true;
        ResizeTextView resizeTextView = this.text_iv;
        resizeTextView.setText(Html.fromHtml("<u>" + this.text + "</u>"));
    }

    public void setItalicFont() {
        if (this.isItalic) {
            this.isItalic = false;
            TextView textView = new TextView(this.context);
            textView.setText(this.text);
            if (this.isBold) {
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            } else {
                textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);
            }
            this.text_iv.setTypeface(textView.getTypeface());
            return;
        }
        this.isItalic = true;
        TextView textView2 = new TextView(this.context);
        textView2.setText(this.text);
        if (this.isBold) {
            textView2.setTypeface(textView2.getTypeface(), Typeface.BOLD_ITALIC);
        } else {
            textView2.setTypeface(textView2.getTypeface(), Typeface.ITALIC);
        }
        this.text_iv.setTypeface(textView2.getTypeface());
    }

    public void setLeftAlignMent() {
        this.text_iv.setGravity(19);
    }

    public void setCenterAlignMent() {
        this.text_iv.setGravity(17);
    }

    public void setRightAlignMent() {
        this.text_iv.setGravity(21);
    }

    public boolean setDefaultTouchListener(boolean z) {
        if (z) {
            setOnTouchListener(new MultiTouchListener().enableRotation(true).setOnTouchCallbackListener(this).setGestureListener(this.gd));
            return true;
        }
        setOnTouchListener(null);
        return false;
    }

    public boolean getBorderVisibility() {
        return this.isBorderVisible;
    }

    public void setBorderVisibility(boolean z) {
        this.isBorderVisible = z;
        if (!z) {
            this.border_iv.setVisibility(View.GONE);
            this.scale_iv.setVisibility(View.GONE);
            this.delete_iv.setVisibility(View.GONE);
            this.rotate_iv.setVisibility(View.GONE);
            setBackgroundResource(0);
        } else if (this.border_iv.getVisibility() != View.VISIBLE) {
            this.border_iv.setVisibility(View.VISIBLE);
            this.scale_iv.setVisibility(View.VISIBLE);
            this.delete_iv.setVisibility(View.VISIBLE);
            this.rotate_iv.setVisibility(View.VISIBLE);
            setBackgroundResource(R.drawable.sticker_border_gray);
            this.text_iv.startAnimation(this.scale);
        }
    }

    public String getText() {
        return this.text_iv.getText().toString();
    }

    public void setText(String str) {
        this.text_iv.setText(str);
        this.text = str;
        if (!this.isUndoRedo) {
            this.text_iv.startAnimation(this.zoomOutScale);
        }
    }

    public void setTextFont(String str) {
        try {
            if (str.equals(CookieSpecs.DEFAULT)) {
                this.text_iv.setTypeface(Typeface.createFromAsset(this.context.getAssets(), "font/Default.ttf"));
                this.fontName = str;
                return;
            }
            AssetManager assets = this.context.getAssets();
            ResizeTextView resizeTextView = this.text_iv;
            resizeTextView.setTypeface(Typeface.createFromAsset(assets, "font/" + str));
            this.fontName = str;
        } catch (Exception unused) {
            Log.e(TAG, "setTextFont: ");
        }
    }

    public String getFontName() {
        return this.fontName;
    }

    public int getTextColor() {
        return this.tColor;
    }

    public void setTextColor(int i) {
        this.text_iv.setTextColor(i);
        this.tColor = i;
    }

    public int getTextAlpha() {
        return this.tAlpha;
    }

    public void setTextAlpha(int i) {
        this.text_iv.setAlpha(i / 100.0f);
        this.tAlpha = i;
    }

    public int getTextShadowColor() {
        return this.shadowColor;
    }

    public void setTextShadowColor(int i) {
        this.shadowColor = i;
        int alphaComponent = ColorUtils.setAlphaComponent(i, this.shadowColorProgress);
        this.shadowColor = alphaComponent;
        this.text_iv.setShadowLayer(this.shadowProg, this.leftRightShadow, this.topBottomShadow, alphaComponent);
    }

    public void setTextShadowOpacity(int i) {
        this.shadowColorProgress = i;
        int alphaComponent = ColorUtils.setAlphaComponent(this.shadowColor, i);
        this.shadowColor = alphaComponent;
        this.text_iv.setShadowLayer(this.shadowProg, this.leftRightShadow, this.topBottomShadow, alphaComponent);
    }

    public void setLeftRightShadow(float f) {
        this.leftRightShadow = f;
        this.text_iv.setShadowLayer(this.shadowProg, f, this.topBottomShadow, this.shadowColor);
    }

    public void setTopBottomShadow(float f) {
        this.topBottomShadow = f;
        this.text_iv.setShadowLayer(this.shadowProg, this.leftRightShadow, f, this.shadowColor);
    }

    public int getTextShadowProg() {
        return this.shadowProg;
    }

    public void setTextShadowProg(int i) {
        this.shadowProg = i;
        this.text_iv.setShadowLayer(i, this.leftRightShadow, this.topBottomShadow, this.shadowColor);
    }

    public String getBgDrawable() {
        return this.bgDrawable;
    }

    public void setBgDrawable(String str) {
        this.bgDrawable = str;
        this.bgColor = 0;
        this.background_iv.setImageBitmap(getTiledBitmap(this.context, getResources().getIdentifier(str, "drawable", this.context.getPackageName()), this.wi, this.he));
        this.background_iv.setBackgroundColor(this.bgColor);
    }

    public int getBgColor() {
        return this.bgColor;
    }

    public void setBgColor(int i) {
        this.bgDrawable = "0";
        this.bgColor = i;
        this.background_iv.setImageBitmap(null);
        this.background_iv.setBackgroundColor(i);
    }

    public int getBgAlpha() {
        return this.bgAlpha;
    }

    public void setBgAlpha(int i) {
        this.background_iv.setAlpha(i / 255.0f);
        this.bgAlpha = i;
    }

    public TextInfo getTextInfo() {
        TextInfo textInfo = new TextInfo();
        textInfo.setPOS_X(getX());
        textInfo.setPOS_Y(getY());
        textInfo.setWIDTH(this.wi);
        textInfo.setHEIGHT(this.he);
        textInfo.setTEXT(this.text);
        textInfo.setFONT_NAME(this.fontName);
        textInfo.setTEXT_COLOR(this.tColor);
        textInfo.setTEXT_ALPHA(this.tAlpha);
        textInfo.setSHADOW_COLOR(this.shadowColor);
        textInfo.setSHADOW_PROG(this.shadowProg);
        textInfo.setBG_COLOR(this.bgColor);
        textInfo.setBG_DRAWABLE(this.bgDrawable);
        textInfo.setBG_ALPHA(this.bgAlpha);
        textInfo.setROTATION(getRotation());
        textInfo.setXRotateProg(this.xRotateProg);
        textInfo.setYRotateProg(this.yRotateProg);
        textInfo.setZRotateProg(this.zRotateProg);
        textInfo.setCurveRotateProg(this.progress);
        textInfo.setFIELD_ONE(this.field_one);
        textInfo.setFIELD_TWO(this.field_two);
        textInfo.setFIELD_THREE(this.field_three);
        textInfo.setFIELD_FOUR(this.field_four);
        return textInfo;
    }

    public void setTextInfo(TextInfo textInfo, boolean z) {
        Log.e("set Text value", "" + textInfo.getPOS_X() + " ," + textInfo.getPOS_Y() + " ," + textInfo.getWIDTH() + " ," + textInfo.getHEIGHT() + " ," + textInfo.getFIELD_TWO());


        this.wi = textInfo.getWIDTH();
        this.he = textInfo.getHEIGHT();
        this.text = textInfo.getTEXT();
        this.fontName = textInfo.getFONT_NAME();
        this.tColor = textInfo.getTEXT_COLOR();
        this.tAlpha = textInfo.getTEXT_ALPHA();
        this.shadowColor = textInfo.getSHADOW_COLOR();
        this.shadowProg = textInfo.getSHADOW_PROG();
        this.bgColor = textInfo.getBG_COLOR();
        this.bgDrawable = textInfo.getBG_DRAWABLE();
        this.bgAlpha = textInfo.getBG_ALPHA();
        this.rotation = textInfo.getROTATION();
        this.field_two = textInfo.getFIELD_TWO();





        setText(this.text);
        setTextFont(this.fontName);
        setTextColor(this.tColor);
        setTextAlpha(this.tAlpha);
        setTextShadowColor(this.shadowColor);
        setTextShadowProg(this.shadowProg);
        int i = this.bgColor;
        if (i != 0) {
            setBgColor(i);
        } else {
            this.background_iv.setBackgroundColor(0);
        }
        if (this.bgDrawable.equals("0")) {
            this.background_iv.setImageBitmap(null);
        } else {
            setBgDrawable(this.bgDrawable);
        }
        setBgAlpha(this.bgAlpha);
        setRotation(textInfo.getROTATION());
        if (this.field_two.equals("")) {
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(textInfo.getPOS_X());
            setY(textInfo.getPOS_Y());
            return;
        }
        String[] split = this.field_two.split(",");
        int parseInt = Integer.parseInt(split[0]);
        int parseInt2 = Integer.parseInt(split[1]);
        ((LayoutParams) getLayoutParams()).leftMargin = parseInt;
        ((LayoutParams) getLayoutParams()).topMargin = parseInt2;
        getLayoutParams().width = this.wi;
        getLayoutParams().height = this.he;
        setX(textInfo.getPOS_X() + (parseInt * (-1)));
        setY(textInfo.getPOS_Y() + (parseInt2 * (-1)));
    }

    public void optimize(float f, float f2) {
        setX(getX() * f);
        setY(getY() * f2);
        getLayoutParams().width = (int) (this.wi * f);
        getLayoutParams().height = (int) (this.he * f2);
    }

    public void incrX() {
        setX(getX() + 2.0f);
    }

    public void decX() {
        setX(getX() - 2.0f);
    }

    public void incrY() {
        setY(getY() + 2.0f);
    }

    public void decY() {
        setY(getY() - 2.0f);
    }

    public int dpToPx(Context context, int i) {
        context.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * i);
    }

    private Bitmap getTiledBitmap(Context context, int i, int i2, int i3) {
        Rect rect = new Rect(0, 0, i2, i3);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context.getResources(), i, new BitmapFactory.Options()), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

    private void initGD() {
        this.gd = new GestureDetector(this.context, new SimpleListner());
    }

    @Override // com.invitationmaker.lss.photomaker.textWork.MultiTouchListener.TouchCallbackListener
    public void onTouchCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchDown(view);
        }
    }

    @Override // com.invitationmaker.lss.photomaker.textWork.MultiTouchListener.TouchCallbackListener
    public void onTouchUpCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchUp(view);
        }
    }

    @Override // com.invitationmaker.lss.photomaker.textWork.MultiTouchListener.TouchCallbackListener
    public void onTouchUpClick(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchMoveUpClick(view);
        }
    }

    @Override // com.invitationmaker.lss.photomaker.textWork.MultiTouchListener.TouchCallbackListener
    public void onTouchMoveCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchMove(view);
        }
    }

    public float getNewX(float f) {
        return this.width * (f / this.sw);
    }

    public float getNewY(float f) {
        return this.height * (f / this.sh);
    }

    /* loaded from: classes2.dex */
    class C07891 implements OnTouchListener {
        C07891() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            AutofitTextRel autofitTextRel = (AutofitTextRel) view.getParent();
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            LayoutParams layoutParams = (LayoutParams) AutofitTextRel.this.getLayoutParams();
            int action = motionEvent.getAction();
            if (action == 0) {
                if (autofitTextRel != null) {
                    autofitTextRel.requestDisallowInterceptTouchEvent(true);
                }
                if (AutofitTextRel.this.listener != null) {
                    AutofitTextRel.this.listener.onScaleDown(AutofitTextRel.this);
                }
                AutofitTextRel.this.invalidate();
                AutofitTextRel.this.basex = rawX;
                AutofitTextRel.this.basey = rawY;
                AutofitTextRel autofitTextRel2 = AutofitTextRel.this;
                autofitTextRel2.basew = autofitTextRel2.getWidth();
                AutofitTextRel autofitTextRel3 = AutofitTextRel.this;
                autofitTextRel3.baseh = autofitTextRel3.getHeight();
                AutofitTextRel.this.getLocationOnScreen(new int[2]);
                AutofitTextRel.this.margl = layoutParams.leftMargin;
                AutofitTextRel.this.margt = layoutParams.topMargin;
                AutofitTextRel.this.currentState = 0;
            } else if (action == 1) {
                AutofitTextRel autofitTextRel4 = AutofitTextRel.this;
                autofitTextRel4.wi = autofitTextRel4.getLayoutParams().width;
                AutofitTextRel autofitTextRel5 = AutofitTextRel.this;
                autofitTextRel5.he = autofitTextRel5.getLayoutParams().height;
                AutofitTextRel autofitTextRel6 = AutofitTextRel.this;
                autofitTextRel6.leftMargin = ((LayoutParams) autofitTextRel6.getLayoutParams()).leftMargin;
                AutofitTextRel autofitTextRel7 = AutofitTextRel.this;
                autofitTextRel7.topMargin = ((LayoutParams) autofitTextRel7.getLayoutParams()).topMargin;
                AutofitTextRel autofitTextRel8 = AutofitTextRel.this;
                autofitTextRel8.field_two = String.valueOf(AutofitTextRel.this.leftMargin) + "," + String.valueOf(AutofitTextRel.this.topMargin);
                if (AutofitTextRel.this.currentState == 3) {
                    AutofitTextRel.this.clickToSaveWork();
                }
                AutofitTextRel.this.currentState = 2;
                if (AutofitTextRel.this.listener != null) {
                    AutofitTextRel.this.listener.onScaleUp(AutofitTextRel.this);
                }
            } else if (action == 2) {
                if (autofitTextRel != null) {
                    autofitTextRel.requestDisallowInterceptTouchEvent(true);
                }
                if (AutofitTextRel.this.listener != null) {
                    AutofitTextRel.this.listener.onScaleMove(AutofitTextRel.this);
                }
                float degrees = (float) Math.toDegrees(Math.atan2(rawY - AutofitTextRel.this.basey, rawX - AutofitTextRel.this.basex));
                if (degrees < 0.0f) {
                    degrees += 360.0f;
                }
                int i = rawX - AutofitTextRel.this.basex;
                int i2 = rawY - AutofitTextRel.this.basey;
                int i3 = i2 * i2;
                int sqrt = (int) (Math.sqrt((i * i) + i3) * Math.cos(Math.toRadians(degrees - AutofitTextRel.this.getRotation())));
                int sqrt2 = (int) (Math.sqrt((sqrt * sqrt) + i3) * Math.sin(Math.toRadians(degrees - AutofitTextRel.this.getRotation())));
                int i4 = (sqrt * 2) + AutofitTextRel.this.basew;
                int i5 = (sqrt2 * 2) + AutofitTextRel.this.baseh;
                if (i4 > AutofitTextRel.this.f27s) {
                    layoutParams.width = i4;
                    layoutParams.leftMargin = AutofitTextRel.this.margl - sqrt;
                }
                if (i5 > AutofitTextRel.this.f27s) {
                    layoutParams.height = i5;
                    layoutParams.topMargin = AutofitTextRel.this.margt - sqrt2;
                }
                AutofitTextRel.this.setLayoutParams(layoutParams);
                AutofitTextRel.this.currentState = 3;
                if (!AutofitTextRel.this.bgDrawable.equals("0")) {
                    AutofitTextRel autofitTextRel9 = AutofitTextRel.this;
                    autofitTextRel9.wi = autofitTextRel9.getLayoutParams().width;
                    AutofitTextRel autofitTextRel10 = AutofitTextRel.this;
                    autofitTextRel10.he = autofitTextRel10.getLayoutParams().height;
                    AutofitTextRel autofitTextRel11 = AutofitTextRel.this;
                    autofitTextRel11.setBgDrawable(autofitTextRel11.bgDrawable);
                }
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class SimpleListner extends GestureDetector.SimpleOnGestureListener {
        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        SimpleListner() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (AutofitTextRel.this.listener == null) {
                return true;
            }
            AutofitTextRel.this.listener.onDoubleTap();
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            super.onLongPress(motionEvent);
        }
    }

    public void clickToSaveWork() {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchMoveUpClick(this);
        }
    }
}
