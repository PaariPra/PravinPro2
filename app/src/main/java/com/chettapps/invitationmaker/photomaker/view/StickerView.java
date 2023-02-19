package com.chettapps.invitationmaker.photomaker.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;

import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.listener.MultiTouchListener;
import com.chettapps.invitationmaker.photomaker.utility.ImageUtils;
import com.chettapps.invitationmaker.photomaker.utils.ElementInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.HttpStatus;

/* loaded from: classes2.dex */
public class StickerView extends RelativeLayout implements MultiTouchListener.TouchCallbackListener {
    public static final String TAG = "ResizableStickerView";
    double angle;
    int baseh;
    int basew;
    int basex;
    int basey;
    private ImageView border_iv;
    private Bitmap btmp;
    float cX;
    float cY;
    private String colorType;
    private Context context;
    private int currentState;
    double dAngle;
    private ImageView delete_iv;
    private String drawableId;
    private int f26s;
    private String field_four;
    private int field_one;
    private String field_three;
    private String field_two;
    private ImageView flip_iv;
    private int he;
    float heightMain;
    private int hueProg;
    private int imgAlpha;
    private int imgColor;
    private boolean isBorderVisible;
    private boolean isColorFilterEnable;
    public boolean isMultiTouchEnabled;
    public boolean isUndoRedo;
    private int leftMargin;
    private TouchEventListener listener;
    private OnTouchListener mTouchListener1;
    public ImageView main_iv;
    int margl;
    int margt;
    private OnTouchListener rTouchListener;
    private Uri resUri;
    private ImageView rotate_iv;
    private float rotation;
    Animation scale;
    private int scaleRotateProg;
    private ImageView scale_iv;
    int screenHeight;
    int screenWidth;
    private String stkr_path;
    double tAngle;
    private int topMargin;
    double vAngle;
    private int wi;
    float widthMain;
    private int xRotateProg;
    private int yRotateProg;
    private float yRotation;
    private int zRotateProg;
    Animation zoomInScale;
    Animation zoomOutScale;

    /* loaded from: classes2.dex */
    public interface TouchEventListener {
        void onDelete();

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

    public StickerView(Context context) {
        super(context);
        this.angle = 0.0d;
        this.btmp = null;
        this.cX = 0.0f;
        this.cY = 0.0f;
        this.colorType = "colored";
        this.currentState = 0;
        this.dAngle = 0.0d;
        this.field_four = "";
        this.field_one = 0;
        this.field_three = "";
        this.field_two = "0,0";
        this.heightMain = 0.0f;
        this.hueProg = 1;
        this.imgAlpha = 255;
        this.imgColor = 0;
        this.isBorderVisible = false;
        this.isColorFilterEnable = false;
        this.isMultiTouchEnabled = true;
        this.isUndoRedo = false;
        this.leftMargin = 0;
        this.listener = null;
        this.mTouchListener1 = new TouchListerner();
        this.rTouchListener = new TouchListner1();
        this.resUri = null;
        this.scaleRotateProg = 0;
        this.screenHeight = HttpStatus.SC_MULTIPLE_CHOICES;
        this.screenWidth = HttpStatus.SC_MULTIPLE_CHOICES;
        this.stkr_path = "";
        this.tAngle = 0.0d;
        this.topMargin = 0;
        this.vAngle = 0.0d;
        this.widthMain = 0.0f;
        this.xRotateProg = 0;
        this.yRotateProg = 0;
        this.zRotateProg = 0;
        init(context);
    }

    public StickerView(Context context, boolean z) {
        super(context);
        this.angle = 0.0d;
        this.btmp = null;
        this.cX = 0.0f;
        this.cY = 0.0f;
        this.colorType = "colored";
        this.currentState = 0;
        this.dAngle = 0.0d;
        this.field_four = "";
        this.field_one = 0;
        this.field_three = "";
        this.field_two = "0,0";
        this.heightMain = 0.0f;
        this.hueProg = 1;
        this.imgAlpha = 255;
        this.imgColor = 0;
        this.isBorderVisible = false;
        this.isColorFilterEnable = false;
        this.isMultiTouchEnabled = true;
        this.isUndoRedo = false;
        this.leftMargin = 0;
        this.listener = null;
        this.mTouchListener1 = new TouchListerner();
        this.rTouchListener = new TouchListner1();
        this.resUri = null;
        this.scaleRotateProg = 0;
        this.screenHeight = HttpStatus.SC_MULTIPLE_CHOICES;
        this.screenWidth = HttpStatus.SC_MULTIPLE_CHOICES;
        this.stkr_path = "";
        this.tAngle = 0.0d;
        this.topMargin = 0;
        this.vAngle = 0.0d;
        this.widthMain = 0.0f;
        this.xRotateProg = 0;
        this.yRotateProg = 0;
        this.zRotateProg = 0;
        this.isUndoRedo = z;
        init(context);
    }

    public StickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.angle = 0.0d;
        this.btmp = null;
        this.cX = 0.0f;
        this.cY = 0.0f;
        this.colorType = "colored";
        this.currentState = 0;
        this.dAngle = 0.0d;
        this.field_four = "";
        this.field_one = 0;
        this.field_three = "";
        this.field_two = "0,0";
        this.heightMain = 0.0f;
        this.hueProg = 1;
        this.imgAlpha = 255;
        this.imgColor = 0;
        this.isBorderVisible = false;
        this.isColorFilterEnable = false;
        this.isMultiTouchEnabled = true;
        this.isUndoRedo = false;
        this.leftMargin = 0;
        this.listener = null;
        this.mTouchListener1 = new TouchListerner();
        this.rTouchListener = new TouchListner1();
        this.resUri = null;
        this.scaleRotateProg = 0;
        this.screenHeight = HttpStatus.SC_MULTIPLE_CHOICES;
        this.screenWidth = HttpStatus.SC_MULTIPLE_CHOICES;
        this.stkr_path = "";
        this.tAngle = 0.0d;
        this.topMargin = 0;
        this.vAngle = 0.0d;
        this.widthMain = 0.0f;
        this.xRotateProg = 0;
        this.yRotateProg = 0;
        this.zRotateProg = 0;
        init(context);
    }

    public StickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.angle = 0.0d;
        this.btmp = null;
        this.cX = 0.0f;
        this.cY = 0.0f;
        this.colorType = "colored";
        this.currentState = 0;
        this.dAngle = 0.0d;
        this.field_four = "";
        this.field_one = 0;
        this.field_three = "";
        this.field_two = "0,0";
        this.heightMain = 0.0f;
        this.hueProg = 1;
        this.imgAlpha = 255;
        this.imgColor = 0;
        this.isBorderVisible = false;
        this.isColorFilterEnable = false;
        this.isMultiTouchEnabled = true;
        this.isUndoRedo = false;
        this.leftMargin = 0;
        this.listener = null;
        this.mTouchListener1 = new TouchListerner();
        this.rTouchListener = new TouchListner1();
        this.resUri = null;
        this.scaleRotateProg = 0;
        this.screenHeight = HttpStatus.SC_MULTIPLE_CHOICES;
        this.screenWidth = HttpStatus.SC_MULTIPLE_CHOICES;
        this.stkr_path = "";
        this.tAngle = 0.0d;
        this.topMargin = 0;
        this.vAngle = 0.0d;
        this.widthMain = 0.0f;
        this.xRotateProg = 0;
        this.yRotateProg = 0;
        this.zRotateProg = 0;
        init(context);
    }

    public StickerView setOnTouchCallbackListener(TouchEventListener touchEventListener) {
        this.listener = touchEventListener;
        return this;
    }

    public void init(Context context) {
        this.context = context;
        this.main_iv = new ImageView(this.context);
        this.scale_iv = new ImageView(this.context);
        this.border_iv = new ImageView(this.context);
        this.flip_iv = new ImageView(this.context);
        this.rotate_iv = new ImageView(this.context);
        this.delete_iv = new ImageView(this.context);
        this.f26s = dpToPx(this.context, 25);
        this.wi = dpToPx(this.context, 200);
        this.he = dpToPx(this.context, 200);
        this.scale_iv.setImageResource(R.drawable.sticker_scale);
        this.border_iv.setImageResource(R.drawable.sticker_border_gray);
        this.flip_iv.setImageResource(R.drawable.sticker_flip);
        this.rotate_iv.setImageResource(R.drawable.sticker_rotate);
        this.delete_iv.setImageResource(R.drawable.sticker_delete1);
        LayoutParams layoutParams = new LayoutParams(this.wi, this.he);
        LayoutParams layoutParams2 = new LayoutParams(-1, -1);
        layoutParams2.setMargins(5, 5, 5, 5);
        if (Build.VERSION.SDK_INT >= 17) {
            layoutParams2.addRule(17);
        } else {
            layoutParams2.addRule(1);
        }
        int i = this.f26s;
        LayoutParams layoutParams3 = new LayoutParams(i, i);
        layoutParams3.addRule(12);
        layoutParams3.addRule(11);
        layoutParams3.setMargins(5, 5, 5, 5);
        int i2 = this.f26s;
        LayoutParams layoutParams4 = new LayoutParams(i2, i2);
        layoutParams4.addRule(10);
        layoutParams4.addRule(11);
        layoutParams4.setMargins(5, 5, 5, 5);
        int i3 = this.f26s;
        LayoutParams layoutParams5 = new LayoutParams(i3, i3);
        layoutParams5.addRule(12);
        layoutParams5.addRule(9);
        layoutParams5.setMargins(5, 5, 5, 5);
        int i4 = this.f26s;
        LayoutParams layoutParams6 = new LayoutParams(i4, i4);
        layoutParams6.addRule(10);
        layoutParams6.addRule(9);
        layoutParams6.setMargins(5, 5, 5, 5);
        LayoutParams layoutParams7 = new LayoutParams(-1, -1);
        setLayoutParams(layoutParams);
        setBackgroundResource(R.drawable.sticker_gray1);
        addView(this.border_iv);
        this.border_iv.setLayoutParams(layoutParams7);
        this.border_iv.setScaleType(ImageView.ScaleType.FIT_XY);
        this.border_iv.setTag("border_iv");
        addView(this.main_iv);
        this.main_iv.setLayoutParams(layoutParams2);
        addView(this.flip_iv);
        this.flip_iv.setLayoutParams(layoutParams4);
        this.flip_iv.setOnClickListener(new OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.view.StickerView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ImageView imageView = StickerView.this.main_iv;
                float f = -180.0f;
                if (StickerView.this.main_iv.getRotationY() == -180.0f) {
                    f = 0.0f;
                }
                imageView.setRotationY(f);
                StickerView.this.main_iv.invalidate();
                StickerView.this.requestLayout();
                StickerView.this.clickToSaveWork();
            }
        });
        addView(this.rotate_iv);
        this.rotate_iv.setLayoutParams(layoutParams5);
        this.rotate_iv.setOnTouchListener(this.rTouchListener);
        addView(this.delete_iv);
        this.delete_iv.setLayoutParams(layoutParams6);
        this.delete_iv.setOnClickListener(new DeleteClick());
        addView(this.scale_iv);
        this.scale_iv.setLayoutParams(layoutParams3);
        this.scale_iv.setOnTouchListener(this.mTouchListener1);
        this.scale_iv.setTag("scale_iv");
        this.rotation = getRotation();
        this.scale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_anim_view);
        this.zoomOutScale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_zoom_out_view);
        this.zoomInScale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_zoom_in_view);
        this.isMultiTouchEnabled = setDefaultTouchListener(true);
    }

    public boolean setDefaultTouchListener(boolean z) {
        if (z) {
            setOnTouchListener(new MultiTouchListener().enableRotation(true).setOnTouchCallbackListener(this));
            return true;
        }
        setOnTouchListener(null);
        return false;
    }

    public void setBorderVisibility(boolean z) {
        this.isBorderVisible = z;
        if (!z) {
            this.border_iv.setVisibility(8);
            this.scale_iv.setVisibility(8);
            this.flip_iv.setVisibility(8);
            this.rotate_iv.setVisibility(8);
            this.delete_iv.setVisibility(8);
            setBackgroundResource(0);
            if (this.isColorFilterEnable) {
                this.main_iv.setColorFilter(Color.parseColor("#303828"));
            }
        } else if (this.border_iv.getVisibility() != 0) {
            this.border_iv.setVisibility(0);
            this.scale_iv.setVisibility(0);
            this.flip_iv.setVisibility(0);
            this.rotate_iv.setVisibility(0);
            this.delete_iv.setVisibility(0);
            setBackgroundResource(R.drawable.sticker_gray1);
            this.main_iv.startAnimation(this.scale);
        }
    }

    public boolean getBorderVisbilty() {
        return this.isBorderVisible;
    }

    public void opecitySticker(int i) {
        try {
            this.main_iv.setAlpha(i);
            this.imgAlpha = i;
        } catch (Exception unused) {
        }
    }

    public int getHueProg() {
        return this.hueProg;
    }

    public void setHueProg(int i) {
        this.hueProg = i;
        if (i == 0) {
            this.main_iv.setColorFilter(-1);
        } else if (i == 100) {
            this.main_iv.setColorFilter(ViewCompat.MEASURED_STATE_MASK);
        } else {
            this.main_iv.setColorFilter(ColorFilterGenerator.adjustHue(i));
        }
    }

    public String getColorType() {
        return this.colorType;
    }

    public int getAlphaProg() {
        return this.imgAlpha;
    }

    public void setAlphaProg(int i) {
        opecitySticker(i);
    }

    public int getColor() {
        return this.imgColor;
    }

    public void setColor(int i) {
        try {
            this.main_iv.setColorFilter(i);
            this.imgColor = i;
        } catch (Exception unused) {
        }
    }

    public void setBgDrawable(String str) {
        Glide.with(this.context).load(Integer.valueOf(getResources().getIdentifier(str, "drawable", this.context.getPackageName()))).apply((BaseRequestOptions<?>) new RequestOptions().dontAnimate().placeholder(R.drawable.no_image).error(R.drawable.no_image)).into(this.main_iv);
        this.drawableId = str;
        if (!this.isUndoRedo) {
            this.main_iv.startAnimation(this.zoomOutScale);
        }
    }

    public void setStrPath(String str) {
        try {
            ImageView imageView = this.main_iv;
            Uri parse = Uri.parse(str);
            Context context = this.context;
            int i = this.screenWidth;
            int i2 = this.screenHeight;
            if (i <= i2) {
                i = i2;
            }
            imageView.setImageBitmap(ImageUtils.getResampleImageBitmap(parse, context, i));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.stkr_path = str;
        if (!this.isUndoRedo) {
            this.main_iv.startAnimation(this.zoomOutScale);
        }
    }

    public Uri getMainImageUri() {
        return this.resUri;
    }

    public void setMainImageUri(Uri uri) {
        this.resUri = uri;
        this.main_iv.setImageURI(uri);
    }

    public Bitmap getMainImageBitmap() {
        return this.btmp;
    }

    public void setMainImageBitmap(Bitmap bitmap) {
        this.main_iv.setImageBitmap(bitmap);
    }

    public void optimize(float f, float f2) {
        setX(getX() * f);
        setY(getY() * f2);
        getLayoutParams().width = (int) (this.wi * f);
        getLayoutParams().height = (int) (this.he * f2);
    }

    public void optimizeScreen(float f, float f2) {
        this.screenHeight = (int) f2;
        this.screenWidth = (int) f;
    }

    public void setMainLayoutWH(float f, float f2) {
        this.widthMain = f;
        this.heightMain = f2;
    }

    public float getMainWidth() {
        return this.widthMain;
    }

    public float getMainHeight() {
        return this.heightMain;
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

    public ElementInfo getComponentInfo() {
        Bitmap bitmap = this.btmp;
        if (bitmap != null) {
            this.stkr_path = saveBitmapObject1(bitmap);
        }
        ElementInfo elementInfo = new ElementInfo();
        elementInfo.setPOS_X(getX());
        elementInfo.setPOS_Y(getY());
        elementInfo.setWIDTH(this.wi);
        elementInfo.setHEIGHT(this.he);
        elementInfo.setRES_ID(this.drawableId);
        elementInfo.setSTC_COLOR(this.imgColor);
        elementInfo.setRES_URI(this.resUri);
        elementInfo.setSTC_OPACITY(this.imgAlpha);
        elementInfo.setCOLORTYPE(this.colorType);
        elementInfo.setBITMAP(this.btmp);
        elementInfo.setROTATION(getRotation());
        elementInfo.setY_ROTATION(this.main_iv.getRotationY());
        elementInfo.setXRotateProg(this.xRotateProg);
        elementInfo.setYRotateProg(this.yRotateProg);
        elementInfo.setZRotateProg(this.zRotateProg);
        elementInfo.setScaleProg(this.scaleRotateProg);
        elementInfo.setSTKR_PATH(this.stkr_path);
        elementInfo.setSTC_HUE(this.hueProg);
        elementInfo.setFIELD_ONE(this.field_one);
        elementInfo.setFIELD_TWO(this.field_two);
        elementInfo.setFIELD_THREE(this.field_three);
        elementInfo.setFIELD_FOUR(this.field_four);
        return elementInfo;
    }

    public ElementInfo getComponentInfoUL() {
        ElementInfo elementInfo = new ElementInfo();
        elementInfo.setPOS_X(getX());
        elementInfo.setPOS_Y(getY());
        elementInfo.setWIDTH(this.wi);
        elementInfo.setHEIGHT(this.he);
        elementInfo.setRES_ID(this.drawableId);
        elementInfo.setSTC_COLOR(this.imgColor);
        elementInfo.setRES_URI(this.resUri);
        elementInfo.setSTC_OPACITY(this.imgAlpha);
        elementInfo.setCOLORTYPE(this.colorType);
        elementInfo.setBITMAP(this.btmp);
        elementInfo.setROTATION(getRotation());
        elementInfo.setY_ROTATION(this.main_iv.getRotationY());
        elementInfo.setXRotateProg(this.xRotateProg);
        elementInfo.setYRotateProg(this.yRotateProg);
        elementInfo.setZRotateProg(this.zRotateProg);
        elementInfo.setScaleProg(this.scaleRotateProg);
        elementInfo.setSTKR_PATH(this.stkr_path);
        elementInfo.setSTC_HUE(this.hueProg);
        elementInfo.setFIELD_ONE(this.field_one);
        elementInfo.setFIELD_TWO(this.field_two);
        elementInfo.setFIELD_THREE(this.field_three);
        elementInfo.setFIELD_FOUR(this.field_four);
        return elementInfo;
    }

    public void setComponentInfo(ElementInfo elementInfo) {
        this.wi = elementInfo.getWIDTH();
        this.he = elementInfo.getHEIGHT();
        this.drawableId = elementInfo.getRES_ID();
        this.resUri = elementInfo.getRES_URI();
        this.btmp = elementInfo.getBITMAP();
        this.rotation = elementInfo.getROTATION();
        this.imgColor = elementInfo.getSTC_COLOR();
        this.yRotation = elementInfo.getY_ROTATION();
        this.imgAlpha = elementInfo.getSTC_OPACITY();
        this.stkr_path = elementInfo.getSTKR_PATH();
        this.colorType = elementInfo.getCOLORTYPE();
        this.hueProg = elementInfo.getSTC_HUE();
        this.field_two = elementInfo.getFIELD_TWO();
        if (!this.stkr_path.equals("")) {
            setStrPath(this.stkr_path);
        } else if (this.drawableId.equals("")) {
            this.main_iv.setImageBitmap(this.btmp);
        } else {
            setBgDrawable(this.drawableId);
        }
        if (this.colorType.equals("white")) {
            setColor(this.imgColor);
        } else {
            setHueProg(this.hueProg);
        }
        setRotation(this.rotation);
        opecitySticker(this.imgAlpha);
        if (this.field_two.equals("")) {
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(elementInfo.getPOS_X());
            setY(elementInfo.getPOS_Y());
        } else {
            String[] split = this.field_two.split(",");
            int parseInt = Integer.parseInt(split[0]);
            int parseInt2 = Integer.parseInt(split[1]);
            ((LayoutParams) getLayoutParams()).leftMargin = parseInt;
            ((LayoutParams) getLayoutParams()).topMargin = parseInt2;
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(elementInfo.getPOS_X() + (parseInt * (-1)));
            setY(elementInfo.getPOS_Y() + (parseInt2 * (-1)));
        }
        if (elementInfo.getTYPE() == "SHAPE") {
            this.flip_iv.setVisibility(8);
        }
        if (elementInfo.getTYPE() == "STICKER") {
            this.flip_iv.setVisibility(0);
        }
        this.main_iv.setRotationY(this.yRotation);
    }

    private void saveStkrBitmap(final Bitmap bitmap) {
        final ProgressDialog show = ProgressDialog.show(this.context, "", "", true);
        show.setCancelable(false);
        new Thread(new Runnable() { // from class: com.chettapps.invitationmaker.photomaker.view.StickerView.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    StickerView stickerView = StickerView.this;
                    stickerView.stkr_path = stickerView.saveBitmapObject1(bitmap);
                } catch (Exception e) {
                    Log.i("testing", "Exception " + e.getMessage());
                    e.printStackTrace();
                } catch (Throwable unused) {
                }
                show.dismiss();
            }
        }).start();
        show.setOnDismissListener(new RingProgressClick());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String saveBitmapObject1(Bitmap bitmap) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Invitation Card Stickers/category1");
        file.mkdirs();
        File file2 = new File(file, "raw1-" + System.currentTimeMillis() + ".png");
        String absolutePath = file2.getAbsolutePath();
        if (file2.exists()) {
            file2.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            return absolutePath;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("testing", "Exception" + e.getMessage());
            return "";
        }
    }

    public int dpToPx(Context context, int i) {
        context.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * i);
    }

    private double getLength(double d, double d2, double d3, double d4) {
        return Math.sqrt(Math.pow(d4 - d2, 2.0d) + Math.pow(d3 - d, 2.0d));
    }

    public void enableColorFilter(boolean z) {
        this.isColorFilterEnable = z;
    }

    @Override // com.chettapps.invitationmaker.photomaker.listener.MultiTouchListener.TouchCallbackListener
    public void onTouchCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchDown(view);
        }
    }

    @Override // com.chettapps.invitationmaker.photomaker.listener.MultiTouchListener.TouchCallbackListener
    public void onTouchUpCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchUp(view);
        }
    }

    @Override // com.invitationmaker.lss.photomaker.listener.MultiTouchListener.TouchCallbackListener
    public void onTouchUpClick(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchMoveUpClick(view);
        }
    }

    @Override // com.invitationmaker.lss.photomaker.listener.MultiTouchListener.TouchCallbackListener
    public void onTouchMoveCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchMove(view);
        }
    }

    /* loaded from: classes2.dex */
    class TouchListerner implements OnTouchListener {
        TouchListerner() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            StickerView stickerView = (StickerView) view.getParent();
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            LayoutParams layoutParams = (LayoutParams) StickerView.this.getLayoutParams();
            int action = motionEvent.getAction();
            if (action == 0) {
                if (stickerView != null) {
                    stickerView.requestDisallowInterceptTouchEvent(true);
                }
                if (StickerView.this.listener != null) {
                    StickerView.this.listener.onScaleDown(StickerView.this);
                }
                StickerView.this.invalidate();
                StickerView.this.basex = rawX;
                StickerView.this.basey = rawY;
                StickerView stickerView2 = StickerView.this;
                stickerView2.basew = stickerView2.getWidth();
                StickerView stickerView3 = StickerView.this;
                stickerView3.baseh = stickerView3.getHeight();
                StickerView.this.getLocationOnScreen(new int[2]);
                StickerView.this.margl = layoutParams.leftMargin;
                StickerView.this.margt = layoutParams.topMargin;
                StickerView.this.currentState = 1;
            } else if (action == 1) {
                StickerView stickerView4 = StickerView.this;
                stickerView4.wi = stickerView4.getLayoutParams().width;
                StickerView stickerView5 = StickerView.this;
                stickerView5.he = stickerView5.getLayoutParams().height;
                StickerView stickerView6 = StickerView.this;
                stickerView6.leftMargin = ((LayoutParams) stickerView6.getLayoutParams()).leftMargin;
                StickerView stickerView7 = StickerView.this;
                stickerView7.topMargin = ((LayoutParams) stickerView7.getLayoutParams()).topMargin;
                StickerView stickerView8 = StickerView.this;
                stickerView8.field_two = String.valueOf(StickerView.this.leftMargin) + "," + String.valueOf(StickerView.this.topMargin);
                if (StickerView.this.listener != null) {
                    StickerView.this.listener.onScaleUp(StickerView.this);
                    if (StickerView.this.currentState == 3) {
                        StickerView.this.clickToSaveWork();
                    }
                    StickerView.this.currentState = 2;
                }
            } else if (action == 2) {
                if (stickerView != null) {
                    stickerView.requestDisallowInterceptTouchEvent(true);
                }
                if (StickerView.this.listener != null) {
                    StickerView.this.listener.onScaleMove(StickerView.this);
                }
                float degrees = (float) Math.toDegrees(Math.atan2(rawY - StickerView.this.basey, rawX - StickerView.this.basex));
                if (degrees < 0.0f) {
                    degrees += 360.0f;
                }
                int i = rawX - StickerView.this.basex;
                int i2 = rawY - StickerView.this.basey;
                int i3 = i2 * i2;
                int sqrt = (int) (Math.sqrt((i * i) + i3) * Math.cos(Math.toRadians(degrees - StickerView.this.getRotation())));
                int sqrt2 = (int) (Math.sqrt((sqrt * sqrt) + i3) * Math.sin(Math.toRadians(degrees - StickerView.this.getRotation())));
                int i4 = (sqrt * 2) + StickerView.this.basew;
                int i5 = (sqrt2 * 2) + StickerView.this.baseh;
                if (i4 > StickerView.this.f26s) {
                    layoutParams.width = i4;
                    layoutParams.leftMargin = StickerView.this.margl - sqrt;
                }
                if (i5 > StickerView.this.f26s) {
                    layoutParams.height = i5;
                    layoutParams.topMargin = StickerView.this.margt - sqrt2;
                }
                StickerView.this.setLayoutParams(layoutParams);
                StickerView.this.performLongClick();
                StickerView.this.currentState = 3;
            }
            return true;
        }
    }

    /* loaded from: classes2.dex */
    class TouchListner1 implements OnTouchListener {
        TouchListner1() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            StickerView stickerView = (StickerView) view.getParent();
            int action = motionEvent.getAction();
            if (action == 0) {
                if (stickerView != null) {
                    stickerView.requestDisallowInterceptTouchEvent(true);
                }
                if (StickerView.this.listener != null) {
                    StickerView.this.listener.onRotateDown(StickerView.this);
                }
                Rect rect = new Rect();
                ((View) view.getParent()).getGlobalVisibleRect(rect);
                StickerView.this.cX = rect.exactCenterX();
                StickerView.this.cY = rect.exactCenterY();
                StickerView.this.vAngle = ((View) view.getParent()).getRotation();
                StickerView stickerView2 = StickerView.this;
                stickerView2.tAngle = (Math.atan2(stickerView2.cY - motionEvent.getRawY(), StickerView.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                StickerView stickerView3 = StickerView.this;
                stickerView3.dAngle = stickerView3.vAngle - StickerView.this.tAngle;
                StickerView.this.currentState = 1;
            } else if (action != 1) {
                if (action == 2) {
                    if (stickerView != null) {
                        stickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (StickerView.this.listener != null) {
                        StickerView.this.listener.onRotateMove(StickerView.this);
                    }
                    StickerView stickerView4 = StickerView.this;
                    stickerView4.angle = (Math.atan2(stickerView4.cY - motionEvent.getRawY(), StickerView.this.cX - motionEvent.getRawX()) * 180.0d) / 3.141592653589793d;
                    ((View) view.getParent()).setRotation((float) (StickerView.this.angle + StickerView.this.dAngle));
                    ((View) view.getParent()).invalidate();
                    ((View) view.getParent()).requestLayout();
                    StickerView.this.currentState = 3;
                }
            } else if (StickerView.this.listener != null) {
                StickerView.this.listener.onRotateUp(StickerView.this);
                if (StickerView.this.currentState == 3) {
                    StickerView.this.clickToSaveWork();
                }
                StickerView.this.currentState = 2;
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class DeleteClick implements OnClickListener {
        DeleteClick() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            final ViewGroup viewGroup = (ViewGroup) StickerView.this.getParent();
            StickerView.this.zoomInScale.setAnimationListener(new Animation.AnimationListener() { // from class: com.invitationmaker.lss.photomaker.view.StickerView.DeleteClick.1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    viewGroup.removeView(StickerView.this);
                }
            });
            StickerView.this.main_iv.startAnimation(StickerView.this.zoomInScale);
            StickerView.this.setBorderVisibility(false);
            if (StickerView.this.listener != null) {
                StickerView.this.listener.onDelete();
            }
        }
    }

    /* loaded from: classes2.dex */
    class RingProgressClick implements DialogInterface.OnDismissListener {
        @Override // android.content.DialogInterface.OnDismissListener
        public void onDismiss(DialogInterface dialogInterface) {
        }

        RingProgressClick() {
        }
    }

    public void clickToSaveWork() {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchMoveUpClick(this);
        }
    }
}
