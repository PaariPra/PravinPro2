package com.chettapps.invitationmaker.photomaker.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.transition.Explode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.motion.widget.Key;
import androidx.core.content.FileProvider;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.bumptech.glide.Glide;
import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.InvitationApplication;
import com.chettapps.invitationmaker.photomaker.activity.BaseActivity;
import com.chettapps.invitationmaker.photomaker.adapter.RecyclerItemClickListener;
import com.chettapps.invitationmaker.photomaker.adapter.RecyclerOverLayAdapter;
import com.chettapps.invitationmaker.photomaker.adapter.RecyclerTextBgAdapter;
import com.chettapps.invitationmaker.photomaker.adapter.TextFontAdapter;
import com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment;
import com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment2;
import com.chettapps.invitationmaker.photomaker.fragment.StickerFragment;
import com.chettapps.invitationmaker.photomaker.fragment.StickerFragmentMore;
import com.chettapps.invitationmaker.photomaker.handler.BlurOperationAsync;
import com.chettapps.invitationmaker.photomaker.handler.DatabaseHandler;
import com.chettapps.invitationmaker.photomaker.handler.RepeatListener;
import com.chettapps.invitationmaker.photomaker.handler.TemplateInfo;
import com.chettapps.invitationmaker.photomaker.listener.GetSnapListener;
import com.chettapps.invitationmaker.photomaker.listener.GetSnapListenerData;
import com.chettapps.invitationmaker.photomaker.listener.OnClickCallback;
import com.chettapps.invitationmaker.photomaker.network.NetworkConnectivityReceiver;
import com.chettapps.invitationmaker.photomaker.pojoClass.BackgroundImage;
import com.chettapps.invitationmaker.photomaker.pojoClass.PosterCo;
import com.chettapps.invitationmaker.photomaker.pojoClass.Sticker_info;
import com.chettapps.invitationmaker.photomaker.pojoClass.Text_info;
import com.chettapps.invitationmaker.photomaker.textWork.AutofitTextRel;
import com.chettapps.invitationmaker.photomaker.textWork.TextInfo;
import com.chettapps.invitationmaker.photomaker.utility.ImageUtils;
import com.chettapps.invitationmaker.photomaker.utils.Configure;
import com.chettapps.invitationmaker.photomaker.utils.ElementInfo;
import com.chettapps.invitationmaker.photomaker.utils.PreferenceClass;
import com.chettapps.invitationmaker.photomaker.view.FitEditText;
import com.chettapps.invitationmaker.photomaker.view.StickerView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;


public class CreateCardActivity extends BaseActivity implements View.OnClickListener, GetSnapListenerData, SeekBar.OnSeekBarChangeListener, GetColorListener, OnSetImageSticker, GetSnapListener, StickerView.TouchEventListener, AutofitTextRel.TouchEventListener {
    private static final int OPEN_CUSTOM_ACITIVITY = 4;
    private static final int SELECT_PICTURE_CAMERA = 805;
    private static final int SELECT_PICTURE_FROM_GALLERY_BACKGROUND = 809;
    private static final int SELECT_PICTURE_GALLERY = 807;
    private static final String TAG = "CreateCardActivity";
    private static final int TEXT_ACTIVITY = 808;
    private static final int TYPE_STICKER = 8072;
    public static CreateCardActivity activity = null;
       public static ImageView backgroundImage = null;
    public static Bitmap bitBlur = null;
    public static Bitmap btmSticker = null;
    public static ImageView btnLayControls = null;
    public static Activity context = null;
    public static Bitmap imgBtmap = null;
    public static boolean isUpadted = false;
    public static boolean isUpdated = false;
    public static FrameLayout layContainer;
    public static int mRadius;
    public static SeekBar seekTailys;
    public static RelativeLayout txtStkrRel;
    public static Bitmap withoutWatermark;
    TextFontAdapter adapter;
    RecyclerOverLayAdapter adaptorOverlay;
    RecyclerTextBgAdapter adaptorTxtBg;
    private RelativeLayout addSticker;
    private RelativeLayout addText;
    private SeekBar alphaSeekbar;
    private Animation animSlideDown;
    private Animation animSlideUp;
        ImageView backgroundBlur;
    LinearLayout bgShow;
    private Bitmap bitmap;
    ImageView btnColorBackgroundPic;
    ImageView btnEditControlBg;
    ImageView btnEditControlColor;
    ImageView btnEditControlShadowColor;
    ImageView btnImgBackground;
    ImageView btnImgCameraSticker;
    ImageView btnRedo;
    ImageView btnShadowBottom;
    ImageView btnShadowLeft;
    ImageView btnShadowRight;
    private ImageView btnShadowTabChange;
    ImageView btnShadowTop;
    ImageView btnTakePicture;
    ImageView btnUndo;
    ImageButton btnUpDown;
    ImageButton btnUpDown1;
    private ImageButton btn_bck1;
    int catId;
    private RelativeLayout centerRelative;
    boolean checkMemory;
    LinearLayout colorShow;
    String colorType;
    LinearLayout controlsShow;
    LinearLayout controlsShowStkr;
    ProgressDialog dialogIs;
    float distance;
    int distanceScroll;
    int dsfc;
    private File f;
    private String filename;
    private View focusedView;
    LinearLayout fontsCurve;
    LinearLayout fontsShow;
    LinearLayout fontsSpacing;
    ImageView guideline;
    String hex;

    private SeekBar hueSeekbar;
    ImageView imgOK;
    LinearLayout layBackground;
    RelativeLayout layColor;
    LinearLayout layColorOacity;
    RelativeLayout layColorOpacity;
    RelativeLayout layControlStkr;
    LinearLayout layDuplicateStkr;
    ImageView layDuplicateText;
    ImageView layEdit;
    private LinearLayout layEffects;
    RelativeLayout layFilter;
    private LinearLayout layFontsSpacing;
    RelativeLayout layHandletails;
    RelativeLayout layHue;
    private RelativeLayout layRemove;
    ScrollView layScroll;
    LinearLayout laySticker;
    RelativeLayout layStkrMain;
    private LinearLayout layTextEdit;
    RelativeLayout layTextMain;
    private LinearLayout layoutEffectView;
    private LinearLayout layoutFilterView;
    private RelativeLayout layoutShadow1;
    private RelativeLayout layoutShadow2;
    ListFragment listFragment;
    private LinearLayout llLogo;
    private Handler mHandler;

    private Runnable mStatusChecker;
    private RelativeLayout mainRelative;
    int myDesignFlag;
    private FrameLayout nativeAdContainer;
    int overlayBlur;
    int overlayOpacty;
    float parentY;

    int posId;
    String position;
    int postId;
    private PreferenceClass preferenceClass;
    private int processs;
    String profile;
    ProgressBar progressBarUndo;
    String ratio;
    RelativeLayout rellative;
    LinearLayout sadowShow;
    float screenHeight;
    float screenWidth;
    private SeekBar seek;
    private SeekBar seekBar3;
    private SeekBar seekBarShadow;
    private SeekBar seekBlur;
    private SeekBar seekLetterSpacing;
    private SeekBar seekLineSpacing;
    private SeekBar seekShadowBlur;
    private SeekBar seekTextCurve;
    private LinearLayout seekbarContainer;
    private LinearLayout seekbarHandle;
    private RelativeLayout selectBackgnd;
    private RelativeLayout selectEffect;

    RelativeLayout shapeRelative;
    int templateId;
      ImageView transImgage;
    private Typeface ttf;
    private Typeface ttfHeader;
    TextView txtBG;
    private TextView txtBgControl;
    private TextView txtColorOpacity;
    private TextView txtColorsControl;
    private TextView txtControlText;
    TextView txtEffect;
    private TextView txtEffectText;
    private TextView txtFilterText;
    private TextView txtFontsControl;
    private TextView txtFontsCurve;
    private TextView txtFontsSpacing;
    private TextView txtFontsStyle;
    TextView txtImage;
    private TextView txtShadowControl;
    HashMap<Integer, Object> txtShapeList;
    TextView txtSticker;
    TextView txtText;
    private TextView txtTextControls;
    private RelativeLayout userImage;
    FrameLayout viewAllFrame;
    boolean OneShow = true;
    int alpha = 80;
    private int bColor = Color.parseColor("#4149b6");
    int backgroundCategory = 0;
    int backgroundOrientation = 2;
    int bgAlpha = 0;
    int bgColor = ViewCompat.MEASURED_STATE_MASK;
    String bgDrawable = "0";
    private boolean checkTouchContinue = false;
    private int curTileId = 0;
    boolean dialogShow = true;
    private boolean editMode = false;
    ArrayList<ElementInfo> elementInfosU_R = new ArrayList<>();
    private View focusedCopy = null;
    String fontName = "";
    String frameName = "";
    private float hr = 1.0f;
    private boolean isBackground = false;
    private boolean isRewarded = false;
    int leftRightShadow = 0;
    private float letterSpacing = 0.0f;
    private float lineSpacing = 0.0f;
    private int mInterval = 50;
    private int min = 0;
    BitmapFactory.Options options = new BitmapFactory.Options();
    String overlayName = "";
    String[] pallete = {"#ffffff", "#cccccc", "#999999", "#666666", "#333333", "#000000", "#ffee90", "#ffd700", "#daa520", "#b8860b", "#ccff66", "#adff2f", "#00fa9a", "#00ff7f", "#00ff00", "#32cd32", "#3cb371", "#99cccc", "#66cccc", "#339999", "#669999", "#006666", "#336666", "#ffcccc", "#ff9999", "#ff6666", "#ff3333", "#ff0033", "#cc0033"};
    float rotation = 0.0f;
    private int seekValue = 90;
    int shadowColor = ViewCompat.MEASURED_STATE_MASK;
    private int shadowFlag = 0;
    int shadowProg = 0;
    boolean showtailsSeek = false;
    int sizeFull = 0;
    ArrayList<Sticker_info> stickerInfoArrayList = new ArrayList<>();
    int stkrColorSet = Color.parseColor("#ffffff");
    int tAlpha = 100;
    int tColor = -1;
    int tempID = PointerIconCompat.TYPE_CONTEXT_MENU;
    String tempPath = "";
    String tempType = " ";
    private List<TemplateInfo> templateList = new ArrayList();
    ArrayList<TemplateInfo> templateListR_U = new ArrayList<>();
    ArrayList<TemplateInfo> templateListU_R = new ArrayList<>();
    int textColorSet = Color.parseColor("#ffffff");
    ArrayList<Text_info> textInfoArrayList = new ArrayList<>();
    ArrayList<TextInfo> textInfosU_R = new ArrayList<>();
    int topBottomShadow = 0;
    SeekBar verticalSeekBar = null;
    private float wr = 1.0f;
    float yAtLayoutCenter = -1.0f;

    private void addTilesBG(int i) {
    }

    private float getnewHeight(int i, int i2, float f, float f2) {
        return (i2 * f) / i;
    }

    private float getnewWidth(int i, int i2, float f, float f2) {
        return (i * f2) / i2;
    }

    private void removeScroll() {
    }

    @Override

    public void onEdit(View view, Uri uri) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void stickerScrollView(View view) {
    }

    public void textScrollView(View view) {
    }


    public float getXpos(float f) {
        return (this.mainRelative.getWidth() * f) / 100.0f;
    }


    public float getYpos(float f) {
        return (this.mainRelative.getHeight() * f) / 100.0f;
    }


    public int getNewWidht(float f, float f2) {
        return (int) ((this.mainRelative.getWidth() * (f2 - f)) / 100.0f);
    }


    public int getNewHeight(float f, float f2) {
        return (int) ((this.mainRelative.getHeight() * (f2 - f)) / 100.0f);
    }



    public int getNewHeightText(float f, float f2)
    {
        float height = (this.mainRelative.getHeight() * (f2 - f)) / 100.0f;
        return (int) (((int) height) + (height / 2.0f));
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);

        setContentView(R.layout.activity_create_card);
        PreferenceClass preferenceClass = new PreferenceClass(getApplicationContext());
        this.preferenceClass = preferenceClass;





        AndroidNetworking.initialize(getApplicationContext());
        if (Build.VERSION.SDK_INT < 8 && Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT >= 21) {
            Explode explode = new Explode();
            explode.setDuration(400L);
            getWindow().setEnterTransition(explode);
            getWindow().setExitTransition(explode);
        }
        findView();
        intilization();
        context = this;
        activity = this;
        this.options.inScaled = false;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels - ImageUtils.dpToPx((Context) this, 104);
        this.ttfHeader = AllConstants.getHeaderTypeface(this);
        ((TextView) findViewById(R.id.txtheader)).setTypeface(setBoldFont());
        this.myDesignFlag = getIntent().getIntExtra("catId", 0);
        this.catId = getIntent().getIntExtra("catId", 0);
        this.postId = getIntent().getIntExtra("pos_id", 0);
        if (this.myDesignFlag != 0) {


            ArrayList parcelableArrayListExtra = getIntent().getParcelableArrayListExtra("template");

            this.textInfoArrayList = getIntent().getParcelableArrayListExtra("text");
            this.stickerInfoArrayList = getIntent().getParcelableArrayListExtra("sticker");


            this.profile = getIntent().getStringExtra("profile");
            this.tempPath = ((PosterCo) parcelableArrayListExtra.get(0)).getBack_image();
            PosterCo posterCo = (PosterCo) parcelableArrayListExtra.get(0);
            this.postId = Integer.parseInt(posterCo.getPost_id());
            this.templateId = Integer.parseInt(posterCo.getPost_id());
            this.frameName = posterCo.getBack_image();
            this.ratio = posterCo.getRatio();
            ProgressDialog progressDialog = new ProgressDialog(this);
            this.dialogIs = progressDialog;
            progressDialog.setMessage(getResources().getString(R.string.plzwait));
            this.dialogIs.setCancelable(false);
            this.dialogIs.show();
            drawBackgroundImageFromDp(this.ratio, this.position, this.profile, "created");
        } else if (getIntent().getBooleanExtra("loadUserFrame", false)) {
            Bundle extras = getIntent().getExtras();
            if (!extras.getString("ratio").equals("cropImg")) {
                this.ratio = extras.getString("ratio");
                this.position = extras.getString("position");
                this.profile = extras.getString("profile");
                this.hex = extras.getString("hex");
                drawBackgroundImage(this.ratio, this.position, this.profile, "nonCreated");
            } else if (extras.getString("ratio").equals("cropImg")) {


                this.ratio = "";
                this.position = "1";
                this.profile = "Temp_Path";
                this.hex = "";
                setImageBitmapAndResizeLayout(ImageUtils.resizeBitmap(AllConstants.bitmap, (int) this.screenWidth, (int) this.screenHeight), "nonCreated");


            }
        } else {
            this.tempType = getIntent().getExtras().getString("Temp_Type");
            DatabaseHandler dbHandler = DatabaseHandler.getDbHandler(getApplicationContext());
            if (this.tempType.equals("MY_TEMP")) {
                this.templateList = dbHandler.getTemplateListDes("USER");
            } else if (this.tempType.equals("FREE_TEMP")) {
                this.templateList = dbHandler.getTemplateList("FREESTYLE");
            } else if (this.tempType.equals("FRIDAY_TEMP")) {
                this.templateList = dbHandler.getTemplateList("FRIDAY");
            } else if (this.tempType.equals("SALE_TEMP")) {
                this.templateList = dbHandler.getTemplateList("SALES");
            } else if (this.tempType.equals("SPORT_TEMP")) {
                this.templateList = dbHandler.getTemplateList("SPORTS");
            }
            dbHandler.close();
            final int intExtra = getIntent().getIntExtra("position", 0);
            this.centerRelative.post(new Runnable() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.1
                @Override // java.lang.Runnable
                public void run() {
                    LordTemplateAsync lordTemplateAsync = new LordTemplateAsync();
                    lordTemplateAsync.execute("" + intExtra);
                }
            });
        }
        int length = this.pallete.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = Color.parseColor(this.pallete[i]);
        }
//        this.horizontalPicker.setColors(iArr);
//        this.horizontalPickerColor.setColors(iArr);
//        this.shadowPickerColor.setColors(iArr);
//        this.pickerBg.setColors(iArr);
//        this.horizontalPicker.setSelectedColor(this.textColorSet);
//        this.horizontalPickerColor.setSelectedColor(this.stkrColorSet);
//        this.shadowPickerColor.setSelectedColor(iArr[5]);
//        this.pickerBg.setSelectedColor(iArr[5]);
        int color = Color.RED;
        int color2 = Color.BLACK;
        int color3 = Color.BLUE;
        int color4 = Color.GREEN;
        updateColor(color);
        updateColor(color2);
        updateShadow(color3);
        updateBgColor(color4);




        /*

        this.horizontalPickerColor.setOnColorChangedListener(new OnColorChangedListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.2
            @Override // uz.shift.colorpicker.OnColorChangedListener
            public void onColorChanged(int i2) {
                CreateCardActivity.this.updateColor(i2);
            }
        });
        this.horizontalPicker.setOnColorChangedListener(new OnColorChangedListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.3
            @Override // uz.shift.colorpicker.OnColorChangedListener
            public void onColorChanged(int i2) {
                CreateCardActivity.this.updateColor(i2);
            }
        });
        this.shadowPickerColor.setOnColorChangedListener(new OnColorChangedListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.4
            @Override // uz.shift.colorpicker.OnColorChangedListener
            public void onColorChanged(int i2) {
                CreateCardActivity.this.updateShadow(i2);
            }
        });
        this.pickerBg.setOnColorChangedListener(new OnColorChangedListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.5
            @Override // uz.shift.colorpicker.OnColorChangedListener
            public void onColorChanged(int i2) {
                CreateCardActivity.this.updateBgColor(i2);
            }
        });

        */


        this.viewAllFrame = (FrameLayout) findViewById(R.id.viewall_layout);
        this.guideline = (ImageView) findViewById(R.id.guidelines);
        this.rellative = (RelativeLayout) findViewById(R.id.rellative);
        ScrollView scrollView = (ScrollView) findViewById(R.id.lay_scroll);
        this.layScroll = scrollView;
        scrollView.setOnTouchListener(new View.OnTouchListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.6
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(13);
        this.layScroll.setLayoutParams(layoutParams);
        this.layScroll.postInvalidate();
        this.layScroll.requestLayout();
        this.progressBarUndo = (ProgressBar) findViewById(R.id.progress_undo);
        findViewById(R.id.btnLeft).setOnTouchListener(new RepeatListener(200, 100, this.guideline, new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CreateCardActivity.this.updatePositionSticker("decX");
            }
        }));
        ((ImageView) findViewById(R.id.btnUp)).setOnTouchListener(new RepeatListener(200, 100, this.guideline, new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CreateCardActivity.this.updatePositionSticker("incrX");
            }
        }));
        ((ImageView) findViewById(R.id.btnRight)).setOnTouchListener(new RepeatListener(200, 100, this.guideline, new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CreateCardActivity.this.updatePositionSticker("decY");
            }
        }));
        ((ImageView) findViewById(R.id.btnDown)).setOnTouchListener(new RepeatListener(200, 100, this.guideline, new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CreateCardActivity.this.updatePositionSticker("incrY");
            }
        }));
        ((ImageButton) findViewById(R.id.btnLeftS)).setOnTouchListener(new RepeatListener(200, 100, this.guideline, new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CreateCardActivity.this.updatePositionSticker("decX");
            }
        }));
        ((ImageButton) findViewById(R.id.btnRightS)).setOnTouchListener(new RepeatListener(200, 100, this.guideline, new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CreateCardActivity.this.updatePositionSticker("incrX");
            }
        }));
        ((ImageButton) findViewById(R.id.btnUpS)).setOnTouchListener(new RepeatListener(200, 100, this.guideline, new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CreateCardActivity.this.updatePositionSticker("decY");
            }
        }));
        ((ImageButton) findViewById(R.id.btnDownS)).setOnTouchListener(new RepeatListener(200, 100, this.guideline, new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CreateCardActivity.this.updatePositionSticker("incrY");
            }
        }));
        this.mHandler = new Handler();
        this.mStatusChecker = new Runnable() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.15
            @Override // java.lang.Runnable
            public void run() {
                if (CreateCardActivity.this.layScroll != null) {
                    CreateCardActivity createCardActivity = CreateCardActivity.this;
                    createCardActivity.removeScrollViewPosition(createCardActivity.focusedView);
                }
                CreateCardActivity.this.mHandler.postDelayed(this, CreateCardActivity.this.mInterval);
            }
        };


    }


    public void removeScrollViewPosition(View view) {
        float f;
        int[] iArr = new int[2];
        this.layScroll.getLocationOnScreen(iArr);
        float f2 = iArr[1];
        float width = view.getWidth();
        float height = view.getHeight();
        float x = view.getX();
        float y = (view.getY() + f2) - this.distanceScroll;
        if (view instanceof StickerView) {
            f = view.getRotation();
        } else {
            f = view.getRotation();
        }
        Matrix matrix = new Matrix();
        RectF rectF = new RectF(x, y, x + width, y + height);
        matrix.postRotate(f, x + (width / 2.0f), (height / 2.0f) + y);
        matrix.mapRect(rectF);
        float min = Math.min(rectF.top, rectF.bottom);
        if (f2 > min) {
            float f3 = (int) (f2 - min);
            try {
                float scrollY = this.layScroll.getScrollY();
                if (scrollY > 0.0f) {
                    float f4 = scrollY - (((int) f3) / 4);
                    if (f4 >= 0.0f) {
                        this.layScroll.smoothScrollTo(0, (int) f4);
                        this.layScroll.getLayoutParams().height = (int) (this.layScroll.getHeight() + (y / 4.0f));
                        this.layScroll.postInvalidate();
                        this.layScroll.requestLayout();
                        return;
                    }
                    this.distanceScroll = 0;
                    this.layScroll.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
                    this.layScroll.postInvalidate();
                    this.layScroll.requestLayout();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void findView() {
        this.txtTextControls = (TextView) findViewById(R.id.txt_text_controls);
        this.txtFontsControl = (TextView) findViewById(R.id.txt_fonts_control);
        this.txtFontsStyle = (TextView) findViewById(R.id.txt_fonts_Style);
        this.layFontsSpacing = (LinearLayout) findViewById(R.id.lay_fonts_Spacing);
        this.txtFontsSpacing = (TextView) findViewById(R.id.txt_fonts_Spacing);
        this.txtFontsCurve = (TextView) findViewById(R.id.txt_fonts_curve);
        this.txtColorsControl = (TextView) findViewById(R.id.txt_colors_control);
        this.txtShadowControl = (TextView) findViewById(R.id.txt_shadow_control);
        this.txtBgControl = (TextView) findViewById(R.id.txt_bg_control);
        this.btnEditControlColor = (ImageView) findViewById(R.id.btnEditControlColor);
        this.btnEditControlShadowColor = (ImageView) findViewById(R.id.btnEditControlShadowColor);
        this.btnEditControlBg = (ImageView) findViewById(R.id.btnEditControlBg);
        this.btnShadowLeft = (ImageView) findViewById(R.id.btnShadowLeft);
        this.btnShadowRight = (ImageView) findViewById(R.id.btnShadowRight);
        this.btnShadowTop = (ImageView) findViewById(R.id.btnShadowTop);
        this.btnShadowBottom = (ImageView) findViewById(R.id.btnShadowBottom);
        this.txtEffectText = (TextView) findViewById(R.id.txtEffectText);
        this.txtFilterText = (TextView) findViewById(R.id.txtFilterText);
        this.layoutEffectView = (LinearLayout) findViewById(R.id.layoutEffectView);
        this.layoutFilterView = (LinearLayout) findViewById(R.id.layoutFilterView);
        this.btnShadowTabChange = (ImageView) findViewById(R.id.btnShadowTabChange);
        this.layoutShadow1 = (RelativeLayout) findViewById(R.id.layoutShadow1);
        this.layoutShadow2 = (RelativeLayout) findViewById(R.id.layoutShadow2);
        this.txtText = (TextView) findViewById(R.id.bt_text);
        this.txtSticker = (TextView) findViewById(R.id.bt_sticker);
        this.txtImage = (TextView) findViewById(R.id.bt_image);
        this.txtEffect = (TextView) findViewById(R.id.bt_effect);
        this.txtBG = (TextView) findViewById(R.id.bt_bg);
        this.btnShadowTabChange.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.19
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CreateCardActivity.this.shadowFlag == 0) {
                    CreateCardActivity.this.shadowFlag = 1;
                    CreateCardActivity.this.layoutShadow2.setVisibility(View.VISIBLE);
                    CreateCardActivity.this.layoutShadow1.setVisibility(View.GONE);
                } else if (CreateCardActivity.this.shadowFlag == 1) {
                    CreateCardActivity.this.shadowFlag = 0;
                    CreateCardActivity.this.layoutShadow1.setVisibility(View.VISIBLE);
                    CreateCardActivity.this.layoutShadow2.setVisibility(View.GONE);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void drawBackgroundImageFromDp(String str, String str2, String str3, String str4) {
        this.laySticker.setVisibility(View.GONE);
        File file = new File(this.tempPath);
        if (file.exists()) {
            try {
                Uri parse = Uri.parse(this.tempPath);
                float f = this.screenWidth;
                float f2 = this.screenHeight;
                if (f <= f2) {
                    f = f2;
                }
                bitmapRatio(str, str3, ImageUtils.getResampleImageBitmap(parse, this, (int) f), str4);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (!str.equals("")) {
            new SavebackgrundAsync().execute(file.getName().replace(".png", ""), str, str3, str4);
        } else if (this.OneShow) {
            errorDialogTempInfo();
            this.OneShow = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void drawBackgroundImage(final String str, String str2, String str3, final String str4) {
        this.laySticker.setVisibility(View.GONE);
        if (new File(this.profile).exists()) {
            try {
                Uri parse = Uri.parse(this.profile);
                float f = this.screenWidth;
                float f2 = this.screenHeight;
                if (f <= f2) {
                    f = f2;
                }
                bitmapRatio(str, str3, ImageUtils.getResampleImageBitmap(parse, this, (int) f), str4);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {


//            try {
//                Glide.with(getApplicationContext()).asBitmap().apply((BaseRequestOptions<?>) new RequestOptions().skipMemoryCache(true)).load(str3).
//                        into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>() {
//
//
//                    @Override
//                    public  void onResourceReady(Object obj, Transition transition) {
//                        onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
//                    }
//
//                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
//                        CreateCardActivity.imgBtmap = bitmap;
//                        CreateCardActivity.this.bitmapRatio(str, "Background", bitmap, str4);
//                    }
//
//
//                });
//
//
//            } catch (NullPointerException e2) {
//                e2.printStackTrace();
//                Glide.with(getApplicationContext()).asBitmap().apply((BaseRequestOptions<?>) new RequestOptions().skipMemoryCache(true)).load(str3).into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.21
//                    @Override // com.bumptech.glide.request.target.Target
//                    public  void onResourceReady(Object obj, Transition transition) {
//                        onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
//                    }
//
//                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
//                        CreateCardActivity.this.bitmapRatio(str, "Background", bitmap, str4);
//                    }
//                });
//            }
//


        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bitmapRatio(String str, String str2, Bitmap bitmap, String str3) {
        if (!str.equals("")) {
            if (str.equals("1:1")) {
                bitmap = cropInRatio(bitmap, 1, 1);
            } else if (str.equals("16:9")) {
                bitmap = cropInRatio(bitmap, 16, 9);
            } else if (str.equals("9:16")) {
                bitmap = cropInRatio(bitmap, 9, 16);
            } else if (str.equals("4:3")) {
                bitmap = cropInRatio(bitmap, 4, 3);
            } else if (str.equals("3:4")) {
                bitmap = cropInRatio(bitmap, 3, 4);
            } else {
                String[] split = str.split(":");
                bitmap = cropInRatio(bitmap, Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            }
        }
        Bitmap resizeBitmap = ImageUtils.resizeBitmap(bitmap, (int) this.screenWidth, (int) this.screenHeight);
        if (str3.equals("created")) {
            setImageBitmapAndResizeLayout(resizeBitmap, "created");
        } else {
            setImageBitmapAndResizeLayout(resizeBitmap, "nonCreated");
        }
    }

    public Bitmap cropInRatio(Bitmap bitmap, int i, int i2) {
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        float f = getnewHeight(i, i2, width, height);
        float f2 = getnewWidth(i, i2, width, height);
        Bitmap createBitmap = (f2 > width || f2 >= width) ? null : Bitmap.createBitmap(bitmap, (int) ((width - f2) / 2.0f), 0, (int) f2, (int) height);
        if (f <= height && f < height) {
            createBitmap = Bitmap.createBitmap(bitmap, 0, (int) ((height - f) / 2.0f), (int) width, (int) f);
        }
        return (f2 == width && f == height) ? bitmap : createBitmap;
    }

    private void setImageBitmapAndResizeLayout(Bitmap bitmap, String str) {
        this.mainRelative.getLayoutParams().width = bitmap.getWidth();
        this.mainRelative.getLayoutParams().height = bitmap.getHeight();
        this.mainRelative.postInvalidate();
        this.mainRelative.requestLayout();
       backgroundImage.setImageBitmap(bitmap);
        imgBtmap = bitmap;
        this.mainRelative.post(new Runnable() {
            @Override // java.lang.Runnable
            public void run() {
                if (CreateCardActivity.this.mainRelative != null && CreateCardActivity.this.mainRelative.getWidth() > 0 && CreateCardActivity.this.mainRelative.getHeight() > 0) {
                    CreateCardActivity.this.guideline.setImageBitmap(AllConstants.guidelines_bitmap(CreateCardActivity.activity, CreateCardActivity.this.mainRelative.getWidth(), CreateCardActivity.this.mainRelative.getHeight()));
                }
                CreateCardActivity.this.layScroll.post(new Runnable() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.22.1
                    @Override // java.lang.Runnable
                    public void run() {
                        int[] iArr = new int[2];
                        CreateCardActivity.this.layScroll.getLocationOnScreen(iArr);
                        CreateCardActivity.this.parentY = iArr[1];
                        CreateCardActivity.this.yAtLayoutCenter = CreateCardActivity.this.parentY;
                    }
                });
            }
        });
      this.backgroundBlur.setVisibility(View.GONE);
        if (str.equals("created")) {
            new BlurOperationTwoAsync(this).execute("");
        } else {
            new BlurOperationAsync(this).execute("");
        }
    }

    private void intilization() {
        this.ttf = AllConstants.getTextTypeface(this);
        ImageButton imageButton = (ImageButton) findViewById(R.id.btn_bck1);
        this.btn_bck1 = imageButton;
        imageButton.setOnClickListener(this);
        layContainer = (FrameLayout) findViewById(R.id.lay_container);
        this.centerRelative = (RelativeLayout) findViewById(R.id.center_rel);
        this.btnImgCameraSticker = (ImageView) findViewById(R.id.btnImgCameraSticker);
        this.btnImgBackground = (ImageView) findViewById(R.id.btnImgBackground);
        this.btnTakePicture = (ImageView) findViewById(R.id.btnTakePicture);
        this.btnColorBackgroundPic = (ImageView) findViewById(R.id.btnColorBackgroundPic);
        this.layRemove = (RelativeLayout) findViewById(R.id.lay_remove);
        this.layTextMain = (RelativeLayout) findViewById(R.id.lay_TextMain);
        this.layStkrMain = (RelativeLayout) findViewById(R.id.lay_StkrMain);
        this.btnUpDown = (ImageButton) findViewById(R.id.btn_up_down);
        this.btnUpDown1 = (ImageButton) findViewById(R.id.btn_up_down1);
        this.mainRelative = (RelativeLayout) findViewById(R.id.main_rel);
        backgroundImage = (ImageView) findViewById(R.id.background_img);
       this.backgroundBlur = (ImageView) findViewById(R.id.background_blur);
        txtStkrRel = (RelativeLayout) findViewById(R.id.txt_stkr_rel);
        this.userImage = (RelativeLayout) findViewById(R.id.select_artwork);
        this.selectBackgnd = (RelativeLayout) findViewById(R.id.select_backgnd);
        this.selectEffect = (RelativeLayout) findViewById(R.id.select_effect);
        this.addSticker = (RelativeLayout) findViewById(R.id.add_sticker);
        this.addText = (RelativeLayout) findViewById(R.id.add_text);
        this.layEffects = (LinearLayout) findViewById(R.id.lay_effects);
        this.laySticker = (LinearLayout) findViewById(R.id.lay_sticker);
        this.layBackground = (LinearLayout) findViewById(R.id.lay_background);
        this.layHandletails = (RelativeLayout) findViewById(R.id.lay_handletails);
        this.seekbarContainer = (LinearLayout) findViewById(R.id.seekbar_container);
        this.seekbarHandle = (LinearLayout) findViewById(R.id.seekbar_handle);
        this.shapeRelative = (RelativeLayout) findViewById(R.id.shape_rel);
        seekTailys = (SeekBar) findViewById(R.id.seek_tailys);
        this.alphaSeekbar = (SeekBar) findViewById(R.id.alpha_seekBar);
        this.seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        this.seekBarShadow = (SeekBar) findViewById(R.id.seekBar_shadow);
        this.seekTextCurve = (SeekBar) findViewById(R.id.seekTextCurve);
        this.hueSeekbar = (SeekBar) findViewById(R.id.hue_seekBar);
        this.seekShadowBlur = (SeekBar) findViewById(R.id.seekShadowBlur);
         this.transImgage = (ImageView) findViewById(R.id.trans_img);
        this.alphaSeekbar.setOnSeekBarChangeListener(this);
        this.seekBar3.setOnSeekBarChangeListener(this);
        this.seekBarShadow.setOnSeekBarChangeListener(this);
        this.hueSeekbar.setOnSeekBarChangeListener(this);
        seekTailys.setOnSeekBarChangeListener(this);
        this.seek = (SeekBar) findViewById(R.id.seek);
        this.layFilter = (RelativeLayout) findViewById(R.id.lay_filter);
        this.layDuplicateText = (ImageView) findViewById(R.id.lay_dupliText);
        this.layDuplicateStkr = (LinearLayout) findViewById(R.id.lay_dupliStkr);
        this.layEdit = (ImageView) findViewById(R.id.lay_edit);
        this.layDuplicateText.setOnClickListener(this);
        this.layDuplicateStkr.setOnClickListener(this);
        this.layEdit.setOnClickListener(this);
        this.seekBlur = (SeekBar) findViewById(R.id.seek_blur);
        this.llLogo = (LinearLayout) findViewById(R.id.logo_ll);
        this.imgOK = (ImageView) findViewById(R.id.btn_done);
        btnLayControls = (ImageView) findViewById(R.id.btn_layControls);
        this.layTextEdit = (LinearLayout) findViewById(R.id.lay_textEdit);
        this.verticalSeekBar = (SeekBar) findViewById(R.id.seekBar2);


//
//        this.horizontalPicker = (LineColorPicker) findViewById(R.id.picker);
//        this.horizontalPickerColor = (LineColorPicker) findViewById(R.id.picker1);
//        this.shadowPickerColor = (LineColorPicker) findViewById(R.id.pickerShadow);
//        this.pickerBg = (LineColorPicker) findViewById(R.id.pickerBg);
//


        this.layColor = (RelativeLayout) findViewById(R.id.lay_color);
        this.layHue = (RelativeLayout) findViewById(R.id.lay_hue);
        this.txtControlText = (TextView) findViewById(R.id.txtControlText);
        this.txtColorOpacity = (TextView) findViewById(R.id.txtColorOpacity);
        this.seekLetterSpacing = (SeekBar) findViewById(R.id.seekLetterSpacing);
        this.seekLineSpacing = (SeekBar) findViewById(R.id.seekLineSpacing);
          this.transImgage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.o1));
        this.hueSeekbar.setProgress(1);
        this.seek.setMax(255);
        this.seek.setProgress(80);
        this.seekBlur.setMax(255);
        this.seekBarShadow.setProgress(0);
        this.seekBar3.setProgress(255);
        this.seekBlur.setProgress(this.min);


        if (Build.VERSION.SDK_INT >= 16) {
            this.transImgage.setImageAlpha(this.alpha);
        } else {
            this.transImgage.setAlpha(this.alpha);
        }


        seekTailys.setMax(290);
        seekTailys.setProgress(90);
        this.seek.setOnSeekBarChangeListener(this);
        this.seekBlur.setOnSeekBarChangeListener(this);
        this.imgOK.setOnClickListener(this);
        btnLayControls.setOnClickListener(this);
        this.userImage.setOnClickListener(this);
        this.selectBackgnd.setOnClickListener(this);
        this.selectEffect.setOnClickListener(this);
        this.addSticker.setOnClickListener(this);
        this.addText.setOnClickListener(this);
        this.layRemove.setOnClickListener(this);
        this.centerRelative.setOnClickListener(this);
        this.animSlideUp = AllConstants.getAnimUp(this);
        this.animSlideDown = AllConstants.getAnimDown(this);
        this.verticalSeekBar.setOnSeekBarChangeListener(this);
        this.btnImgCameraSticker.setOnClickListener(this);
        this.btnImgBackground.setOnClickListener(this);
        this.btnColorBackgroundPic.setOnClickListener(this);
        this.btnTakePicture.setOnClickListener(this);
        initOverlayRecycler();
        StickerCategoryVertical();
        BackgroundCategoryVertical(this.backgroundOrientation, this.backgroundCategory);
        fackClick();
        this.seekLetterSpacing.setOnSeekBarChangeListener(this);
        this.seekLineSpacing.setOnSeekBarChangeListener(this);
        this.seekTextCurve.setOnSeekBarChangeListener(this);
        this.seekShadowBlur.setOnSeekBarChangeListener(this);
        this.fontsShow = (LinearLayout) findViewById(R.id.fontsShow);
        this.fontsSpacing = (LinearLayout) findViewById(R.id.fontsSpacing);
        this.fontsCurve = (LinearLayout) findViewById(R.id.fontsCurve);
        this.colorShow = (LinearLayout) findViewById(R.id.colorShow);
        this.sadowShow = (LinearLayout) findViewById(R.id.sadowShow);
        this.bgShow = (LinearLayout) findViewById(R.id.bgShow);
        this.controlsShow = (LinearLayout) findViewById(R.id.controlsShow);

        TextFontAdapter textFontAdapter =
                new TextFontAdapter(this, getResources().getStringArray(R.array.fonts_array));

        this.adapter = textFontAdapter;
        textFontAdapter.setSelected(0);
        ((GridView) findViewById(R.id.font_gridview)).setAdapter((ListAdapter) this.adapter);
        this.adapter.setItemClickCallback(new OnClickCallback() {
                                              @Override
                                              public void onClickCallBack(ArrayList<BackgroundImage> backgroundImages, String str, Context context, String s) {

                                                  CreateCardActivity.this.setTextFonts(str);
                                                  CreateCardActivity.this.adapter.setSelected(Integer.parseInt(str));
                                              }
                                          }


  /*              new OnClickCallback<ArrayList<String>, Integer, String, Activity>() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.23
            public void onClickCallBack(ArrayList<String> arrayList, Integer num, String str, Activity activity2) {
                CreateCardActivity.this.setTextFonts(str);
                CreateCardActivity.this.adapter.setSelected(num.intValue());
            }
        }*/


        );
        this.adaptorTxtBg = new RecyclerTextBgAdapter(this, AllConstants.imageId);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.txtBg_recylr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(this.adaptorTxtBg);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int i) {
                CreateCardActivity createCardActivity = CreateCardActivity.this;
                createCardActivity.setTextBgTexture("btxt" + i);
            }
        }));
        this.layColorOpacity = (RelativeLayout) findViewById(R.id.lay_colorOpacity);
        this.layControlStkr = (RelativeLayout) findViewById(R.id.lay_controlStkr);
        this.layColorOacity = (LinearLayout) findViewById(R.id.lay_colorOacity);
        this.controlsShowStkr = (LinearLayout) findViewById(R.id.controlsShowStkr);
        this.layColorOpacity.setOnClickListener(this);
        this.layControlStkr.setOnClickListener(this);
        this.btnUndo = (ImageView) findViewById(R.id.btn_undo);
        this.btnRedo = (ImageView) findViewById(R.id.btn_redo);
        this.btnUndo.setOnClickListener(this);
        this.btnRedo.setOnClickListener(this);
        showFragment();
    }

    private void StickerCategoryVertical() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        StickerFragment stickerFragment = (StickerFragment) supportFragmentManager.findFragmentByTag("sticker_main");
        if (stickerFragment != null) {
            beginTransaction.remove(stickerFragment);
        }
        beginTransaction.add(R.id.frameContainerSticker, StickerFragment.newInstance(), "sticker_main");
        try {
            beginTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void BackgroundCategoryVertical(int i, int i2) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        BackgroundFragment backgroundFragment = (BackgroundFragment) supportFragmentManager.findFragmentByTag("inback_category_frgm");
        if (backgroundFragment != null) {
            beginTransaction.remove(backgroundFragment);
        }
        beginTransaction.add(R.id.frameContainerBackground, BackgroundFragment.newInstance(), "inback_category_frgm");
        try {
            beginTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initOverlayRecycler() {
        this.adaptorOverlay = new RecyclerOverLayAdapter(this, AllConstants.overlayArr);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.overlay_recylr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(this.adaptorOverlay);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.25
            @Override
            // com.invitationmaker.lss.photomaker.adapter.RecyclerItemClickListener.OnItemClickListener
            public void onItemClick(View view, int i) {
                CreateCardActivity createCardActivity = CreateCardActivity.this;
                createCardActivity.overlayName = "o" + (i + 1);
                CreateCardActivity createCardActivity2 = CreateCardActivity.this;
                createCardActivity2.setBitmapOverlay(createCardActivity2.getResources().getIdentifier(CreateCardActivity.this.overlayName, "drawable", CreateCardActivity.this.getPackageName()));
            }
        }));
    }

    private void showFragment() {
        this.listFragment = new ListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.lay_container, this.listFragment, "fragment").commit();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_sticker /* 2131296341 */:
                removeScroll();
                removeImageViewControll();
                hideSlideBar();
                if (this.seekbarContainer.getVisibility() == View.VISIBLE) {
                    this.seekbarContainer.startAnimation(this.animSlideDown);
                    this.seekbarContainer.setVisibility(View.GONE);
                    this.laySticker.setVisibility(View.GONE);
                }
                if (this.laySticker.getVisibility() == View.GONE) {
                    this.laySticker.setVisibility(View.VISIBLE);
                }
                this.layEffects.setVisibility(View.GONE);
                this.layStkrMain.setVisibility(View.GONE);
                this.layBackground.setVisibility(View.GONE);
                this.layTextMain.setVisibility(View.GONE);
                addBotton();
                this.txtSticker.setTextColor(getResources().getColor(R.color.color_add_btn));
                return;
            case R.id.add_text /* 2131296342 */:
                removeScroll();
                removeImageViewControll();
                hideSlideBar();
                if (this.seekbarContainer.getVisibility() == View.VISIBLE) {
                    this.seekbarContainer.startAnimation(this.animSlideDown);
                    this.seekbarContainer.setVisibility(View.GONE);
                }
                this.layEffects.setVisibility(View.GONE);
                this.layStkrMain.setVisibility(View.GONE);
                this.layBackground.setVisibility(View.GONE);
                this.layTextMain.setVisibility(View.GONE);
                this.laySticker.setVisibility(View.GONE);
                addBotton();
                this.txtText.setTextColor(getResources().getColor(R.color.color_add_btn));
                addTextDialog(null);
                return;
            case R.id.btnAlignMentFont:
                setLeftAlignMent();
                return;
            case R.id.btnBoldFont:
                setBoldFonts();
                return;
            case R.id.btnCapitalFont:
                setCapitalFont();
                return;
            case R.id.btnCenterFont:
                setCenterAlignMent();
                return;
            case R.id.btnColorBackgroundPic:
                colorPickerDialog(false);
                return;
            case R.id.btnEditControlBg:
                mainControlBgPickerDialog(false);
                return;
            case R.id.btnEditControlColor:
                mainControlcolorPickerDialog(false);
                return;
            case R.id.btnEditControlShadowColor:
                mainControlShadowPickerDialog(false);
                return;
            case R.id.btnImgBackground:
                requestGalleryImagePermission();
                return;
            case R.id.btnImgCameraSticker:
                requestGalleryPermission();
                return;
            case R.id.btnItalicFont:
                setItalicFont();
                return;
            case R.id.btnLayoutEffect:
                this.layoutFilterView.setVisibility(View.GONE);
                this.layoutEffectView.setVisibility(View.VISIBLE);
                this.txtEffectText.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
                this.txtFilterText.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
                return;
            case R.id.btnLayoutFilter /* 2131296438 */:
                this.layoutEffectView.setVisibility(View.GONE);
                this.layoutFilterView.setVisibility(View.VISIBLE);
                this.txtEffectText.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
                this.txtFilterText.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
                return;
            case R.id.btnRightFont:
                setRightAlignMent();
                return;
            case R.id.btnShadowBottom:
                setBottomShadow();
                return;
            case R.id.btnShadowLeft:
                setLeftShadow();
                return;
            case R.id.btnShadowRight:
                setRightShadow();
                return;
            case R.id.btnShadowTop:
                setTopShadow();
                return;
            case R.id.btnTakePicture:
                requestGalleryPermission();
                return;
            case R.id.btnUnderlineFont:
                setUnderLineFont();
                return;
            case R.id.btn_bck1:
                this.layScroll.smoothScrollTo(0, this.distanceScroll);
                return;
            case R.id.btn_bckprass:
                removeScroll();
                onBackPressed();
                return;
            case R.id.btn_done:
                saveImage();
                return;
            case R.id.btn_layControls:
                removeScroll();
                removeImageViewControll();
                if (this.layTextMain.getVisibility() == View.VISIBLE) {
                    this.layTextMain.startAnimation(this.animSlideDown);
                    this.layTextMain.setVisibility(View.GONE);
                }
                if (this.layStkrMain.getVisibility() == View.VISIBLE) {
                    this.layStkrMain.startAnimation(this.animSlideDown);
                    this.layStkrMain.setVisibility(View.GONE);
                }
                if (layContainer.getVisibility() == View.GONE) {
                    btnLayControls.setVisibility(View.GONE);
                    this.listFragment.getLayoutChild();
                    layContainer.setVisibility(View.VISIBLE);
                    layContainer.animate().translationX(layContainer.getLeft()).setDuration(200L).setInterpolator(new DecelerateInterpolator()).start();
                    return;
                }
                layContainer.setVisibility(View.VISIBLE);
                layContainer.animate().translationX(-layContainer.getRight()).setDuration(200L).setInterpolator(new AccelerateInterpolator()).start();
                new Handler().postDelayed(new Runnable() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.26
                    @Override // java.lang.Runnable
                    public void run() {
                        CreateCardActivity.layContainer.setVisibility(View.GONE);
                        CreateCardActivity.btnLayControls.setVisibility(View.VISIBLE);
                    }
                }, 200L);
                return;
            case R.id.btn_redo /* 2131296479 */:
                redo();
                return;
            case R.id.btn_undo /* 2131296481 */:
                undo();
                return;
            case R.id.btn_up_down /* 2131296482 */:
                this.focusedCopy = this.focusedView;
                removeScroll();
                this.layStkrMain.requestLayout();
                this.layStkrMain.postInvalidate();
                if (this.seekbarContainer.getVisibility() == View.VISIBLE) {
                    hideResContainer();
                    return;
                } else {
                    showResContainer();
                    return;
                }
            case R.id.btn_up_down1 /* 2131296483 */:
                this.focusedCopy = this.focusedView;
                removeScroll();
                this.layTextMain.requestLayout();
                this.layTextMain.postInvalidate();
                if (this.layTextEdit.getVisibility() == View.VISIBLE) {
                    hideTextResContainer();
                    return;
                } else {
                    showTextResContainer();
                    return;
                }
            case R.id.center_rel /* 2131296502 */:
            case R.id.lay_remove /* 2131296742 */:
                this.layEffects.setVisibility(View.GONE);
                this.layStkrMain.setVisibility(View.GONE);
                this.guideline.setVisibility(View.GONE);
                this.laySticker.setVisibility(View.GONE);
                this.layBackground.setVisibility(View.GONE);
                onTouchApply();
                return;
            case R.id.lay_backgnd_control /* 2131296716 */:
                this.fontsShow.setVisibility(View.GONE);
                this.fontsSpacing.setVisibility(View.GONE);
                this.fontsCurve.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.VISIBLE);
                this.controlsShow.setVisibility(View.GONE);
                selectControl8();
                return;
            case R.id.lay_colorOpacity /* 2131296721 */:
                this.layColorOacity.setVisibility(View.VISIBLE);
                this.controlsShowStkr.setVisibility(View.GONE);
                this.txtControlText.setTextColor(getResources().getColor(R.color.titlecolorbtn));
                this.txtColorOpacity.setTextColor(getResources().getColor(R.color.crop_selected_color));
                return;
            case R.id.lay_colors_control /* 2131296722 */:
                this.fontsShow.setVisibility(View.GONE);
                this.fontsSpacing.setVisibility(View.GONE);
                this.fontsCurve.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.VISIBLE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                selectControl6();
                return;
            case R.id.lay_controlStkr /* 2131296724 */:
                this.layColorOacity.setVisibility(View.GONE);
                this.controlsShowStkr.setVisibility(View.VISIBLE);
                this.txtControlText.setTextColor(getResources().getColor(R.color.crop_selected_color));
                this.txtColorOpacity.setTextColor(getResources().getColor(R.color.titlecolorbtn));
                return;
            case R.id.lay_controls_control /* 2131296725 */:
                this.fontsShow.setVisibility(View.GONE);
                this.fontsSpacing.setVisibility(View.GONE);
                this.fontsCurve.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.VISIBLE);
                selectControl1();
                return;
            case R.id.lay_dupliStkr /* 2131296727 */:
                int childCount = txtStkrRel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = txtStkrRel.getChildAt(i);
                    if (childAt instanceof StickerView) {
                        StickerView stickerView = (StickerView) childAt;
                        if (stickerView.getBorderVisbilty()) {
                            StickerView stickerView2 = new StickerView(this);
                            stickerView2.setComponentInfo(stickerView.getComponentInfo());
                            stickerView2.setId(ViewIdGenerator.generateViewId());
                            stickerView2.setMainLayoutWH(this.mainRelative.getWidth(), this.mainRelative.getHeight());
//                            txtStkrRel.addView(stickerView2);
                            removeImageViewControll();
                            stickerView2.setOnTouchCallbackListener(this);
                            stickerView2.setBorderVisibility(true);
                        }
                    }
                }
                return;
            case R.id.lay_dupliText /* 2131296728 */:
                int childCount2 = txtStkrRel.getChildCount();
                for (int i2 = 0; i2 < childCount2; i2++) {
                    View childAt2 = txtStkrRel.getChildAt(i2);
                    if (childAt2 instanceof AutofitTextRel) {
                        AutofitTextRel autofitTextRel = (AutofitTextRel) childAt2;
                        if (autofitTextRel.getBorderVisibility()) {
                            AutofitTextRel autofitTextRel2 = new AutofitTextRel((Context) this, false);
//                            txtStkrRel.addView(autofitTextRel2);
                            removeImageViewControll();
                            autofitTextRel2.setTextInfo(autofitTextRel.getTextInfo(), false);
                            autofitTextRel2.setId(ViewIdGenerator.generateViewId());
                            autofitTextRel2.setOnTouchCallbackListener(this);
                            autofitTextRel2.setBorderVisibility(true);
                        }
                    }
                }
                return;
            case R.id.lay_edit /* 2131296729 */:
                doubleTabPrass();
                return;
            case R.id.lay_fonts_Curve /* 2131296732 */:
                this.fontsSpacing.setVisibility(View.GONE);
                this.fontsCurve.setVisibility(View.VISIBLE);
                this.fontsShow.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                selectControl5();
                return;
            case R.id.lay_fonts_Spacing /* 2131296733 */:
                this.fontsSpacing.setVisibility(View.VISIBLE);
                this.fontsCurve.setVisibility(View.GONE);
                this.fontsShow.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                selectControl4();
                return;
            case R.id.lay_fonts_control /* 2131296734 */:
                this.fontsShow.setVisibility(View.VISIBLE);
                this.fontsSpacing.setVisibility(View.GONE);
                this.fontsCurve.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                selectControl2();
                return;
            case R.id.lay_fonts_style /* 2131296735 */:
                this.fontsSpacing.setVisibility(View.GONE);
                this.fontsCurve.setVisibility(View.GONE);
                this.fontsShow.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                selectControl3();
                return;
            case R.id.lay_shadow_control /* 2131296744 */:
                this.fontsShow.setVisibility(View.GONE);
                this.fontsSpacing.setVisibility(View.GONE);
                this.fontsCurve.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.VISIBLE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                selectControl7();
                return;
            case R.id.select_artwork /* 2131296970 */:
                removeScroll();
                removeImageViewControll();
                hideSlideBar();
                this.layEffects.setVisibility(View.GONE);
                this.layStkrMain.setVisibility(View.GONE);
                this.layBackground.setVisibility(View.GONE);
                this.layTextMain.setVisibility(View.GONE);
                this.laySticker.setVisibility(View.GONE);
                showPicImageDialog();
                addBotton();
                this.txtImage.setTextColor(getResources().getColor(R.color.color_add_btn));
                return;
            case R.id.select_backgnd /* 2131296971 */:
                hideSlideBar();
                if (this.layBackground.getVisibility() != View.VISIBLE) {
                    this.layBackground.setVisibility(View.VISIBLE);
                    this.layBackground.startAnimation(this.animSlideUp);
                } else {
                    this.layBackground.setVisibility(View.GONE);
                    this.layBackground.startAnimation(this.animSlideDown);
                }
                this.layEffects.setVisibility(View.GONE);
                this.layStkrMain.setVisibility(View.GONE);
                this.layTextMain.setVisibility(View.GONE);
                this.laySticker.setVisibility(View.GONE);
                addBotton();
                this.txtBG.setTextColor(getResources().getColor(R.color.color_add_btn));
                return;
            case R.id.select_effect /* 2131296973 */:
                removeScroll();
                removeImageViewControll();
                hideSlideBar();
                if (this.layEffects.getVisibility() != View.VISIBLE) {
                    this.layEffects.setVisibility(View.VISIBLE);
                    this.layEffects.startAnimation(this.animSlideUp);
                } else {
                    this.layEffects.setVisibility(View.GONE);
                    this.layEffects.startAnimation(this.animSlideDown);
                }
                this.layStkrMain.setVisibility(View.GONE);
                this.layBackground.setVisibility(View.GONE);
                this.layTextMain.setVisibility(View.GONE);
                this.laySticker.setVisibility(View.GONE);
                addBotton();
                this.txtEffect.setTextColor(getResources().getColor(R.color.color_add_btn));
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveImage() {
        removeScroll();
        hideSlideBar();
        this.layEffects.setVisibility(View.GONE);
        this.layStkrMain.setVisibility(View.GONE);
        this.layBackground.setVisibility(View.GONE);
        this.layTextMain.setVisibility(View.GONE);
        this.laySticker.setVisibility(View.GONE);
        removeImageViewControll();
        if (this.seekbarContainer.getVisibility() == View.VISIBLE) {
            this.seekbarContainer.startAnimation(this.animSlideDown);
            this.seekbarContainer.setVisibility(View.GONE);
        }
        if (this.layTextMain.getVisibility() == View.VISIBLE) {
            this.layTextMain.startAnimation(this.animSlideDown);
            this.layTextMain.setVisibility(View.GONE);
        }
        if (this.layStkrMain.getVisibility() == View.VISIBLE) {
            this.layStkrMain.startAnimation(this.animSlideDown);
            this.layStkrMain.setVisibility(View.GONE);
        }
        this.guideline.setVisibility(View.GONE);
        this.bitmap = viewToBitmap(this.mainRelative);
        this.llLogo.setVisibility(View.VISIBLE);
        this.llLogo.setDrawingCacheEnabled(true);
        Bitmap createBitmap = Bitmap.createBitmap(this.llLogo.getDrawingCache());
        this.llLogo.setDrawingCacheEnabled(false);
        this.llLogo.setVisibility(View.INVISIBLE);
        withoutWatermark = this.bitmap;
        if (!this.preferenceClass.getBoolean("isAdsDisabled", false)) {
            this.bitmap = ImageUtils.mergelogo(this.bitmap, createBitmap);
        }
        saveBitmap(true);
    }

    private void requestGalleryImagePermission() {
        Dexter.withContext(this).withPermissions("android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.28
            @Override // com.karumi.dexter.listener.multi.MultiplePermissionsListener
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    CreateCardActivity.this.onGalleryBackground();
                }
                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                    CreateCardActivity.this.showSettingsDialog();
                }
            }

            @Override // com.karumi.dexter.listener.multi.MultiplePermissionsListener
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError dexterError) {

                Toast.makeText(CreateCardActivity.this.getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();

            }
        }).onSameThread().check();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestGalleryPermission() {
        Dexter.withContext(this).withPermissions("android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE").
                withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            CreateCardActivity.this.onGalleryButtonClick();
                        }
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            CreateCardActivity.this.showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).withErrorListener(new PermissionRequestErrorListener() {

                    @Override
                    public void onError(DexterError dexterError) {
                        Toast.makeText(CreateCardActivity.this.getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                }).onSameThread().check();
    }


    public void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.31
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                CreateCardActivity.this.openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.32
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivityForResult(intent, 101);
    }

    private void addBotton() {
        this.txtText.setTextColor(-1);
        this.txtSticker.setTextColor(-1);
        this.txtImage.setTextColor(-1);
        this.txtEffect.setTextColor(-1);
        this.txtBG.setTextColor(-1);
    }

    private void setRightShadow() {
        this.leftRightShadow += 4;
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setLeftRightShadow(this.leftRightShadow);
                }
            }
        }
    }

    private void setLeftShadow() {
        this.leftRightShadow -= 4;
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setLeftRightShadow(this.leftRightShadow);
                }
            }
        }
    }

    private void setBottomShadow() {
        this.topBottomShadow += 4;
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setTopBottomShadow(this.topBottomShadow);
                }
            }
        }
    }

    private void setTopShadow() {
        this.topBottomShadow -= 4;
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setTopBottomShadow(this.topBottomShadow);
                }
            }
        }
    }

    private void mainControlcolorPickerDialog(boolean z) {
        new AmbilWarnaDialog(this, this.bColor, z, new AmbilWarnaDialog.OnAmbilWarnaListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.33
            @Override // yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                CreateCardActivity.this.updateColor(i);
            }

            @Override // yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                Log.e(CreateCardActivity.TAG, "onCancel: ");
            }
        }).show();
    }

    private void mainControlShadowPickerDialog(boolean z) {
        new AmbilWarnaDialog(this, this.bColor, z, new AmbilWarnaDialog.OnAmbilWarnaListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.34
            @Override // yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                CreateCardActivity.this.updateShadow(i);
            }

            @Override // yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                Log.e(CreateCardActivity.TAG, "onCancel: ");
            }
        }).show();
    }

    private void mainControlBgPickerDialog(boolean z) {
        new AmbilWarnaDialog(this, this.bColor, z, new AmbilWarnaDialog.OnAmbilWarnaListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.35
            @Override // yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                CreateCardActivity.this.updateBgColor(i);
            }

            @Override // yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                Log.e(CreateCardActivity.TAG, "onCancel: ");
            }
        }).show();
    }

    private void showResContainer() {
        this.btnUpDown.animate().setDuration(500L).start();
        this.btnUpDown.setBackgroundResource(R.drawable.textlib_down);
        this.seekbarContainer.setVisibility(View.VISIBLE);
        this.layStkrMain.startAnimation(this.animSlideUp);
        this.layStkrMain.requestLayout();
        this.layStkrMain.postInvalidate();
        this.layStkrMain.post(new Runnable() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.36
            @Override // java.lang.Runnable
            public void run() {
                CreateCardActivity createCardActivity = CreateCardActivity.this;
                createCardActivity.stickerScrollView(createCardActivity.focusedView);
            }
        });
    }

    private void hideResContainer() {
        this.btnUpDown.animate().setDuration(500L).start();
        this.btnUpDown.setBackgroundResource(R.drawable.textlib_up);
        this.layStkrMain.startAnimation(this.animSlideDown);
        this.seekbarContainer.setVisibility(View.GONE);
        this.layStkrMain.requestLayout();
        this.layStkrMain.postInvalidate();
        this.layStkrMain.post(new Runnable() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.37
            @Override // java.lang.Runnable
            public void run() {
                CreateCardActivity createCardActivity = CreateCardActivity.this;
                createCardActivity.stickerScrollView(createCardActivity.focusedView);
            }
        });
    }

    private void showTextResContainer() {
        this.btnUpDown1.animate().setDuration(500L).start();
        this.btnUpDown1.setBackgroundResource(R.drawable.textlib_down);
        this.layTextEdit.setVisibility(View.VISIBLE);
        this.layTextMain.startAnimation(this.animSlideUp);
        this.layTextMain.requestLayout();
        this.layTextMain.postInvalidate();
        this.layTextMain.post(new Runnable() {
            @Override
            public void run() {
                CreateCardActivity createCardActivity = CreateCardActivity.this;
                createCardActivity.textScrollView(createCardActivity.focusedView);
            }
        });
    }

    private void hideTextResContainer() {
        this.btnUpDown1.animate().setDuration(500L).start();
        this.btnUpDown1.setBackgroundResource(R.drawable.textlib_up);
        this.layTextMain.startAnimation(this.animSlideDown);
        this.layTextEdit.setVisibility(View.GONE);
        this.layTextMain.requestLayout();
        this.layTextMain.postInvalidate();
        this.layTextMain.post(new Runnable() {
            @Override
            public void run() {
                CreateCardActivity createCardActivity = CreateCardActivity.this;
                createCardActivity.textScrollView(createCardActivity.focusedView);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTextBgTexture(String str) {
        getResources().getIdentifier(str, "drawable", getPackageName());
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setBgDrawable(str);
                    autofitTextRel.setBgAlpha(this.seekBar3.getProgress());
                    this.bgColor = 0;
                    ((AutofitTextRel) txtStkrRel.getChildAt(i)).getTextInfo().setBG_DRAWABLE(str);
                    this.bgDrawable = autofitTextRel.getBgDrawable();
                    this.bgAlpha = this.seekBar3.getProgress();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTextFonts(String str) {
        this.fontName = str;
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setTextFont(str);
                    saveBitmapUndu();
                }
            }
        }
    }

    private void setLetterApacing() {
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.applyLetterSpacing(this.letterSpacing);
                }
            }
        }
    }

    private void setLineApacing() {
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.applyLineSpacing(this.lineSpacing);
                }
            }
        }
    }

    private void setBoldFonts() {
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setBoldFont();
                }
            }
        }
    }

    private void setCapitalFont() {
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setCapitalFont();
                }
            }
        }
    }

    private void setUnderLineFont() {
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setUnderLineFont();
                }
            }
        }
    }

    private void setItalicFont() {
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setItalicFont();
                }
            }
        }
    }

    private void setLeftAlignMent() {
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setLeftAlignMent();
                }
            }
        }
    }

    private void setCenterAlignMent() {
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setCenterAlignMent();
                }
            }
        }
    }

    private void setRightAlignMent() {
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setRightAlignMent();
                }
            }
        }
    }


    public void setBitmapOverlay(int i) {
        this.layFilter.setVisibility(View.VISIBLE);
        this.transImgage.setVisibility(View.VISIBLE);
        try {
          this.transImgage.setImageBitmap(BitmapFactory.decodeResource(getResources(), i));
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), i, options);
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = ImageUtils.getClosestResampleSize(options.outWidth, options.outHeight, this.mainRelative.getWidth() < this.mainRelative.getHeight() ? this.mainRelative.getWidth() : this.mainRelative.getHeight());
            options.inJustDecodeBounds = false;
           this.transImgage.setImageBitmap(BitmapFactory.decodeResource(getResources(), i, options2));
        }
    }


    public void updateColor(int i) {
        int childCount = txtStkrRel.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = txtStkrRel.getChildAt(i2);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setTextColor(i);
                    this.tColor = i;
                    this.textColorSet = i;

                    saveBitmapUndu();
                }
            }
            if (childAt instanceof StickerView) {
                StickerView stickerView = (StickerView) childAt;
                if (stickerView.getBorderVisbilty()) {
                    stickerView.setColor(i);
                    this.stkrColorSet = i;

                    saveBitmapUndu();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateShadow(int i) {
        int childCount = txtStkrRel.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = txtStkrRel.getChildAt(i2);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setTextShadowColor(i);
                    this.shadowColor = i;
                    saveBitmapUndu();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBgColor(int i) {
        int childCount = txtStkrRel.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = txtStkrRel.getChildAt(i2);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    autofitTextRel.setBgAlpha(this.seekBar3.getProgress());
                    autofitTextRel.setBgColor(i);
                    this.bgColor = i;
                    this.bgDrawable = "0";
                    saveBitmapUndu();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePositionSticker(String str) {
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                if (autofitTextRel.getBorderVisibility()) {
                    if (str.equals("incrX")) {
                        autofitTextRel.incrX();
                    }
                    if (str.equals("decX")) {
                        autofitTextRel.decX();
                    }
                    if (str.equals("incrY")) {
                        autofitTextRel.incrY();
                    }
                    if (str.equals("decY")) {
                        autofitTextRel.decY();
                    }
                }
            }
            if (childAt instanceof StickerView) {
                StickerView stickerView = (StickerView) childAt;
                if (stickerView.getBorderVisbilty()) {
                    if (str.equals("incrX")) {
                        stickerView.incrX();
                    }
                    if (str.equals("decX")) {
                        stickerView.decX();
                    }
                    if (str.equals("incrY")) {
                        stickerView.incrY();
                    }
                    if (str.equals("decY")) {
                        stickerView.decY();
                    }
                }
            }
        }
    }

    private boolean closeViewAll() {

        this.viewAllFrame.removeAllViews();
        this.viewAllFrame.setVisibility(View.GONE);
        return false;

    }


    public void saveComponent1(long j, DatabaseHandler databaseHandler) {
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if (childAt instanceof AutofitTextRel) {
                TextInfo textInfo = ((AutofitTextRel) childAt).getTextInfo();
                textInfo.setTEMPLATE_ID((int) j);
                textInfo.setORDER(i);
                textInfo.setTYPE("TEXT");
                databaseHandler.insertTextRow(textInfo);
            } else {
                saveShapeAndSticker(j, i, TYPE_STICKER, databaseHandler);
            }
        }
    }

    public void saveShapeAndSticker(long j, int i, int i2, DatabaseHandler databaseHandler) {
        ElementInfo componentInfo = ((StickerView) txtStkrRel.getChildAt(i)).getComponentInfo();
        componentInfo.setTEMPLATE_ID((int) j);
        componentInfo.setTYPE("STICKER");
        componentInfo.setORDER(i);
        databaseHandler.insertComponentInfoRow(componentInfo);
    }

    public void addTextDialog(TextInfo textInfo) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_text_popup);
        dialog.setCancelable(false);
        TextView textView = (TextView) dialog.findViewById(R.id.txtTitle);
        final FitEditText fitEditText = (FitEditText) dialog.findViewById(R.id.auto_fit_edit_text);
        Button button = (Button) dialog.findViewById(R.id.btnCancelDialog);
        Button button2 = (Button) dialog.findViewById(R.id.btnAddTextSDialog);
        if (textInfo != null) {
            fitEditText.setText(textInfo.getTEXT());
        } else {
            fitEditText.setText("");
        }
        textView.setTypeface(setBoldFont());
        fitEditText.setTypeface(setNormalFont());
        button.setTypeface(setNormalFont());
        button2.setTypeface(setNormalFont());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fitEditText.getText().toString().trim().length() > 0) {
                    String replace = fitEditText.getText().toString().replace("\n", " ");
                    TextInfo textInfo2 = new TextInfo();
                    if (CreateCardActivity.this.editMode) {
                        textInfo2.setTEXT(replace);
                        try {
                            textInfo2.setFONT_NAME(textInfo2.getFONT_NAME());
                            textInfo2.setTEXT_COLOR(textInfo2.getTEXT_COLOR());
                            textInfo2.setTEXT_ALPHA(textInfo2.getTEXT_ALPHA());
                            textInfo2.setSHADOW_COLOR(textInfo2.getSHADOW_COLOR());
                            textInfo2.setSHADOW_PROG(textInfo2.getSHADOW_PROG());
                            textInfo2.setBG_COLOR(textInfo2.getBG_COLOR());
                            textInfo2.setBG_DRAWABLE(textInfo2.getBG_DRAWABLE());
                            textInfo2.setBG_ALPHA(textInfo2.getBG_ALPHA());
                            textInfo2.setROTATION(textInfo2.getROTATION());
                            textInfo2.setFIELD_TWO("");
                            textInfo2.setPOS_X(textInfo2.getPOS_X());
                            textInfo2.setPOS_Y(textInfo2.getPOS_Y());
                            textInfo2.setWIDTH(textInfo2.getWIDTH());
                            textInfo2.setHEIGHT(textInfo2.getHEIGHT());
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            textInfo2.setFONT_NAME(CreateCardActivity.this.fontName);
                            textInfo2.setTEXT_COLOR(ViewCompat.MEASURED_STATE_MASK);
                            textInfo2.setTEXT_ALPHA(100);
                            textInfo2.setSHADOW_COLOR(ViewCompat.MEASURED_STATE_MASK);
                            textInfo2.setSHADOW_PROG(0);
                            textInfo2.setBG_COLOR(ViewCompat.MEASURED_STATE_MASK);
                            textInfo2.setBG_DRAWABLE("0");
                            textInfo2.setBG_ALPHA(0);
                            textInfo2.setROTATION(0.0f);
                            textInfo2.setFIELD_TWO("");
                            textInfo2.setPOS_X((CreateCardActivity.txtStkrRel.getWidth() / 2) - ImageUtils.dpToPx((Context) CreateCardActivity.this, 100));
                            textInfo2.setPOS_Y((CreateCardActivity.txtStkrRel.getHeight() / 2) - ImageUtils.dpToPx((Context) CreateCardActivity.this, 100));
                            textInfo2.setWIDTH(ImageUtils.dpToPx((Context) CreateCardActivity.this, 200));
                            textInfo2.setHEIGHT(ImageUtils.dpToPx((Context) CreateCardActivity.this, 200));
                        }
                        int childCount = CreateCardActivity.txtStkrRel.getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            View childAt = CreateCardActivity.txtStkrRel.getChildAt(i);
                            if (childAt instanceof AutofitTextRel) {
                                AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                                if (autofitTextRel.getBorderVisibility()) {
                                    autofitTextRel.setTextInfo(textInfo2, false);
                                    autofitTextRel.setBorderVisibility(true);
                                    CreateCardActivity.this.editMode = false;
                                }
                            }
                        }
                    } else {
                        textInfo2.setTEXT(replace);
                        textInfo2.setFONT_NAME(CreateCardActivity.this.fontName);
                        textInfo2.setTEXT_COLOR(ViewCompat.MEASURED_STATE_MASK);
                        textInfo2.setTEXT_ALPHA(100);
                        textInfo2.setSHADOW_COLOR(ViewCompat.MEASURED_STATE_MASK);
                        textInfo2.setSHADOW_PROG(0);
                        textInfo2.setBG_COLOR(ViewCompat.MEASURED_STATE_MASK);
                        textInfo2.setBG_DRAWABLE("0");
                        textInfo2.setBG_ALPHA(0);
                        textInfo2.setROTATION(0.0f);
                        textInfo2.setFIELD_TWO("");
                        textInfo2.setPOS_X((CreateCardActivity.txtStkrRel.getWidth() / 2) - ImageUtils.dpToPx((Context) CreateCardActivity.this, 100));
                        textInfo2.setPOS_Y((CreateCardActivity.txtStkrRel.getHeight() / 2) - ImageUtils.dpToPx((Context) CreateCardActivity.this, 100));
                        textInfo2.setWIDTH(ImageUtils.dpToPx((Context) CreateCardActivity.this, 200));
                        textInfo2.setHEIGHT(ImageUtils.dpToPx((Context) CreateCardActivity.this, 200));
                        try {
                            CreateCardActivity.this.verticalSeekBar.setProgress(100);
                            CreateCardActivity.this.seekBarShadow.setProgress(0);
                            CreateCardActivity.this.seekBar3.setProgress(255);
                            AutofitTextRel autofitTextRel2 = new AutofitTextRel((Context) CreateCardActivity.this, false);
                            /* CreateCardActivity.txtStkrRel.addView(autofitTextRel2);*/
                            autofitTextRel2.setTextInfo(textInfo2, false);
                            autofitTextRel2.setId(ViewIdGenerator.generateViewId());
                            autofitTextRel2.setOnTouchCallbackListener(CreateCardActivity.this);
                            autofitTextRel2.setBorderVisibility(true);
                        } catch (ArrayIndexOutOfBoundsException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (CreateCardActivity.this.layTextMain.getVisibility() == View.GONE) {
                        CreateCardActivity.this.layTextMain.setVisibility(View.VISIBLE);
                        CreateCardActivity.this.layTextMain.startAnimation(CreateCardActivity.this.animSlideUp);
                    }
                    CreateCardActivity.this.saveBitmapUndu();
                    dialog.dismiss();
                    return;
                }
                Toast.makeText(CreateCardActivity.this, "Please enter text here.", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();

    }

    private void onTouchApply() {
        removeScroll();
        if (this.layStkrMain.getVisibility() == View.VISIBLE) {
            this.layStkrMain.startAnimation(this.animSlideDown);
            this.layStkrMain.setVisibility(View.GONE);
        }
        if (this.layTextMain.getVisibility() == View.VISIBLE) {
            this.layTextMain.startAnimation(this.animSlideDown);
            this.layTextMain.setVisibility(View.GONE);
        }
        if (this.showtailsSeek) {
            this.layHandletails.setVisibility(View.VISIBLE);
        }
        if (this.seekbarContainer.getVisibility() == View.GONE) {
            this.seekbarContainer.clearAnimation();
            this.layTextMain.clearAnimation();
            this.seekbarContainer.setVisibility(View.VISIBLE);
            this.seekbarContainer.startAnimation(this.animSlideUp);
        }
        this.layStkrMain.clearAnimation();
        this.layTextMain.clearAnimation();
        removeImageViewControll();
        hideSlideBar();
    }

    @Override // com.invitationmaker.lss.photomaker.listener.GetSnapListener
    public void onSnapFilter(int i, int i2, String str) {
        this.laySticker.setVisibility(View.GONE);
        btnLayControls.setVisibility(View.VISIBLE);
        if (this.layTextMain.getVisibility() == View.VISIBLE) {
            this.layTextMain.startAnimation(this.animSlideDown);
            this.layTextMain.setVisibility(View.GONE);
        }
        if (!str.equals("")) {
            this.colorType = "colored";
            addSticker("", str, null);
        } else if (i2 == 33) {
            setDrawable("colored", "sticker_offer_" + (i + 1));
        } else if (i2 == 34) {
            setDrawable("colored", "sticker_sale_" + (i + 1));
        } else if (i2 == 35) {
            setDrawable("colored", "sticker_banner_" + (i + 1));
        } else if (i2 == 36) {
            setDrawable("colored", "sticker_ribbon_" + (i + 1));
        } else if (i2 == 37) {
            setDrawable("colored", "sticker_sport_" + (i + 1));
        } else if (i2 == 38) {
            setDrawable("colored", "sticker_birthday_" + (i + 1));
        } else if (i2 == 39) {
            setDrawable("colored", "sticker_decoration_" + (i + 1));
        } else if (i2 == 40) {
            setDrawable("colored", "sticker_party_" + (i + 1));
        } else if (i2 == 41) {
            setDrawable("colored", "sticker_love_" + (i + 1));
        } else if (i2 == 42) {
            setDrawable("colored", "sticker_music_" + (i + 1));
        } else if (i2 == 43) {
            setDrawable("colored", "sticker_festival_" + (i + 1));
        } else if (i2 == 44) {
            setDrawable("colored", "sticker_nature_" + (i + 1));
        } else if (i2 == 45) {
            setDrawable("colored", "sticker_car_" + (i + 1));
        } else if (i2 == 46) {
            setDrawable("colored", "sticker_emoji_" + (i + 1));
        } else if (i2 == 47) {
            setDrawable("colored", "sticker_college_" + (i + 1));
        } else if (i2 == 48) {
            setDrawable("colored", "sticker_coffe_" + (i + 1));
        } else if (i2 == 49) {
            setDrawable("colored", "sticker_halloween_" + (i + 1));
        } else if (i2 == 50) {
            setDrawable("white", "shap" + (i + 1));
        } else if (i2 == 51) {
            setDrawable("colored", "sticker_animal_" + (i + 1));
        } else if (i2 == 52) {
            setDrawable("colored", "sticker_cartoon_" + (i + 1));
        } else {
            this.colorType = "colored";
        }
    }

    @Override
    public void onSnapFilter(int i, int i2, String str, String str2) {
        this.laySticker.setVisibility(View.GONE);
        btnLayControls.setVisibility(View.VISIBLE);
        if (this.layTextMain.getVisibility() == View.VISIBLE) {
            this.layTextMain.startAnimation(this.animSlideDown);
            this.layTextMain.setVisibility(View.GONE);
        }
        if (i2 == 104) {
            if (str != null) {
                this.isBackground = true;
                Uri fromFile = Uri.fromFile(new File(str));
                File cacheDir = getCacheDir();
                Uri fromFile2 = Uri.fromFile(new File(cacheDir, "SampleCropImage" + System.currentTimeMillis() + ".png"));
                String[] split = this.ratio.split(":");
                int parseInt = Integer.parseInt(split[0]);
                int parseInt2 = Integer.parseInt(split[1]);
                UCrop.Options options = new UCrop.Options();
                options.setCompressionFormat(Bitmap.CompressFormat.PNG);
                options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                options.setFreeStyleCropEnabled(false);
                UCrop.of(fromFile, fromFile2).withOptions(options).withAspectRatio(parseInt, parseInt2).start(this);
            }
        } else if (!str.equals("")) {
            this.imgOK.setVisibility(View.VISIBLE);
            if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
                getSupportFragmentManager().popBackStack();
            }
            this.colorType = str2;
            addSticker("", str, null);
        } else if (i2 == 33) {
            setDrawable("colored", "sticker_offer_" + (i + 1));
        } else if (i2 == 34) {
            setDrawable("colored", "sticker_sale_" + (i + 1));
        } else if (i2 == 35) {
            setDrawable("colored", "sticker_banner_" + (i + 1));
        } else if (i2 == 36) {
            setDrawable("colored", "sticker_ribbon_" + (i + 1));
        } else if (i2 == 37) {
            setDrawable("colored", "sticker_sport_" + (i + 1));
        } else if (i2 == 38) {
            setDrawable("colored", "sticker_birthday_" + (i + 1));
        } else if (i2 == 39) {
            setDrawable("colored", "sticker_decoration_" + (i + 1));
        } else if (i2 == 40) {
            setDrawable("colored", "sticker_party_" + (i + 1));
        } else if (i2 == 41) {
            setDrawable("colored", "sticker_love_" + (i + 1));
        } else if (i2 == 42) {
            setDrawable("colored", "sticker_music_" + (i + 1));
        } else if (i2 == 43) {
            setDrawable("colored", "sticker_festival_" + (i + 1));
        } else if (i2 == 44) {
            setDrawable("colored", "sticker_nature_" + (i + 1));
        } else if (i2 == 45) {
            setDrawable("colored", "sticker_car_" + (i + 1));
        } else if (i2 == 46) {
            setDrawable("colored", "sticker_emoji_" + (i + 1));
        } else if (i2 == 47) {
            setDrawable("colored", "sticker_college_" + (i + 1));
        } else if (i2 == 48) {
            setDrawable("colored", "sticker_coffe_" + (i + 1));
        } else if (i2 == 49) {
            setDrawable("colored", "sticker_halloween_" + (i + 1));
        } else if (i2 == 50) {
            setDrawable("white", "shap" + (i + 1));
        } else if (i2 == 51) {
            setDrawable("colored", "sticker_animal_" + (i + 1));
        } else if (i2 == 52) {
            setDrawable("colored", "sticker_cartoon_" + (i + 1));
        } else {
            this.colorType = "colored";
        }
    }

    private void setDrawable(String str, String str2) {
        this.colorType = str;
        if (str.equals("white")) {
            this.layColor.setVisibility(View.VISIBLE);
            this.layHue.setVisibility(View.GONE);
        } else {
            this.layColor.setVisibility(View.GONE);
            this.layHue.setVisibility(View.VISIBLE);
        }
        this.layEffects.setVisibility(View.GONE);
        this.layTextMain.setVisibility(View.GONE);
        addSticker(str2, "", null);
    }

    private void addSticker(String str, String str2, Bitmap bitmap) {

        if (this.layStkrMain.getVisibility() == View.GONE) {
            this.layStkrMain.setVisibility(View.VISIBLE);
            this.layStkrMain.startAnimation(this.animSlideUp);
        }
        if (this.colorType.equals("white")) {
            this.layColor.setVisibility(View.VISIBLE);
            this.layHue.setVisibility(View.GONE);
        } else {
            this.layColor.setVisibility(View.GONE);
            this.layHue.setVisibility(View.VISIBLE);
        }
        this.hueSeekbar.setProgress(1);
        removeImageViewControll();
        ElementInfo elementInfo = new ElementInfo();
        elementInfo.setPOS_X((this.mainRelative.getWidth() / 2) - ImageUtils.dpToPx((Context) this, 70));
        elementInfo.setPOS_Y((this.mainRelative.getHeight() / 2) - ImageUtils.dpToPx((Context) this, 70));
        elementInfo.setWIDTH(ImageUtils.dpToPx((Context) this, 140));
        elementInfo.setHEIGHT(ImageUtils.dpToPx((Context) this, 140));
        elementInfo.setROTATION(0.0f);
        elementInfo.setRES_ID(str);
        elementInfo.setBITMAP(bitmap);
        elementInfo.setCOLORTYPE(this.colorType);
        elementInfo.setTYPE("STICKER");
        elementInfo.setSTC_OPACITY(255);
        elementInfo.setSTC_COLOR(0);
        elementInfo.setSTKR_PATH(str2);
        elementInfo.setSTC_HUE(this.hueSeekbar.getProgress());
        elementInfo.setFIELD_TWO("0,0");
        StickerView stickerView = new StickerView(this);
        stickerView.optimizeScreen(this.screenWidth, this.screenHeight);
        stickerView.setMainLayoutWH(this.mainRelative.getWidth(), this.mainRelative.getHeight());
        stickerView.setComponentInfo(elementInfo);
        stickerView.setId(ViewIdGenerator.generateViewId());
//        txtStkrRel.addView(stickerView);
        stickerView.setOnTouchCallbackListener(this);
        stickerView.setBorderVisibility(true);


    }

    private Bitmap viewToBitmap(View view) {
        try {
            Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            view.draw(new Canvas(createBitmap));
            return createBitmap;
        } finally {
            view.destroyDrawingCache();
        }
    }

    private void saveBitmap(final boolean z) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.plzwait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(new Runnable() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.42
            @Override // java.lang.Runnable
            public void run() {
                String str;
                try {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Invitation Design");
                    if (file.exists() || file.mkdirs()) {
                        String str2 = "Photo_" + System.currentTimeMillis();
                        if (z) {
                            str = str2 + ".png";
                        } else {
                            str = str2 + ".jpg";
                        }
                        CreateCardActivity.this.filename = file.getPath() + File.separator + str;
                        File file2 = new File(CreateCardActivity.this.filename);
                        try {
                            if (!file2.exists()) {
                                file2.createNewFile();
                            }
                            FileOutputStream fileOutputStream = new FileOutputStream(file2);
                            Bitmap createBitmap = Bitmap.createBitmap(CreateCardActivity.this.bitmap.getWidth(), CreateCardActivity.this.bitmap.getHeight(), CreateCardActivity.this.bitmap.getConfig());
                            Canvas canvas = new Canvas(createBitmap);
                            canvas.drawColor(-1);
                            canvas.drawBitmap(CreateCardActivity.this.bitmap, 0.0f, 0.0f, (Paint) null);
                            CreateCardActivity.this.checkMemory = createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                            createBitmap.recycle();
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            CreateCardActivity.isUpadted = true;
                            MediaScannerConnection.scanFile(CreateCardActivity.this, new String[]{file2.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.42.1
                                @Override
                                // android.media.MediaScannerConnection.OnScanCompletedListener
                                public void onScanCompleted(String str3, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + str3 + ":");
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("-> uri=");
                                    sb.append(uri);
                                    Log.i("ExternalStorage", sb.toString());
                                }
                            });
                            CreateCardActivity.this.sendBroadcast(new Intent("androR.id.intent.action.MEDIA_SCANNER_SCAN_FILE", FileProvider.getUriForFile(CreateCardActivity.this, CreateCardActivity.this.getApplicationContext().getPackageName() + ".provider", file2)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Thread.sleep(1000L);
                        progressDialog.dismiss();
                        return;
                    }
                    Log.d("", "Can't create directory to save image.");
                    Toast.makeText(CreateCardActivity.this.getApplicationContext(), CreateCardActivity.this.getResources().getString(R.string.create_dir_err),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception unused) {
                }
            }
        }).start();
        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                if (CreateCardActivity.this.checkMemory) {
                    CreateCardActivity.this.mainRelative.setDrawingCacheEnabled(true);
                    Bitmap createBitmap = Bitmap.createBitmap(CreateCardActivity.this.mainRelative.getDrawingCache());
                    CreateCardActivity.this.mainRelative.setDrawingCacheEnabled(false);
                    DatabaseHandler databaseHandler = null;
                    try {
                        if (CreateCardActivity.this.ratio.equals("")) {
                            CreateCardActivity.this.tempPath = AllConstants.saveBitmapObject1(CreateCardActivity.imgBtmap);
                        }
                        CreateCardActivity.this.tempPath = AllConstants.saveBitmapObject1(CreateCardActivity.imgBtmap);
                        String saveBitmapObject = AllConstants.saveBitmapObject(CreateCardActivity.activity, ImageUtils.resizeBitmap(createBitmap, ((int) CreateCardActivity.this.screenWidth) / 3, ((int) CreateCardActivity.this.screenHeight) / 3));
                        if (saveBitmapObject != null) {
                            TemplateInfo templateInfo = new TemplateInfo();
                            templateInfo.setTHUMB_URI(saveBitmapObject);
                            templateInfo.setFRAME_NAME(CreateCardActivity.this.frameName);
                            templateInfo.setRATIO(CreateCardActivity.this.ratio);
                            templateInfo.setPROFILE_TYPE(CreateCardActivity.this.profile);
                            templateInfo.setSEEK_VALUE(String.valueOf(CreateCardActivity.this.seekValue));
                            templateInfo.setTYPE("USER");
                            templateInfo.setTEMP_PATH(CreateCardActivity.this.tempPath);
                            templateInfo.setTEMPCOLOR(CreateCardActivity.this.hex);
                            templateInfo.setOVERLAY_NAME(CreateCardActivity.this.overlayName);
                            templateInfo.setOVERLAY_OPACITY(CreateCardActivity.this.seek.getProgress());
                            templateInfo.setOVERLAY_BLUR(CreateCardActivity.this.seekBlur.getProgress());
                            databaseHandler = DatabaseHandler.getDbHandler(CreateCardActivity.this.getApplicationContext());
                            CreateCardActivity.this.saveComponent1(databaseHandler.insertTemplateRow(templateInfo), databaseHandler);
                            CreateCardActivity.isUpdated = true;
                        }
                        if (databaseHandler != null) {
                            databaseHandler.close();
                        }
                    } catch (Exception e) {
                        Log.i("testing", "Exception " + e.getMessage());
                        e.printStackTrace();
                        if (databaseHandler != null) {
                            databaseHandler.close();
                        }
                    } catch (Throwable unused) {
                        if (databaseHandler != null) {
                            databaseHandler.close();
                        }
                    }
                    if (CreateCardActivity.this.isRewarded) {
                        CreateCardActivity.this.isRewarded = false;
                    }



                  /*
                    Intent intent = new Intent(CreateCardActivity.this, ShareImageActivity.class);
                    intent.putExtra("uri", CreateCardActivity.this.filename);
                    intent.putExtra("way", "Poster");
                    CreateCardActivity.this.startActivity(intent);
                    */


                    return;


                }
                new AlertDialog.Builder(CreateCardActivity.this, 16974126).
                        setMessage(AllConstants.getSpannableString(CreateCardActivity.this, Typeface.DEFAULT, R.string.memoryerror)).setPositiveButton(AllConstants.getSpannableString(CreateCardActivity.this, Typeface.DEFAULT, R.string.ok), new DialogInterface.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.43.1
                            @Override
                            public void onClick(DialogInterface dialogInterface2, int i) {
                                dialogInterface2.dismiss();
                            }
                        }).create().show();
            }
        });
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        int id = seekBar.getId();
        int i2 = 0;
        if (id == R.id.alpha_seekBar) {
            int childCount = txtStkrRel.getChildCount();
            while (i2 < childCount) {
                View childAt = txtStkrRel.getChildAt(i2);
                if (childAt instanceof StickerView) {
                    StickerView stickerView = (StickerView) childAt;
                    if (stickerView.getBorderVisbilty()) {
                        stickerView.setAlphaProg(i);
                    }
                }
                i2++;
            }
        } else if (id != R.id.hue_seekBar) {
            switch (id) {
                case R.id.seek /* 2131296958 */:
                    this.alpha = i;
                    if (Build.VERSION.SDK_INT >= 16) {
                        this.transImgage.setImageAlpha(this.alpha);
                        return;
                    } else {
                        this.transImgage.setAlpha(this.alpha);
                        return;
                    }
                case R.id.seekBar2 /* 2131296959 */:
                    this.processs = i;
                    int childCount2 = txtStkrRel.getChildCount();
                    while (i2 < childCount2) {
                        View childAt2 = txtStkrRel.getChildAt(i2);
                        if (childAt2 instanceof AutofitTextRel) {
                            AutofitTextRel autofitTextRel = (AutofitTextRel) childAt2;
                            if (autofitTextRel.getBorderVisibility()) {
                                autofitTextRel.setTextAlpha(i);
                            }
                        }
                        i2++;
                    }
                    return;
                case R.id.seekBar3 /* 2131296960 */:
                    int childCount3 = txtStkrRel.getChildCount();
                    while (i2 < childCount3) {
                        View childAt3 = txtStkrRel.getChildAt(i2);
                        if (childAt3 instanceof AutofitTextRel) {
                            AutofitTextRel autofitTextRel2 = (AutofitTextRel) childAt3;
                            if (autofitTextRel2.getBorderVisibility()) {
                                autofitTextRel2.setBgAlpha(i);
                                this.bgAlpha = i;
                            }
                        }
                        i2++;
                    }
                    return;
                case R.id.seekBar_shadow /* 2131296961 */:
                    int childCount4 = txtStkrRel.getChildCount();
                    while (i2 < childCount4) {
                        View childAt4 = txtStkrRel.getChildAt(i2);
                        if (childAt4 instanceof AutofitTextRel) {
                            AutofitTextRel autofitTextRel3 = (AutofitTextRel) childAt4;
                            if (autofitTextRel3.getBorderVisibility()) {
                                autofitTextRel3.setTextShadowProg(i);
                                this.shadowProg = i;
                            }
                        }
                        i2++;
                    }
                    return;
                case R.id.seekLetterSpacing:
                    this.letterSpacing = i / 3;
                    setLetterApacing();
                    return;
                case R.id.seekLineSpacing:
                    this.lineSpacing = i / 2;
                    setLineApacing();
                    return;
                case R.id.seekShadowBlur:
                    int childCount5 = txtStkrRel.getChildCount();
                    while (i2 < childCount5) {
                        View childAt5 = txtStkrRel.getChildAt(i2);
                        if (childAt5 instanceof AutofitTextRel) {
                            AutofitTextRel autofitTextRel4 = (AutofitTextRel) childAt5;
                            if (autofitTextRel4.getBorderVisibility()) {
                                autofitTextRel4.setTextShadowOpacity(i);
                            }
                        }
                        i2++;
                    }
                    return;
                case R.id.seekTextCurve /* 2131296965 */:
                    int progress = seekBar.getProgress() - 360;
                    mRadius = progress;
                    if (progress <= 0 && progress >= -8) {
                        mRadius = -8;
                    }
                    int childCount6 = txtStkrRel.getChildCount();
                    while (i2 < childCount6) {
                        View childAt6 = txtStkrRel.getChildAt(i2);
                        if (childAt6 instanceof AutofitTextRel) {
                            AutofitTextRel autofitTextRel5 = (AutofitTextRel) childAt6;
                            if (autofitTextRel5.getBorderVisibility()) {
                                autofitTextRel5.setDrawParams();
                            }
                        }
                        i2++;
                    }
                    return;
                case R.id.seek_blur /* 2131296966 */:
                    if (i != 0) {

                          this.backgroundBlur.setVisibility(View.VISIBLE);
                        this.min = i;
                        if (Build.VERSION.SDK_INT >= 16) {
                                this.backgroundBlur.setImageAlpha(i);
                            return;
                        } else {
                              this.backgroundBlur.setAlpha(i);
                            return;
                        }
                    } else {
                          this.backgroundBlur.setVisibility(View.GONE);
                        return;
                    }
                case R.id.seek_tailys /* 2131296967 */:
                     this.backgroundBlur.setVisibility(View.GONE);
                    this.seekValue = i;
                    addTilesBG(this.curTileId);
                    return;
                default:
                    return;
            }
        } else {
            int childCount7 = txtStkrRel.getChildCount();
            while (i2 < childCount7) {
                View childAt7 = txtStkrRel.getChildAt(i2);
                if (childAt7 instanceof StickerView) {
                    StickerView stickerView2 = (StickerView) childAt7;
                    if (stickerView2.getBorderVisbilty()) {
                        stickerView2.setHueProg(i);
                    }
                }
                i2++;
            }
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.alpha_seekBar /* 2131296348 */:
            case R.id.hue_seekBar /* 2131296653 */:
            case R.id.seekBar2 /* 2131296959 */:
                saveBitmapUndu();
                return;
            case R.id.seek_tailys /* 2131296967 */:
                this.backgroundBlur.setVisibility(View.GONE);
                return;
            default:
                return;
        }
    }


    private void touchDown(View view, String str) {
        this.leftRightShadow = 0;
        this.topBottomShadow = 0;
        this.focusedView = view;
        if (str.equals("hideboder")) {
            removeImageViewControll();
        }
        hideSlideBar();
        if (view instanceof StickerView) {
            this.layEffects.setVisibility(View.GONE);
            this.layTextMain.setVisibility(View.GONE);
            this.layStkrMain.setVisibility(View.GONE);
            StickerView stickerView = (StickerView) view;
            this.stkrColorSet = stickerView.getColor();
            this.alphaSeekbar.setProgress(stickerView.getAlphaProg());
            this.hueSeekbar.setProgress(stickerView.getHueProg());
        }
        if (view instanceof AutofitTextRel) {
            this.layEffects.setVisibility(View.GONE);
            this.layStkrMain.setVisibility(View.GONE);
            this.layTextMain.setVisibility(View.GONE);
            AutofitTextRel autofitTextRel = (AutofitTextRel) view;
            this.textColorSet = autofitTextRel.getTextColor();
            this.fontName = autofitTextRel.getFontName();
            this.tColor = autofitTextRel.getTextColor();
            this.shadowColor = autofitTextRel.getTextShadowColor();
            this.shadowProg = autofitTextRel.getTextShadowProg();
            this.tAlpha = autofitTextRel.getTextAlpha();
            this.bgDrawable = autofitTextRel.getBgDrawable();
            this.bgAlpha = autofitTextRel.getBgAlpha();
            this.rotation = view.getRotation();
            this.bgColor = autofitTextRel.getBgColor();
            if (this.bgDrawable.equals("0") || this.bgAlpha == 0) {
                this.adaptorTxtBg.setSelected(500);
            } else {
                this.adaptorTxtBg.setSelected(Integer.parseInt(this.bgDrawable.replace("btxt", "")));
            }
            this.verticalSeekBar.setProgress(this.tAlpha);
            this.seekBarShadow.setProgress(this.shadowProg);
            this.seekBar3.setProgress(this.bgAlpha);
        }
        if (this.guideline.getVisibility() == View.GONE) {
            this.guideline.setVisibility(View.VISIBLE);
        }
    }

    private void hideSlideBar() {
        if (layContainer.getVisibility() == View.VISIBLE) {
            layContainer.animate().translationX(-layContainer.getRight()).setDuration(200L).setInterpolator(new AccelerateInterpolator()).start();
            new Handler().postDelayed(new Runnable() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.44
                @Override // java.lang.Runnable
                public void run() {
                    CreateCardActivity.layContainer.setVisibility(View.GONE);
                    CreateCardActivity.btnLayControls.setVisibility(View.VISIBLE);
                }
            }, 200L);
        }
    }

    private void touchMove(View view) {
        boolean z = view instanceof StickerView;
        if (z) {
            StickerView stickerView = (StickerView) view;
            this.alphaSeekbar.setProgress(stickerView.getAlphaProg());
            this.hueSeekbar.setProgress(stickerView.getHueProg());
        } else {
            this.layStkrMain.setVisibility(View.GONE);
            this.layBackground.setVisibility(View.GONE);
        }
        if (z) {
            this.layEffects.setVisibility(View.GONE);
            this.layTextMain.setVisibility(View.GONE);
            this.layStkrMain.setVisibility(View.GONE);
            this.layBackground.setVisibility(View.GONE);
            removeScroll();
        }
        if (view instanceof AutofitTextRel) {
            this.layEffects.setVisibility(View.GONE);
            this.layTextMain.setVisibility(View.GONE);
            this.layStkrMain.setVisibility(View.GONE);
            this.layBackground.setVisibility(View.GONE);
            removeScroll();
        }
    }

    private void touchUp(final View view) {
        if (this.focusedCopy != this.focusedView) {
            this.seekbarContainer.setVisibility(View.VISIBLE);
            this.layTextEdit.setVisibility(View.VISIBLE);
        }
        if (view instanceof AutofitTextRel) {
            this.rotation = view.getRotation();
            if (this.layTextMain.getVisibility() == View.GONE) {
                this.layTextMain.setVisibility(View.VISIBLE);
                this.layTextMain.startAnimation(this.animSlideUp);
                this.layTextMain.post(new Runnable() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.45
                    @Override // java.lang.Runnable
                    public void run() {
                        CreateCardActivity.this.stickerScrollView(view);
                    }
                });
            }
            int i = this.processs;
            if (i != 0) {
                this.verticalSeekBar.setProgress(i);
            }
        }
        if ((view instanceof StickerView) && this.layStkrMain.getVisibility() == View.GONE) {
            if (("" + ((StickerView) view).getColorType()).equals("white")) {
                this.layColor.setVisibility(View.VISIBLE);
                this.layHue.setVisibility(View.GONE);
            } else {
                this.layColor.setVisibility(View.GONE);
                this.layHue.setVisibility(View.VISIBLE);
            }
            this.layStkrMain.setVisibility(View.VISIBLE);
            this.layStkrMain.startAnimation(this.animSlideUp);
            this.layStkrMain.post(new Runnable() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.46
                @Override // java.lang.Runnable
                public void run() {
                    CreateCardActivity.this.stickerScrollView(view);
                }
            });
        }
        if (this.guideline.getVisibility() == View.VISIBLE) {
            this.guideline.setVisibility(View.GONE);
        }
        if (this.seekbarContainer.getVisibility() == View.GONE) {
            this.seekbarContainer.startAnimation(this.animSlideDown);
            this.seekbarContainer.setVisibility(View.GONE);
        }
    }

    @Override


    public void onDelete() {
        removeScroll();
        if (this.layStkrMain.getVisibility() == View.VISIBLE) {
            this.layStkrMain.startAnimation(this.animSlideDown);
            this.layStkrMain.setVisibility(View.GONE);
        }
        if (this.layTextMain.getVisibility() == View.GONE) {
            this.layTextMain.startAnimation(this.animSlideDown);
            this.layTextMain.setVisibility(View.GONE);
        }
        this.guideline.setVisibility(View.GONE);
        int childCount = txtStkrRel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = txtStkrRel.getChildAt(i);
            if ((childAt instanceof AutofitTextRel) && ((AutofitTextRel) childAt).getBorderVisibility()) {
                txtStkrRel.removeView(childAt);
            }
            if ((childAt instanceof StickerView) && ((StickerView) childAt).getBorderVisbilty()) {
                txtStkrRel.removeView(childAt);
            }
        }
        saveBitmapUndu();
    }


    @Override

    public void onRotateDown(View view) {
        touchDown(view, "viewboder");
    }

    @Override

    public void onRotateMove(View view) {
        touchMove(view);
    }

    @Override

    public void onRotateUp(View view) {
        touchUp(view);
    }

    @Override

    public void onScaleDown(View view) {
        touchDown(view, "viewboder");
    }

    @Override

    public void onScaleMove(View view) {
        touchMove(view);
    }

    @Override

    public void onScaleUp(View view) {
        touchUp(view);
    }

    @Override
    public void onTouchDown(View view) {
        touchDown(view, "hideboder");
        if (this.checkTouchContinue) {
            this.layStkrMain.post(new Runnable() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.47
                @Override // java.lang.Runnable
                public void run() {
                    CreateCardActivity.this.checkTouchContinue = true;
                    CreateCardActivity.this.mHandler.post(CreateCardActivity.this.mStatusChecker);
                }
            });
        }
    }

    @Override
    public void onTouchMove(View view) {
        touchMove(view);
    }

    @Override
    public void onTouchUp(View view) {
        this.checkTouchContinue = false;
        this.mHandler.removeCallbacks(this.mStatusChecker);
        touchUp(view);
    }

    @Override

    public void onTouchMoveUpClick(View view) {
        saveBitmapUndu();
    }

    @Override
    public void onDoubleTap() {
        doubleTabPrass();
    }

    private void doubleTabPrass() {
        this.editMode = true;
        try {
            RelativeLayout relativeLayout = txtStkrRel;
            if (relativeLayout.getChildAt(relativeLayout.getChildCount() - 1) instanceof AutofitTextRel) {
                RelativeLayout relativeLayout2 = txtStkrRel;
                TextInfo textInfo = ((AutofitTextRel) relativeLayout2.getChildAt(relativeLayout2.getChildCount() - 1)).getTextInfo();
                this.layEffects.setVisibility(View.GONE);
                this.layStkrMain.setVisibility(View.GONE);
                this.layTextMain.setVisibility(View.GONE);
                addTextDialog(textInfo);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void removeImageViewControll() {
        this.guideline.setVisibility(View.GONE);
        RelativeLayout relativeLayout = txtStkrRel;
        if (relativeLayout != null) {
            int childCount = relativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = txtStkrRel.getChildAt(i);
                if (childAt instanceof AutofitTextRel) {
                    ((AutofitTextRel) childAt).setBorderVisibility(false);
                }
                if (childAt instanceof StickerView) {
                    ((StickerView) childAt).setBorderVisibility(false);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override
    // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        Intent intent2;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            this.layStkrMain.setVisibility(View.GONE);
            if (intent != null || i == SELECT_PICTURE_CAMERA || i == 4 || i == TEXT_ACTIVITY) {
                Bundle bundle = null;
                if (i == TEXT_ACTIVITY) {
                    bundle = intent.getExtras();
                    TextInfo textInfo = new TextInfo();
                    textInfo.setPOS_X(bundle.getFloat("X", 0.0f));
                    textInfo.setPOS_Y(bundle.getFloat("Y", 0.0f));
                    textInfo.setWIDTH(bundle.getInt("wi", ImageUtils.dpToPx((Context) this, 200)));
                    textInfo.setHEIGHT(bundle.getInt("he", ImageUtils.dpToPx((Context) this, 200)));
                    textInfo.setTEXT(bundle.getString("text", ""));
                    textInfo.setFONT_NAME(bundle.getString("fontName", ""));
                    textInfo.setTEXT_COLOR(bundle.getInt("tColor", Color.parseColor("#4149b6")));
                    textInfo.setTEXT_ALPHA(bundle.getInt("tAlpha", 100));
                    textInfo.setSHADOW_COLOR(bundle.getInt("shadowColor", Color.parseColor("#7641b6")));
                    textInfo.setSHADOW_PROG(bundle.getInt("shadowProg", 5));
                    textInfo.setBG_COLOR(bundle.getInt("bgColor", 0));
                    textInfo.setBG_DRAWABLE(bundle.getString("bgDrawable", "0"));
                    textInfo.setBG_ALPHA(bundle.getInt("bgAlpha", 255));
                    textInfo.setROTATION(bundle.getFloat(Key.ROTATION, 0.0f));
                    textInfo.setFIELD_TWO(bundle.getString("field_two", ""));
                    Log.e("double tab 22", "" + bundle.getFloat("X", 0.0f) + " ," + bundle.getFloat("Y", 0.0f));
                    this.fontName = bundle.getString("fontName", "");
                    this.tColor = bundle.getInt("tColor", Color.parseColor("#4149b6"));
                    this.shadowColor = bundle.getInt("shadowColor", Color.parseColor("#7641b6"));
                    this.shadowProg = bundle.getInt("shadowProg", 0);
                    this.tAlpha = bundle.getInt("tAlpha", 100);
                    this.bgDrawable = bundle.getString("bgDrawable", "0");
                    this.bgAlpha = bundle.getInt("bgAlpha", 255);
                    this.rotation = bundle.getFloat(Key.ROTATION, 0.0f);
                    this.bgColor = bundle.getInt("bgColor", 0);
                    if (this.editMode) {
                        RelativeLayout relativeLayout = txtStkrRel;
                        ((AutofitTextRel) relativeLayout.getChildAt(relativeLayout.getChildCount() - 1)).setTextInfo(textInfo, false);
                        RelativeLayout relativeLayout2 = txtStkrRel;
                        ((AutofitTextRel) relativeLayout2.getChildAt(relativeLayout2.getChildCount() - 1)).setBorderVisibility(true);
                        this.editMode = false;
                    } else {
                        this.verticalSeekBar.setProgress(100);
                        this.seekBarShadow.setProgress(0);
                        this.seekBar3.setProgress(255);
                        AutofitTextRel autofitTextRel = new AutofitTextRel((Context) this, false);
//                        txtStkrRel.addView(autofitTextRel);
                        autofitTextRel.setTextInfo(textInfo, false);
                        autofitTextRel.setId(ViewIdGenerator.generateViewId());
                        autofitTextRel.setOnTouchCallbackListener(this);
                        autofitTextRel.setBorderVisibility(true);
                    }
                    if (this.layTextMain.getVisibility() == View.GONE) {
                        this.layTextMain.setVisibility(View.VISIBLE);
                        this.layTextMain.startAnimation(this.animSlideUp);
                    }
                }
                if (i == SELECT_PICTURE_GALLERY) {
                    try {
                        File cacheDir = getCacheDir();
                        Uri fromFile = Uri.fromFile(new File(cacheDir, "SampleCropImage" + System.currentTimeMillis() + ".png"));
                        UCrop.Options options = new UCrop.Options();
                        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                        options.setFreeStyleCropEnabled(true);
                        UCrop.of(intent.getData(), fromFile).withOptions(options).start(this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (i == SELECT_PICTURE_CAMERA) {
                    try {
                        File cacheDir2 = getCacheDir();
                        Uri fromFile2 = Uri.fromFile(new File(cacheDir2, "SampleCropImage" + System.currentTimeMillis() + ".png"));
                        UCrop.Options options2 = new UCrop.Options();
                        options2.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                        options2.setFreeStyleCropEnabled(true);
                        UCrop.of(FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", this.f), fromFile2).withOptions(options2).start(this);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                if (i == SELECT_PICTURE_FROM_GALLERY_BACKGROUND) {
                    try {
                        if (this.layBackground.getVisibility() == View.VISIBLE) {
                            this.layBackground.startAnimation(this.animSlideDown);
                            this.layBackground.setVisibility(View.GONE);
                        }
                       this.screenWidth = backgroundImage.getWidth();
                        this.screenHeight = backgroundImage.getHeight();
                        AllConstants.bitmap = ImageUtils.scaleCenterCrop(AllConstants.getBitmapFromUri(
                                this, intent.getData(), this.screenWidth, this.screenHeight), (int) this.screenHeight, (int) this.screenWidth);
                        this.showtailsSeek = false;
                        this.ratio = "";
                        this.position = "1";
                        this.profile = "Temp_Path";
                        this.hex = "";
                        setImageBitmapAndResizeLayout(AllConstants.bitmap, "nonCreated");
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                if (i2 == -1 && i == 69) {
                    intent2 = intent;
                    handleCropResult(intent2);
                } else {
                    intent2 = intent;
                    if (i2 == 96) {
                        UCrop.getError(intent);
                    }
                }
                if (i == 4) {
                    openCustomActivity(bundle, intent2);
                    return;
                }
                return;
            }
            new AlertDialog.Builder(this, 16974126).setMessage(AllConstants.getSpannableString(this,
                    Typeface.DEFAULT, R.string.picUpImg)).setPositiveButton(AllConstants.getSpannableString(this,
                    Typeface.DEFAULT, R.string.ok), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i3) {
                    dialogInterface.cancel();
                }
            }).create().show();
        } else if (i == TEXT_ACTIVITY) {
            this.editMode = false;
        }
    }

    private void handleCropResult(Intent intent) {
        Uri output = UCrop.getOutput(intent);
        this.layBackground.setVisibility(View.GONE);
        this.imgOK.setVisibility(View.VISIBLE);
        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            getSupportFragmentManager().popBackStack();
        }
        if (output != null) {
            if (this.isBackground) {
                this.profile = "no";
                this.showtailsSeek = false;
                this.position = "1";
                this.profile = "Temp_Path";
                this.hex = "";
                try {
                    if (this.seekbarContainer.getVisibility() == View.GONE) {
                        this.seekbarContainer.setVisibility(View.VISIBLE);
                        this.seekbarContainer.startAnimation(this.animSlideUp);
                    }
                    String str = this.ratio;
                    String str2 = this.profile;
                    float f = this.screenWidth;
                    float f2 = this.screenHeight;
                    if (f <= f2) {
                        f = f2;
                    }
                    bitmapRatio(str, str2, ImageUtils.getResampleImageBitmap(output, this, (int) f), "nonCreated");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                this.colorType = "colored";
                addSticker("", output.getPath(), null);
            }
        }
        this.isBackground = false;
    }

    public void openCustomActivity(Bundle bundle, Intent intent) {
        intent.getExtras();
        this.profile = "no";
        this.showtailsSeek = false;
        this.ratio = "";
        this.position = "1";
        this.profile = "Temp_Path";
        this.hex = "";
        setImageBitmapAndResizeLayout(ImageUtils.resizeBitmap(AllConstants.bitmap, (int) this.screenWidth, (int) this.screenHeight), "nonCreated");
    }

    public void onGalleryButtonClick() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.PICK");
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)), SELECT_PICTURE_GALLERY);
    }

    public void onCameraButtonClick() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        this.f = new File(Configure.GetFileDir(this).getPath(), ".temp.jpg");
        intent.putExtra("output", FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", this.f));
        startActivityForResult(intent, SELECT_PICTURE_CAMERA);
    }

    public void onGalleryBackground() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), SELECT_PICTURE_FROM_GALLERY_BACKGROUND);
    }

    private void colorPickerDialog(boolean z) {
        new AmbilWarnaDialog(this, this.bColor, z, new AmbilWarnaDialog.OnAmbilWarnaListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.49
            @Override // yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
            }

            @Override // yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                CreateCardActivity.this.updateBackgroundColor(i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBackgroundColor(int i) {
        this.layBackground.setVisibility(View.GONE);
        this.imgOK.setVisibility(View.VISIBLE);
        if (this.layBackground.getVisibility() == View.VISIBLE) {
            this.layBackground.startAnimation(this.animSlideDown);
            this.layBackground.setVisibility(View.GONE);
        }
        Bitmap createBitmap = Bitmap.createBitmap(720, 1280, Bitmap.Config.ARGB_8888);
        createBitmap.eraseColor(i);
        Log.e(TAG, "updateColor: ");
        try {
          this.screenWidth = backgroundImage.getWidth();
            float height = backgroundImage.getHeight();
            this.screenHeight = 100;
            AllConstants.bitmap = ImageUtils.scaleCenterCrop(createBitmap, (int) 100, (int) this.screenWidth);
            this.showtailsSeek = false;
            this.position = "1";
            this.profile = "Temp_Path";
            this.hex = "";
            setImageBitmapAndResizeLayout(AllConstants.bitmap, "nonCreated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            getSupportFragmentManager().popBackStack();
        } else if (this.viewAllFrame.getVisibility() == View.VISIBLE) {
            closeViewAll();
        } else if (this.layTextMain.getVisibility() == View.VISIBLE) {
            if (this.layTextEdit.getVisibility() == View.VISIBLE) {
                hideTextResContainer();
                removeScroll();
                return;
            }
            showBackDialog();
        } else if (this.laySticker.getVisibility() == View.VISIBLE) {
            this.laySticker.setVisibility(View.GONE);
            this.addSticker.setBackgroundResource(R.drawable.trans);
            btnLayControls.setVisibility(View.VISIBLE);
        } else if (this.layStkrMain.getVisibility() == View.VISIBLE) {
            if (this.seekbarContainer.getVisibility() == View.VISIBLE) {
                hideResContainer();
                removeScroll();
                return;
            }
            showBackDialog();
        } else if (this.layEffects.getVisibility() == View.VISIBLE) {
            this.layEffects.startAnimation(this.animSlideDown);
            this.layEffects.setVisibility(View.GONE);
        } else if (this.layBackground.getVisibility() == View.VISIBLE) {
            this.layBackground.startAnimation(this.animSlideDown);
            this.layBackground.setVisibility(View.GONE);
        } else if (layContainer.getVisibility() == View.VISIBLE) {
            layContainer.animate().translationX(-layContainer.getRight()).setDuration(200L).setInterpolator(new AccelerateInterpolator()).start();
            new Handler().postDelayed(new Runnable() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.50
                @Override // java.lang.Runnable
                public void run() {
                    CreateCardActivity.layContainer.setVisibility(View.GONE);
                    CreateCardActivity.btnLayControls.setVisibility(View.VISIBLE);
                }
            }, 200L);
        } else {
            showBackDialog();
        }
    }

    private void showBackDialog() {
//        final Dialog dialog = new Dialog(this, );
//        dialog.setCancelable(true);
//        dialog.setContentView(R.layout.leave_popup);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        Button button = (Button) dialog.findViewById(R.id.btn_yes);
//        Button button2 = (Button) dialog.findViewById(R.id.btn_no);
//        ((TextView) dialog.findViewById(R.id.txt_free)).setTypeface(setBoldFont());
//        button.setTypeface(setBoldFont());
//        button2.setTypeface(setBoldFont());
//        button.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.51
//            @Override // android.view.View.OnClickListener
//            public void onClick(View view) {
        CreateCardActivity.this.finish();
        //  dialog.dismiss();
//            }
//        });
//        button2.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.52
//            @Override // android.view.View.OnClickListener
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.53
//            @Override // android.content.DialogInterface.OnDismissListener
//            public void onDismiss(DialogInterface dialogInterface) {
//            }
//        });
//        dialog.show();
    }

    private void showPicImageDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.img_pic_dialog);
        ((TextView) dialog.findViewById(R.id.permission_des)).setTypeface(setNormalFont());
        ((TextView) dialog.findViewById(R.id.txtTitle)).setTypeface(setBoldFont());
        ((ImageView) dialog.findViewById(R.id.iv_gallery)).setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.54
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CreateCardActivity.this.requestGalleryPermission();
                dialog.dismiss();
            }
        });
        ((ImageView) dialog.findViewById(R.id.iv_camera)).setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.55
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CreateCardActivity.this.requestCameraPermission();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestCameraPermission() {
        Dexter.withActivity(this).withPermissions("android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.57
            @Override // com.karumi.dexter.listener.multi.MultiplePermissionsListener
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    CreateCardActivity.this.onCameraButtonClick();
                }
                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                    CreateCardActivity.this.showSettingsDialog();
                }
            }

            @Override // com.karumi.dexter.listener.multi.MultiplePermissionsListener
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.56
            @Override // com.karumi.dexter.listener.PermissionRequestErrorListener
            public void onError(DexterError dexterError) {
                Toast.makeText(CreateCardActivity.this.getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
            }
        }).onSameThread().check();
    }

    @Override // com.invitationmaker.lss.photomaker.main.OnSetImageSticker
    public void ongetSticker() {
        this.colorType = "colored";
        addSticker("", "", AllConstants.bitmap);
    }

    @Override // com.invitationmaker.lss.photomaker.main.GetColorListener
    public void onColor(int i, String str, int i2) {
        if (i != 0) {
            int childCount = txtStkrRel.getChildCount();
            int i3 = 0;
            if (str.equals("txtShadow")) {
                while (i3 < childCount) {
                    View childAt = txtStkrRel.getChildAt(i3);
                    if (childAt instanceof AutofitTextRel) {
                        ((AutofitTextRel) txtStkrRel.getChildAt(i2)).setBorderVisibility(true);
                        AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                        if (autofitTextRel.getBorderVisibility()) {
                            this.shadowColor = i;
                            autofitTextRel.setTextShadowColor(i);
                        }
                    }
                    i3++;
                }
            } else if (str.equals("txtBg")) {
                while (i3 < childCount) {
                    View childAt2 = txtStkrRel.getChildAt(i3);
                    if (childAt2 instanceof AutofitTextRel) {
                        ((AutofitTextRel) txtStkrRel.getChildAt(i2)).setBorderVisibility(true);
                        AutofitTextRel autofitTextRel2 = (AutofitTextRel) childAt2;
                        if (autofitTextRel2.getBorderVisibility()) {
                            this.bgColor = i;
                            this.bgDrawable = "0";
                            autofitTextRel2.setBgColor(i);
                            autofitTextRel2.setBgAlpha(this.seekBar3.getProgress());
                        }
                    }
                    i3++;
                }
            } else {
                View childAt3 = txtStkrRel.getChildAt(i2);
                if (childAt3 instanceof AutofitTextRel) {
                    ((AutofitTextRel) txtStkrRel.getChildAt(i2)).setBorderVisibility(true);
                    AutofitTextRel autofitTextRel3 = (AutofitTextRel) childAt3;
                    if (autofitTextRel3.getBorderVisibility()) {
                        this.tColor = i;
                        this.textColorSet = i;
                        autofitTextRel3.setTextColor(i);
                    }
                }
                if (childAt3 instanceof StickerView) {
                    ((StickerView) txtStkrRel.getChildAt(i2)).setBorderVisibility(true);
                    StickerView stickerView = (StickerView) childAt3;
                    if (stickerView.getBorderVisbilty()) {
                        this.stkrColorSet = i;
                        stickerView.setColor(i);
                    }
                }
            }
        } else {
            removeScroll();
            if (this.layTextMain.getVisibility() == View.VISIBLE) {
                this.layTextMain.startAnimation(this.animSlideDown);
                this.layTextMain.setVisibility(View.GONE);
            }
            if (this.layStkrMain.getVisibility() == View.VISIBLE) {
                this.layStkrMain.startAnimation(this.animSlideDown);
                this.layStkrMain.setVisibility(View.GONE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void errorDialogTempInfo() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.error_dialog);
        ((TextView) dialog.findViewById(R.id.txterorr)).setTypeface(this.ttfHeader);
        ((TextView) dialog.findViewById(R.id.txt)).setTypeface(this.ttf);
        Button button = (Button) dialog.findViewById(R.id.btn_ok_e);
        button.setTypeface(this.ttf);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.58
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CreateCardActivity.this.finish();
            }
        });
        Button button2 = (Button) dialog.findViewById(R.id.btn_conti);
        button2.setTypeface(this.ttf);
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.59
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override
    // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        freeMemory();
    }

    public void freeMemory() {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.bitmap = null;
        }
        Bitmap bitmap2 = imgBtmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
            imgBtmap = null;
        }
        Bitmap bitmap3 = withoutWatermark;
        if (bitmap3 != null) {
            bitmap3.recycle();
            withoutWatermark = null;
        }
        Bitmap bitmap4 = btmSticker;
        if (bitmap4 != null) {
            bitmap4.recycle();
            btmSticker = null;
        }
        try {
            new Thread(new Runnable() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.60
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Glide.get(CreateCardActivity.this).clearDiskCache();
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            Glide.get(this).clearMemory();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
        }
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    private void fackClick() {
        this.layEffects.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.61
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.layTextEdit.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.62
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.seekbarContainer.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.63
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.seekbarHandle.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.64
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.seekLetterSpacing.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.65
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.seekLineSpacing.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.66
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.verticalSeekBar.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.67
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.seekBarShadow.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.68
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.seekShadowBlur.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.69
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.seekBar3.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.70
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.seekBlur.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.71
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.seek.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.CreateCardActivity.72
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
    }

    public void selectControl1() {
        this.txtTextControls.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
        this.txtFontsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsStyle.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsCurve.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtColorsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtShadowControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtBgControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
    }

    public void selectControl2() {
        this.txtTextControls.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
        this.txtFontsStyle.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsSpacing.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsCurve.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtColorsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtShadowControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtBgControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
    }

    public void selectControl3() {
        this.txtTextControls.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsStyle.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
        this.txtFontsSpacing.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsCurve.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtColorsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtShadowControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtBgControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
    }

    public void selectControl4() {
        this.txtTextControls.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsStyle.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
        this.txtFontsSpacing.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsCurve.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtColorsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtShadowControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtBgControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
    }

    public void selectControl5() {
        this.txtTextControls.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsStyle.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsSpacing.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsCurve.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
        this.txtColorsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtShadowControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtBgControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
    }

    public void selectControl6() {
        this.txtTextControls.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsStyle.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsSpacing.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsCurve.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtColorsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
        this.txtShadowControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtBgControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
    }

    public void selectControl7() {
        this.txtTextControls.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsStyle.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtColorsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsCurve.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsSpacing.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtShadowControl.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
        this.txtBgControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
    }

    public void selectControl8() {
        this.txtTextControls.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsStyle.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtColorsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsCurve.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsSpacing.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtShadowControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtBgControl.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
    }

    @Override // com.invitationmaker.lss.photomaker.listener.GetSnapListenerData
    public void onSnapFilter(ArrayList<BackgroundImage> arrayList, int i, String str) {
        if (i == 0) {
            seeMoreSticker(arrayList, str);
        } else {
            seeMore(arrayList);
        }
    }

    private void seeMoreSticker(ArrayList<BackgroundImage> arrayList, String str) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        StickerFragmentMore stickerFragmentMore = (StickerFragmentMore) supportFragmentManager.findFragmentByTag("sticker_list");
        if (stickerFragmentMore != null) {
            beginTransaction.remove(stickerFragmentMore);
        }
        beginTransaction.add(R.id.frameContainerSticker, StickerFragmentMore.newInstance(arrayList, str), "sticker_list");
        beginTransaction.addToBackStack("sticker_list");
        try {
            beginTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void seeMore(ArrayList<BackgroundImage> arrayList) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        BackgroundFragment2 backgroundFragment2 = (BackgroundFragment2) supportFragmentManager.findFragmentByTag("back_category_frgm_2");
        if (backgroundFragment2 != null) {
            beginTransaction.remove(backgroundFragment2);
        }
        beginTransaction.add(R.id.frameContainerBackground, BackgroundFragment2.newInstance(arrayList), "back_category_frgm_2");
        beginTransaction.addToBackStack("back_category_frgm_2");
        try {
            beginTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* loaded from: classes2.dex */
    public class BlurOperationTwoAsync extends AsyncTask<String, Void, String> {
        Activity context;

        /* JADX INFO: Access modifiers changed from: protected */
        public String doInBackground(String... strArr) {
            return "yes";
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
        }

        public BlurOperationTwoAsync(CreateCardActivity createCardActivity) {
            this.context = createCardActivity;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onPostExecute(String str) {
            CreateCardActivity.txtStkrRel.removeAllViews();
            if (CreateCardActivity.this.tempPath.equals("")) {
                LordStickersAsync lordStickersAsync = new LordStickersAsync();
                lordStickersAsync.execute("" + CreateCardActivity.this.templateId);
                return;
            }
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Invitation Stickers/category1");
            if (file.exists()) {
                if (file.listFiles().length >= 7) {
                    LordStickersAsync lordStickersAsync2 = new LordStickersAsync();
                    lordStickersAsync2.execute("" + CreateCardActivity.this.templateId);
                } else if (new File(CreateCardActivity.this.tempPath).exists()) {
                    LordStickersAsync lordStickersAsync3 = new LordStickersAsync();
                    lordStickersAsync3.execute("" + CreateCardActivity.this.templateId);
                } else {
                    LordStickersAsync lordStickersAsync4 = new LordStickersAsync();
                    lordStickersAsync4.execute("" + CreateCardActivity.this.templateId);
                }
            } else if (new File(CreateCardActivity.this.tempPath).exists()) {
                LordStickersAsync lordStickersAsync5 = new LordStickersAsync();
                lordStickersAsync5.execute("" + CreateCardActivity.this.templateId);
            } else {
                LordStickersAsync lordStickersAsync6 = new LordStickersAsync();
                lordStickersAsync6.execute("" + CreateCardActivity.this.templateId);
            }
        }
    }


    public class LordStickersAsync extends AsyncTask<String, String, Boolean> {
        private LordStickersAsync() {
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public Boolean doInBackground(String... strArr) {


            ArrayList<ElementInfo> arrayList;
            ArrayList<TextInfo> arrayList2;


            DatabaseHandler dbHandler = DatabaseHandler.getDbHandler(CreateCardActivity.this.getApplicationContext());
            if (CreateCardActivity.this.myDesignFlag == 0) {
                dbHandler.getComponentInfoList(CreateCardActivity.this.templateId, "SHAPE");
                arrayList2 = dbHandler.getTextInfoList(CreateCardActivity.this.templateId);
                arrayList = dbHandler.getComponentInfoList(CreateCardActivity.this.templateId, "STICKER");
            } else {
                arrayList2 = new ArrayList<>();
                arrayList = new ArrayList<>();
                int i = CreateCardActivity.this.posId;


                for (int i2 = 0; i2 < CreateCardActivity.this.stickerInfoArrayList.size(); i2++) {
                    CreateCardActivity createCardActivity = CreateCardActivity.this;
                    int newWidht = createCardActivity.getNewWidht(Float.valueOf(createCardActivity.stickerInfoArrayList.get(i2).getSt_x_pos()).floatValue(), Float.valueOf(CreateCardActivity.this.stickerInfoArrayList.get(i2).getSt_width()).floatValue());
                    CreateCardActivity createCardActivity2 = CreateCardActivity.this;
                    int newHeight = createCardActivity2.getNewHeight(Float.valueOf(createCardActivity2.stickerInfoArrayList.get(i2).getSt_y_pos()).floatValue(), Float.valueOf(CreateCardActivity.this.stickerInfoArrayList.get(i2).getSt_height()).floatValue());
                    int i3 = newWidht < 10 ? 20 : (newWidht <= 10 || newWidht > 20) ? newWidht : 35;
                    int i4 = newHeight < 10 ? 20 : (newHeight <= 10 || newHeight > 20) ? newHeight : 35;
                    String st_field2 = CreateCardActivity.this.stickerInfoArrayList.get(i2).getSt_field2() != null ? CreateCardActivity.this.stickerInfoArrayList.get(i2).getSt_field2() : "";
                    float parseFloat = (CreateCardActivity.this.stickerInfoArrayList.get(i2).getSt_rotation() == null || CreateCardActivity.this.stickerInfoArrayList.get(i2).getSt_rotation().equals("")) ? 0.0f : Float.parseFloat(CreateCardActivity.this.stickerInfoArrayList.get(i2).getSt_rotation());
                    int i5 = CreateCardActivity.this.postId;
                    CreateCardActivity createCardActivity3 = CreateCardActivity.this;
                    float xpos = createCardActivity3.getXpos(Float.valueOf(createCardActivity3.stickerInfoArrayList.get(i2).getSt_x_pos()).floatValue());
                    CreateCardActivity createCardActivity4 = CreateCardActivity.this;
                    arrayList.add(new ElementInfo(i5, xpos, createCardActivity4.getYpos(Float.valueOf(createCardActivity4.stickerInfoArrayList.get(i2).getSt_y_pos()).floatValue()), i3, i4, parseFloat, 0.0f, "", "STICKER", Integer.parseInt(CreateCardActivity.this.stickerInfoArrayList.get(i2).getSt_order()), 0, 255, 0, 0, 0, 0, CreateCardActivity.this.stickerInfoArrayList.get(i2).getSt_image(), "colored", 1, 0, st_field2, "", "", null, null));
                }


                for (int i6 = 0; i6 < CreateCardActivity.this.textInfoArrayList.size(); i6++) {
                    int i7 = CreateCardActivity.this.postId;
                    String text = CreateCardActivity.this.textInfoArrayList.get(i6).getText();
                    String font_family = CreateCardActivity.this.textInfoArrayList.get(i6).getFont_family();
                    int parseColor = Color.parseColor(CreateCardActivity.this.textInfoArrayList.get(i6).getTxt_color());
                    CreateCardActivity createCardActivity5 = CreateCardActivity.this;
                    float xpos2 = createCardActivity5.getXpos(Float.valueOf(createCardActivity5.textInfoArrayList.get(i6).getTxt_x_pos()).floatValue());
                    CreateCardActivity createCardActivity6 = CreateCardActivity.this;
                    float ypos = createCardActivity6.getYpos(Float.valueOf(createCardActivity6.textInfoArrayList.get(i6).getTxt_y_pos()).floatValue());
                    CreateCardActivity createCardActivity7 = CreateCardActivity.this;
                    int newWidht2 = createCardActivity7.getNewWidht(Float.valueOf(createCardActivity7.textInfoArrayList.get(i6).getTxt_x_pos()).floatValue(), Float.valueOf(CreateCardActivity.this.textInfoArrayList.get(i6).getTxt_width()).floatValue());
                    CreateCardActivity createCardActivity8 = CreateCardActivity.this;
                    arrayList2.add(new TextInfo(i7, text, font_family, parseColor, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, xpos2, ypos, newWidht2, createCardActivity8.getNewHeightText(Float.valueOf(createCardActivity8.textInfoArrayList.get(i6).getTxt_y_pos()).floatValue(), Float.valueOf(CreateCardActivity.this.textInfoArrayList.get(i6).getTxt_height()).floatValue()), Float.parseFloat(CreateCardActivity.this.textInfoArrayList.get(i6).getTxt_rotation()), "TEXT", Integer.parseInt(CreateCardActivity.this.textInfoArrayList.get(i6).getTxt_order()), 0, 0, 0, 0, 0, "", "", ""));

                }
            }


            dbHandler.close();
            CreateCardActivity.this.txtShapeList = new HashMap<>();


            Iterator<TextInfo> it = arrayList2.iterator();
            while (it.hasNext()) {
                TextInfo next = it.next();
                CreateCardActivity.this.txtShapeList.put(Integer.valueOf(next.getORDER()), next);
            }


            Iterator<ElementInfo> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ElementInfo next2 = it2.next();
                CreateCardActivity.this.txtShapeList.put(Integer.valueOf(next2.getORDER()), next2);
            }
            return true;


        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            CreateCardActivity.this.dialogIs.dismiss();


            ArrayList arrayList = new ArrayList(CreateCardActivity.this.txtShapeList.keySet());
            Collections.sort(arrayList);


            int size = arrayList.size();
            for (int i = 0; i < size; i++) {

                Object obj = CreateCardActivity.this.txtShapeList.get(arrayList.get(i));


                if (obj instanceof ElementInfo) {

                    ElementInfo elementInfo = (ElementInfo) obj;
                    String stkr_path = elementInfo.getSTKR_PATH();
                    if (stkr_path.equals("")) {


                        StickerView stickerView = new StickerView(CreateCardActivity.this);
                        CreateCardActivity.txtStkrRel.addView(stickerView);


                        stickerView.optimizeScreen(CreateCardActivity.this.screenWidth, CreateCardActivity.this.screenHeight);
                        stickerView.setMainLayoutWH(CreateCardActivity.this.mainRelative.getWidth(), CreateCardActivity.this.mainRelative.getHeight());
                        stickerView.setComponentInfo(elementInfo);
                        stickerView.setId(ViewIdGenerator.generateViewId());
                        stickerView.optimize(CreateCardActivity.this.wr, CreateCardActivity.this.hr);
                        stickerView.setOnTouchCallbackListener(CreateCardActivity.this);
                        stickerView.setBorderVisibility(false);
                        CreateCardActivity.this.sizeFull++;


                    } else {
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Invitation Stickers/category1");
                        if (!file.exists() && !file.mkdirs()) {
                            Log.d("", "Can't create directory to save image.");
                            CreateCardActivity createCardActivity = CreateCardActivity.this;
                            Toast.makeText(createCardActivity, createCardActivity.getResources().getString(R.string.create_dir_err), Toast.LENGTH_SHORT).show();
                            return;
                        } else if (new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Invitation Stickers/category1").exists()) {
                            File file2 = new File(stkr_path);
                            if (file2.exists()) {
                                StickerView stickerView2 = new StickerView(CreateCardActivity.this);
                                CreateCardActivity.txtStkrRel.addView(stickerView2);
                                stickerView2.optimizeScreen(CreateCardActivity.this.screenWidth, CreateCardActivity.this.screenHeight);
                                stickerView2.setMainLayoutWH(CreateCardActivity.this.mainRelative.getWidth(), CreateCardActivity.this.mainRelative.getHeight());
                                stickerView2.setComponentInfo(elementInfo);
                                stickerView2.setId(ViewIdGenerator.generateViewId());
                                stickerView2.optimize(CreateCardActivity.this.wr, CreateCardActivity.this.hr);
                                stickerView2.setOnTouchCallbackListener(CreateCardActivity.this);
                                stickerView2.setBorderVisibility(false);
                                CreateCardActivity.this.sizeFull++;
                            } else if (file2.getName().replace(".png", "").length() < 7) {
                                CreateCardActivity.this.dialogShow = false;
                                new SaveStickersAsync(obj).execute(stkr_path);
                            } else {
                                if (CreateCardActivity.this.OneShow) {
                                    CreateCardActivity.this.dialogShow = true;
                                    CreateCardActivity.this.errorDialogTempInfo();
                                    CreateCardActivity.this.OneShow = false;
                                }
                                CreateCardActivity.this.sizeFull++;
                            }
                        } else {
                            File file3 = new File(stkr_path);
                            if (file3.exists()) {
                                StickerView stickerView3 = new StickerView(CreateCardActivity.this);
                                CreateCardActivity.txtStkrRel.addView(stickerView3);
                                stickerView3.optimizeScreen(CreateCardActivity.this.screenWidth, CreateCardActivity.this.screenHeight);
                                stickerView3.setMainLayoutWH(CreateCardActivity.this.mainRelative.getWidth(), CreateCardActivity.this.mainRelative.getHeight());
                                stickerView3.setComponentInfo(elementInfo);
                                stickerView3.setId(ViewIdGenerator.generateViewId());
                                stickerView3.optimize(CreateCardActivity.this.wr, CreateCardActivity.this.hr);
                                stickerView3.setOnTouchCallbackListener(CreateCardActivity.this);
                                stickerView3.setBorderVisibility(false);
                                CreateCardActivity.this.sizeFull++;
                            } else if (file3.getName().replace(".png", "").length() < 7) {
                                CreateCardActivity.this.dialogShow = false;
                                new SaveStickersAsync(obj).execute(stkr_path);
                            } else {
                                if (CreateCardActivity.this.OneShow) {
                                    CreateCardActivity.this.dialogShow = true;
                                    CreateCardActivity.this.errorDialogTempInfo();
                                    CreateCardActivity.this.OneShow = false;
                                }
                                CreateCardActivity.this.sizeFull++;
                            }
                        }
                    }
                }





                else {


                    AutofitTextRel autofitTextRel = new AutofitTextRel((Context) CreateCardActivity.this, false);
                    CreateCardActivity.txtStkrRel.addView(autofitTextRel);


                    TextInfo textInfo = (TextInfo) obj;
                    autofitTextRel.setTextInfo(textInfo, false);
                    autofitTextRel.setId(ViewIdGenerator.generateViewId());
                    autofitTextRel.optimize(CreateCardActivity.this.wr, CreateCardActivity.this.hr);
                    autofitTextRel.setOnTouchCallbackListener(CreateCardActivity.this);
                    autofitTextRel.setBorderVisibility(false);







                    CreateCardActivity.this.fontName = textInfo.getFONT_NAME();
                    CreateCardActivity.this.tColor = textInfo.getTEXT_COLOR();
                    CreateCardActivity.this.shadowColor = textInfo.getSHADOW_COLOR();
                    CreateCardActivity.this.shadowProg = textInfo.getSHADOW_PROG();
                    CreateCardActivity.this.tAlpha = textInfo.getTEXT_ALPHA();
                    CreateCardActivity.this.bgDrawable = textInfo.getBG_DRAWABLE();
                    CreateCardActivity.this.bgAlpha = textInfo.getBG_ALPHA();
                    CreateCardActivity.this.rotation = textInfo.getROTATION();
                    CreateCardActivity.this.bgColor = textInfo.getBG_COLOR();
                    CreateCardActivity.this.sizeFull++;








                }
            }
            if (CreateCardActivity.this.txtShapeList.size() == CreateCardActivity.this.sizeFull && CreateCardActivity.this.dialogShow) {
                try {
                    CreateCardActivity.this.dialogIs.dismiss();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            if (!CreateCardActivity.this.overlayName.equals("")) {
                CreateCardActivity createCardActivity2 = CreateCardActivity.this;
                createCardActivity2.setBitmapOverlay(createCardActivity2.getResources().getIdentifier(CreateCardActivity.this.overlayName, "drawable", CreateCardActivity.this.getPackageName()));
            }
            CreateCardActivity.this.saveBitmapUndu();
        }
    }

    /* loaded from: classes2.dex */
    private class LordTemplateAsync extends AsyncTask<String, String, Boolean> {
        int indx;
        String postion;

        private LordTemplateAsync() {
            this.indx = 0;
            this.postion = "1";
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
            CreateCardActivity.this.dialogIs = new ProgressDialog(CreateCardActivity.this);
            CreateCardActivity.this.dialogIs.setMessage(CreateCardActivity.this.getResources().getString(R.string.plzwait));
            CreateCardActivity.this.dialogIs.setCancelable(false);
            CreateCardActivity.this.dialogIs.show();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public Boolean doInBackground(String... strArr) {
            if (CreateCardActivity.this.myDesignFlag == 0) {
                this.indx = Integer.parseInt(strArr[0]);
            } else {
                this.indx = 0;
            }
            TemplateInfo templateInfo = (TemplateInfo) CreateCardActivity.this.templateList.get(this.indx);
            CreateCardActivity.this.templateId = templateInfo.getTEMPLATE_ID();
            CreateCardActivity.this.frameName = templateInfo.getFRAME_NAME();
            CreateCardActivity.this.tempPath = templateInfo.getTEMP_PATH();
            CreateCardActivity.this.ratio = templateInfo.getRATIO();
            CreateCardActivity.this.profile = templateInfo.getPROFILE_TYPE();
            String seek_value = templateInfo.getSEEK_VALUE();
            CreateCardActivity.this.hex = templateInfo.getTEMPCOLOR();
            CreateCardActivity.this.overlayName = templateInfo.getOVERLAY_NAME();
            CreateCardActivity.this.overlayOpacty = templateInfo.getOVERLAY_OPACITY();
            CreateCardActivity.this.overlayBlur = templateInfo.getOVERLAY_BLUR();
            CreateCardActivity.this.seekValue = Integer.parseInt(seek_value);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            String valueOf = String.valueOf(Integer.parseInt(this.postion));
            if (((TemplateInfo) CreateCardActivity.this.templateList.get(this.indx)).getTYPE().equals("USER")) {
                if (CreateCardActivity.this.myDesignFlag != 0) {
                    CreateCardActivity createCardActivity = CreateCardActivity.this;
                    createCardActivity.drawBackgroundImage(createCardActivity.ratio, valueOf, CreateCardActivity.this.frameName, "created");
                    return;
                }
                CreateCardActivity createCardActivity2 = CreateCardActivity.this;
                createCardActivity2.drawBackgroundImageFromDp(createCardActivity2.ratio, valueOf, CreateCardActivity.this.tempPath, "created");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class SaveStickersAsync extends AsyncTask<String, String, Boolean> {
        Object objk;
        String stkr_path;

        public SaveStickersAsync(Object obj) {
            this.objk = obj;
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public Boolean doInBackground(String... strArr) {
            String str = strArr[0];
            this.stkr_path = ((ElementInfo) this.objk).getSTKR_PATH();
            try {
                Bitmap decodeResource = BitmapFactory.decodeResource(CreateCardActivity.this.getResources(), CreateCardActivity.this.getResources().getIdentifier(str, "drawable", CreateCardActivity.this.getPackageName()));
                if (decodeResource != null) {
                    return Boolean.valueOf(AllConstants.saveBitmapObject(CreateCardActivity.this, decodeResource, this.stkr_path));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            CreateCardActivity.this.sizeFull++;
            if (CreateCardActivity.this.txtShapeList.size() == CreateCardActivity.this.sizeFull) {
                CreateCardActivity.this.dialogShow = true;
            }
            if (bool.booleanValue()) {
                StickerView stickerView = new StickerView(CreateCardActivity.this);
                /*   CreateCardActivity.txtStkrRel.addView(stickerView);*/
                stickerView.optimizeScreen(CreateCardActivity.this.screenWidth, CreateCardActivity.this.screenHeight);
                stickerView.setMainLayoutWH(CreateCardActivity.this.mainRelative.getWidth(), CreateCardActivity.this.mainRelative.getHeight());
                stickerView.setComponentInfo((ElementInfo) this.objk);
                stickerView.setId(ViewIdGenerator.generateViewId());
                stickerView.optimize(CreateCardActivity.this.wr, CreateCardActivity.this.hr);
                stickerView.setOnTouchCallbackListener(CreateCardActivity.this);
                stickerView.setBorderVisibility(false);
            }
            if (CreateCardActivity.this.dialogShow) {
                CreateCardActivity.this.dialogIs.dismiss();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class SavebackgrundAsync extends AsyncTask<String, String, Boolean> {
        private String crted;
        private String profile;
        private String ratio;

        private SavebackgrundAsync() {
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public Boolean doInBackground(String... strArr) {
            String str = strArr[0];
            this.ratio = strArr[1];
            this.profile = strArr[2];
            this.crted = strArr[3];
            try {
                Bitmap decodeResource = BitmapFactory.decodeResource(CreateCardActivity.this.getResources(), CreateCardActivity.this.getResources().getIdentifier(str, "drawable", CreateCardActivity.this.getPackageName()));
                if (decodeResource != null) {
                    CreateCardActivity createCardActivity = CreateCardActivity.this;
                    return Boolean.valueOf(AllConstants.saveBitmapObject(createCardActivity, decodeResource, createCardActivity.tempPath));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if (bool.booleanValue()) {
                try {
                    CreateCardActivity createCardActivity = CreateCardActivity.this;
                    String str = this.ratio;
                    String str2 = this.profile;
                    Uri parse = Uri.parse(createCardActivity.tempPath);
                    CreateCardActivity createCardActivity2 = CreateCardActivity.this;
                    createCardActivity.bitmapRatio(str, str2, ImageUtils.getResampleImageBitmap(parse, createCardActivity2, (int) (createCardActivity2.screenWidth > CreateCardActivity.this.screenHeight ? CreateCardActivity.this.screenWidth : CreateCardActivity.this.screenHeight)), this.crted);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CreateCardActivity.txtStkrRel.removeAllViews();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveBitmapUndu() {
        try {
            this.tempID++;
            TemplateInfo templateInfo = new TemplateInfo();
            templateInfo.setTHUMB_URI("");
            templateInfo.setTEMPLATE_ID(this.tempID);
            templateInfo.setFRAME_NAME(this.frameName);
            templateInfo.setRATIO(this.ratio);
            templateInfo.setPROFILE_TYPE(this.profile);
            templateInfo.setSEEK_VALUE(String.valueOf(this.seekValue));
            templateInfo.setTYPE("USER");
            templateInfo.setTEMP_PATH(this.tempPath);
            templateInfo.setTEMPCOLOR(this.hex);
            templateInfo.setOVERLAY_NAME(this.overlayName);
            templateInfo.setOVERLAY_OPACITY(this.seek.getProgress());
            templateInfo.setOVERLAY_BLUR(this.seekBlur.getProgress());
            ArrayList<TextInfo> arrayList = new ArrayList<>();
            ArrayList<ElementInfo> arrayList2 = new ArrayList<>();
            int childCount = txtStkrRel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = txtStkrRel.getChildAt(i);
                if (childAt instanceof AutofitTextRel) {
                    TextInfo textInfo = ((AutofitTextRel) childAt).getTextInfo();
                    textInfo.setTEMPLATE_ID(this.templateId);
                    textInfo.setORDER(i);
                    textInfo.setTYPE("TEXT");
                    arrayList.add(textInfo);
                } else {
                    ElementInfo componentInfoUL = ((StickerView) txtStkrRel.getChildAt(i)).getComponentInfoUL();
                    componentInfoUL.setTEMPLATE_ID(this.templateId);
                    componentInfoUL.setTYPE("STICKER");
                    componentInfoUL.setORDER(i);
                    arrayList2.add(componentInfoUL);
                }
            }
            templateInfo.setTextInfoArrayList(arrayList);
            templateInfo.setElementInfoArrayList(arrayList2);
            this.templateListU_R.add(templateInfo);
            iconVisibility();
        } catch (Exception e) {
            Log.i("testing", "Exception " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable unused) {
        }
    }

    public void loadSaveUnduRedo(TemplateInfo templateInfo) {
        this.templateId = templateInfo.getTEMPLATE_ID();
        this.frameName = templateInfo.getFRAME_NAME();
        this.tempPath = templateInfo.getTEMP_PATH();
        this.ratio = templateInfo.getRATIO();
        this.profile = templateInfo.getPROFILE_TYPE();
        this.tempID = templateInfo.getTEMPLATE_ID();
        String seek_value = templateInfo.getSEEK_VALUE();
        this.hex = templateInfo.getTEMPCOLOR();
        this.overlayName = templateInfo.getOVERLAY_NAME();
        this.overlayOpacty = templateInfo.getOVERLAY_OPACITY();
        this.overlayBlur = templateInfo.getOVERLAY_BLUR();
        this.seekValue = Integer.parseInt(seek_value);
        this.textInfosU_R = templateInfo.getTextInfoArrayList();
        this.elementInfosU_R = templateInfo.getElementInfoArrayList();
        this.progressBarUndo.setVisibility(View.VISIBLE);
        this.btnRedo.setVisibility(View.GONE);
        this.btnUndo.setVisibility(View.GONE);
        LordStickersAsyncU_R lordStickersAsyncU_R = new LordStickersAsyncU_R();
        lordStickersAsyncU_R.execute("" + this.tempID);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class LordStickersAsyncU_R extends AsyncTask<String, String, Boolean> {
        private LordStickersAsyncU_R() {
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public Boolean doInBackground(String... strArr) {
            CreateCardActivity.this.txtShapeList = new HashMap<>();
            Iterator<TextInfo> it = CreateCardActivity.this.textInfosU_R.iterator();
            while (it.hasNext()) {
                TextInfo next = it.next();
                CreateCardActivity.this.txtShapeList.put(Integer.valueOf(next.getORDER()), next);
            }
            Iterator<ElementInfo> it2 = CreateCardActivity.this.elementInfosU_R.iterator();
            while (it2.hasNext()) {
                ElementInfo next2 = it2.next();
                CreateCardActivity.this.txtShapeList.put(Integer.valueOf(next2.getORDER()), next2);
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            CreateCardActivity.txtStkrRel.removeAllViews();
            ArrayList arrayList = new ArrayList(CreateCardActivity.this.txtShapeList.keySet());
            Collections.sort(arrayList);
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                Object obj = CreateCardActivity.this.txtShapeList.get(arrayList.get(i));
                if (obj instanceof ElementInfo) {
                    ElementInfo elementInfo = (ElementInfo) obj;
                    String stkr_path = elementInfo.getSTKR_PATH();
                    if (stkr_path.equals("")) {
                        StickerView stickerView = new StickerView((Context) CreateCardActivity.this, true);
//                        CreateCardActivity.txtStkrRel.addView(stickerView);
                        stickerView.optimizeScreen(CreateCardActivity.this.screenWidth, CreateCardActivity.this.screenHeight);
                        stickerView.setMainLayoutWH(CreateCardActivity.this.mainRelative.getWidth(), CreateCardActivity.this.mainRelative.getHeight());
                        stickerView.setComponentInfo(elementInfo);
                        stickerView.setId(ViewIdGenerator.generateViewId());
                        stickerView.optimize(CreateCardActivity.this.wr, CreateCardActivity.this.hr);
                        stickerView.setOnTouchCallbackListener(CreateCardActivity.this);
                        stickerView.setBorderVisibility(false);
                        CreateCardActivity.this.sizeFull++;
                    } else {
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Invitation Stickers/category1");
                        if (!file.exists() && !file.mkdirs()) {
                            Log.d("", "Can't create directory to save image.");
                            CreateCardActivity createCardActivity = CreateCardActivity.this;
                            Toast.makeText(createCardActivity, createCardActivity.getResources().getString(R.string.create_dir_err), Toast.LENGTH_SHORT).show();
                            return;
                        } else if (new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Invitation Stickers/category1").exists()) {
                            File file2 = new File(stkr_path);
                            if (file2.exists()) {
                                StickerView stickerView2 = new StickerView((Context) CreateCardActivity.this, true);
//                                CreateCardActivity.txtStkrRel.addView(stickerView2);
                                stickerView2.optimizeScreen(CreateCardActivity.this.screenWidth, CreateCardActivity.this.screenHeight);
                                stickerView2.setMainLayoutWH(CreateCardActivity.this.mainRelative.getWidth(), CreateCardActivity.this.mainRelative.getHeight());
                                stickerView2.setComponentInfo(elementInfo);
                                stickerView2.setId(ViewIdGenerator.generateViewId());
                                stickerView2.optimize(CreateCardActivity.this.wr, CreateCardActivity.this.hr);
                                stickerView2.setOnTouchCallbackListener(CreateCardActivity.this);
                                stickerView2.setBorderVisibility(false);
                                CreateCardActivity.this.sizeFull++;
                            } else if (file2.getName().replace(".png", "").length() < 7) {
                                CreateCardActivity.this.dialogShow = false;
                            } else {
                                if (CreateCardActivity.this.OneShow) {
                                    CreateCardActivity.this.dialogShow = true;
                                    CreateCardActivity.this.errorDialogTempInfo();
                                    CreateCardActivity.this.OneShow = false;
                                }
                                CreateCardActivity.this.sizeFull++;
                            }
                        } else {
                            File file3 = new File(stkr_path);
                            if (file3.exists()) {
                                StickerView stickerView3 = new StickerView((Context) CreateCardActivity.this, true);
//                                CreateCardActivity.txtStkrRel.addView(stickerView3);
                                stickerView3.optimizeScreen(CreateCardActivity.this.screenWidth, CreateCardActivity.this.screenHeight);
                                stickerView3.setMainLayoutWH(CreateCardActivity.this.mainRelative.getWidth(), CreateCardActivity.this.mainRelative.getHeight());
                                stickerView3.setComponentInfo(elementInfo);
                                stickerView3.setId(ViewIdGenerator.generateViewId());
                                stickerView3.optimize(CreateCardActivity.this.wr, CreateCardActivity.this.hr);
                                stickerView3.setOnTouchCallbackListener(CreateCardActivity.this);
                                stickerView3.setBorderVisibility(false);
                                CreateCardActivity.this.sizeFull++;
                            } else if (file3.getName().replace(".png", "").length() < 7) {
                                CreateCardActivity.this.dialogShow = false;
                            } else {
                                if (CreateCardActivity.this.OneShow) {
                                    CreateCardActivity.this.dialogShow = true;
                                    CreateCardActivity.this.errorDialogTempInfo();
                                    CreateCardActivity.this.OneShow = false;
                                }
                                CreateCardActivity.this.sizeFull++;
                            }
                        }
                    }
                } else {
                    AutofitTextRel autofitTextRel = new AutofitTextRel((Context) CreateCardActivity.this, true);
//                    CreateCardActivity.txtStkrRel.addView(autofitTextRel);
                    TextInfo textInfo = (TextInfo) obj;
                    autofitTextRel.setTextInfo(textInfo, false);
                    autofitTextRel.setId(ViewIdGenerator.generateViewId());
                    autofitTextRel.optimize(CreateCardActivity.this.wr, CreateCardActivity.this.hr);
                    autofitTextRel.setOnTouchCallbackListener(CreateCardActivity.this);
                    autofitTextRel.setBorderVisibility(false);
                    CreateCardActivity.this.fontName = textInfo.getFONT_NAME();
                    CreateCardActivity.this.tColor = textInfo.getTEXT_COLOR();
                    CreateCardActivity.this.shadowColor = textInfo.getSHADOW_COLOR();
                    CreateCardActivity.this.shadowProg = textInfo.getSHADOW_PROG();
                    CreateCardActivity.this.tAlpha = textInfo.getTEXT_ALPHA();
                    CreateCardActivity.this.bgDrawable = textInfo.getBG_DRAWABLE();
                    CreateCardActivity.this.bgAlpha = textInfo.getBG_ALPHA();
                    CreateCardActivity.this.rotation = textInfo.getROTATION();
                    CreateCardActivity.this.bgColor = textInfo.getBG_COLOR();
                    CreateCardActivity.this.sizeFull++;
                }
            }
            CreateCardActivity.this.progressBarUndo.setVisibility(View.GONE);
            CreateCardActivity.this.btnRedo.setVisibility(View.VISIBLE);
            CreateCardActivity.this.btnUndo.setVisibility(View.VISIBLE);
        }
    }

    public void undo() {
        if (this.layTextMain.getVisibility() == View.VISIBLE) {
            this.layTextMain.setVisibility(View.GONE);
        }
        if (this.layStkrMain.getVisibility() == View.VISIBLE) {
            this.layStkrMain.setVisibility(View.GONE);
        }
        if (this.templateListU_R.size() > 2) {
            this.btnUndo.setImageResource(R.drawable.undo);
        } else {
            this.btnUndo.setImageResource(R.drawable.undo_disable);
        }
        if (this.templateListU_R.size() > 1) {
            ArrayList<TemplateInfo> arrayList = this.templateListU_R;
            loadSaveUnduRedo(arrayList.get(arrayList.size() - 2));
            ArrayList<TemplateInfo> arrayList2 = this.templateListR_U;
            ArrayList<TemplateInfo> arrayList3 = this.templateListU_R;
            arrayList2.add(arrayList3.get(arrayList3.size() - 1));
            ArrayList<TemplateInfo> arrayList4 = this.templateListU_R;
            arrayList4.remove(arrayList4.get(arrayList4.size() - 1));
        }
        iconVisibility();
    }

    public void redo() {
        if (this.layTextMain.getVisibility() == View.VISIBLE) {
            this.layTextMain.setVisibility(View.GONE);
        }
        if (this.layStkrMain.getVisibility() == View.VISIBLE) {
            this.layStkrMain.setVisibility(View.GONE);
        }
        if (this.templateListR_U.size() > 1) {
            this.btnRedo.setImageResource(R.drawable.redo);
        } else {
            this.btnRedo.setImageResource(R.drawable.redo_disable);
        }
        if (this.templateListR_U.size() > 0) {
            ArrayList<TemplateInfo> arrayList = this.templateListR_U;
            loadSaveUnduRedo(arrayList.get(arrayList.size() - 1));
            ArrayList<TemplateInfo> arrayList2 = this.templateListU_R;
            ArrayList<TemplateInfo> arrayList3 = this.templateListR_U;
            arrayList2.add(arrayList3.get(arrayList3.size() - 1));
            ArrayList<TemplateInfo> arrayList4 = this.templateListR_U;
            arrayList4.remove(arrayList4.get(arrayList4.size() - 1));
        }
        iconVisibility();
    }

    public void iconVisibility() {
        if (this.templateListU_R.size() > 1) {
            this.btnUndo.setImageResource(R.drawable.undo);
        } else {
            this.btnUndo.setImageResource(R.drawable.undo_disable);
        }
        if (this.templateListR_U.size() > 0) {
            this.btnRedo.setImageResource(R.drawable.redo);
        } else {
            this.btnRedo.setImageResource(R.drawable.redo_disable);
        }
    }
}
