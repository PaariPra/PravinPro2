package com.chettapps.invitationmaker.photomaker.main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import com.chettapps.invitationmaker.R;
import com.google.gson.Gson;
import com.chettapps.invitationmaker.photomaker.InvitationApplication;

import com.chettapps.invitationmaker.photomaker.activity.BaseActivity;
import com.chettapps.invitationmaker.photomaker.adapter.RecyclerViewLoadMoreScroll;
import com.chettapps.invitationmaker.photomaker.adapter.VeticalViewBgAdapter;

import com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment1;
import com.chettapps.invitationmaker.photomaker.listener.GetSnapListener;
import com.chettapps.invitationmaker.photomaker.listener.OnLoadMoreListener;
import com.chettapps.invitationmaker.photomaker.network.NetworkConnectivityReceiver;
import com.chettapps.invitationmaker.photomaker.pojoClass.BackgroundImage;
import com.chettapps.invitationmaker.photomaker.pojoClass.MainBG;
import com.chettapps.invitationmaker.photomaker.pojoClass.Snap;
import com.chettapps.invitationmaker.photomaker.pojoClass.ThumbBG;
import com.chettapps.invitationmaker.photomaker.pojoClass.YourDataProvider;
import com.chettapps.invitationmaker.photomaker.utils.Config;
import com.chettapps.invitationmaker.photomaker.utils.Configure;
import com.chettapps.invitationmaker.photomaker.utils.PreferenceClass;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class BGImageActivity extends BaseActivity implements View.OnClickListener, GetSnapListener, GetSnapListenerS {
    private static final int SELECT_PICTURE_FROM_CAMERA = 9062;
    private static final int SELECT_PICTURE_FROM_GALLERY = 9072;
    public static final String TAG = "BackgrounImageActivity";
    private ImageView btnBack;
    private ImageView btnColorPicker;
    private ImageView btnGalleryPicker;
    private ImageView btnTakePicture;
    private ProgressDialog dialogIs;
    private File f;
    private int height;
    private ProgressBar loading_view;
    LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    private PreferenceClass preferenceClass;
    private SharedPreferences preferences;
    private String ratio;
    private Uri resultUri;
    float screenHeight;
    float screenWidth;
    private RecyclerViewLoadMoreScroll scrollListener;
    private int totalAds;
    private TextView txtTitle;
    private VeticalViewBgAdapter veticalViewAdapter;
    private int widht;
    YourDataProvider yourDataProvider;
    private final int bColor = Color.parseColor("#4149b6");
    private int cnt = 0;
    private final int isFirstLoad = 0;
    ArrayList<Object> snapData = new ArrayList<>();
    ArrayList<MainBG> thumbnail_bg = new ArrayList<>();

    @Override // com.chettapps.invitationmaker.photomaker.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        getWindow().setFlags(1024, 1024);
        super.onCreate(bundle);
        setContentView(R.layout.activity_bg_image);
        this.loading_view = (ProgressBar) findViewById(R.id.loading_view);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.background_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.mLinearLayoutManager = linearLayoutManager;
        this.mRecyclerView.setLayoutManager(linearLayoutManager);
        this.mRecyclerView.setHasFixedSize(true);
        Config.SaveInt("flow", 1, this);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        findViews();
        this.preferenceClass = new PreferenceClass(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;
        getBgImages();





    }





    private void getBgImages() {
        InvitationApplication instance = InvitationApplication.getInstance();
        instance.addToRequestQueue(new StringRequest(1, AllConstants.BASE_URL_POSTER_BG + "poster/backgroundlatest", new Response.Listener<String>() { // from class: com.chettapps.invitationmaker.photomaker.main.BGImageActivity.4
            public void onResponse(String str) {
                try {
                    ThumbBG thumbBG = (ThumbBG) new Gson().fromJson(str,  ThumbBG.class);
                    if (thumbBG != null && thumbBG.getThumbnail_bg() != null && thumbBG.getThumbnail_bg().size() > 0) {
                        BGImageActivity.this.thumbnail_bg = new ArrayList<>();
                        BGImageActivity.this.thumbnail_bg = thumbBG.getThumbnail_bg();
                        BGImageActivity.this.setupAdapter();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // from class: com.chettapps.invitationmaker.photomaker.main.BGImageActivity.5
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(BGImageActivity.TAG, "Error: " + volleyError.getMessage());
            }
        }) { // from class: com.chettapps.invitationmaker.photomaker.main.BGImageActivity.6
            @Override // com.android.volley.Request
            public Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("device", "1");
                return hashMap;
            }
        }, TAG);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setupAdapter() {
        for (int i = 0; i < this.thumbnail_bg.size(); i++) {
            this.snapData.add(new Snap(1, this.thumbnail_bg.get(i).getCategory_name(), this.thumbnail_bg.get(i).getCategory_list()));
        }
        this.loading_view.setVisibility(View.GONE);
        YourDataProvider yourDataProvider = new YourDataProvider();
        this.yourDataProvider = yourDataProvider;
        yourDataProvider.setPosterList(this.snapData);
        if (this.snapData != null) {
            VeticalViewBgAdapter veticalViewBgAdapter = new VeticalViewBgAdapter(this, this.yourDataProvider.getLoadMorePosterItems(), this.mRecyclerView, 1);
            this.veticalViewAdapter = veticalViewBgAdapter;
            this.mRecyclerView.setAdapter(veticalViewBgAdapter);
            RecyclerViewLoadMoreScroll recyclerViewLoadMoreScroll = new RecyclerViewLoadMoreScroll(this.mLinearLayoutManager);
            this.scrollListener = recyclerViewLoadMoreScroll;
            recyclerViewLoadMoreScroll.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.chettapps.invitationmaker.photomaker.main.BGImageActivity.7
                @Override // com.chettapps.invitationmaker.photomaker.listener.OnLoadMoreListener
                public void onLoadMore() {
                    BGImageActivity.this.LoadMoreData();
                }
            });
            this.mRecyclerView.addOnScrollListener(this.scrollListener);
        }
        this.loading_view.setVisibility(View.GONE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void LoadMoreData() {
        this.veticalViewAdapter.addLoadingView();
        new Handler().postDelayed(new Runnable() { // from class: com.chettapps.invitationmaker.photomaker.main.BGImageActivity.8
            @Override // java.lang.Runnable
            public void run() {
                BGImageActivity.this.veticalViewAdapter.removeLoadingView();
                BGImageActivity.this.veticalViewAdapter.addData(BGImageActivity.this.yourDataProvider.getLoadMorePosterItemsS());
                BGImageActivity.this.veticalViewAdapter.notifyDataSetChanged();
                BGImageActivity.this.scrollListener.setLoaded();
            }
        }, 3000L);
    }

    public File getCacheFolder(Context context) {
        File GetFileDir = Configure.GetFileDir(context);
        GetFileDir.mkdirs();
        return GetFileDir;
    }

    public void onGetPosition(String str) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.dialogIs = progressDialog;
        progressDialog.setMessage("Please wit");
        this.dialogIs.setCancelable(false);
        this.dialogIs.show();
        InvitationApplication.getInstance().addToRequestQueue(new ImageRequest(str, new GetPositionResponseListener(this, getCacheFolder(this)), 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565, new Response.ErrorListener() { // from class: com.chettapps.invitationmaker.photomaker.main.BGImageActivity.9
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                BGImageActivity.this.dialogIs.dismiss();
            }
        }));
    }

    public void onGetPositionResponseReceived(File file, Bitmap bitmap) {
        try {
            this.dialogIs.dismiss();
            try {
                File file2 = new File(file, "localFileName.png");
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                try {
                    Uri fromFile = Uri.fromFile(file2);
                    Uri fromFile2 = Uri.fromFile(new File(file, "SampleCropImage" + System.currentTimeMillis() + ".png"));
                    UCrop.Options options = new UCrop.Options();
                    options.setToolbarColor(getResources().getColor(R.color.purple_200));
                    options.setAspectRatioOptions(2, new AspectRatio("1:1", 1.0f, 1.0f), new AspectRatio("3:2", 3.0f, 2.0f), new AspectRatio("2:3", 2.0f, 3.0f), new AspectRatio("4:3", 4.0f, 3.0f), new AspectRatio("3:4", 3.0f, 4.0f), new AspectRatio("16:9", 16.0f, 9.0f), new AspectRatio("5:4", 5.0f, 4.0f), new AspectRatio("4:5", 4.0f, 5.0f));
                    UCrop.of(fromFile, fromFile2).withOptions(options).start(this);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }

    public void itemClickSeeMoreAdapter(ArrayList<BackgroundImage> arrayList, String str) {
        seeMore(arrayList);
    }

    private void seeMore(ArrayList<BackgroundImage> arrayList) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        BackgroundFragment1 backgroundFragment1 = (BackgroundFragment1) supportFragmentManager.findFragmentByTag("back_category_frgm");
        if (backgroundFragment1 != null) {
            beginTransaction.remove(backgroundFragment1);
        }
        beginTransaction.add(R.id.frameContainerBackground, BackgroundFragment1.newInstance(arrayList), "back_category_frgm");
        beginTransaction.addToBackStack("back_category_frgm");
        try {
            beginTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        freeMemory();
    }

    public void freeMemory() {
        try {
            new Thread(new Runnable() { // from class: com.chettapps.invitationmaker.photomaker.main.BGImageActivity.10
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Glide.get(BGImageActivity.this).clearDiskCache();
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


//    public void onActivityResult(int r23, int r24, Intent r25) {
//        /*
//            Method dump skipped, instructions count: 511
//            To view this dump change 'Code comments level' option to 'DEBUG'
//        */
//        throw new UnsupportedOperationException("Method not decompiled: com.chettapps.invitationmaker.photomaker.main.BGImageActivity.onActivityResult(int, int, android.content.Intent):void");
//    }

    private void handleCropResult(Intent intent) {
        Uri uri;
        try {
            uri = UCrop.getOutput(intent);
        } catch (Exception e) {
            e.printStackTrace();
            uri = null;
        }
        this.resultUri = uri;
        if (uri != null) {
            this.widht = UCrop.getOutputImageWidth(intent);
            int outputImageHeight = UCrop.getOutputImageHeight(intent);
            this.height = outputImageHeight;
            int i = this.widht;
            int gcd = gcd(i, outputImageHeight);
            this.ratio = "" + (i / gcd) + ":" + (outputImageHeight / gcd) + ":" + i + ":" + outputImageHeight;
            try {
                redirectToCreateCardScreen();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private int gcd(int i, int i2) {
        return i2 == 0 ? i : gcd(i2, i % i2);
    }

    public void redirectToCreateCardScreen() {
        if (this.resultUri != null) {
            Intent intent = new Intent(this, CreateCardActivity.class);
            intent.putExtra("ratio", this.ratio);
            intent.putExtra("loadUserFrame", true);
            intent.putExtra("profile", this.resultUri.toString());
            intent.putExtra("hex", "");
            startActivity(intent);
            return;
        }
        Toast.makeText(this, "Image Not Retrived", Toast.LENGTH_SHORT).show();
    }

    private void findViews() {
        this.btnBack = (ImageView) findViewById(R.id.btn_back);
        this.txtTitle = (TextView) findViewById(R.id.txtTitle);
        this.btnColorPicker = (ImageView) findViewById(R.id.btnColorPicker);
        this.btnGalleryPicker = (ImageView) findViewById(R.id.btnGalleryPicker);
        this.btnTakePicture = (ImageView) findViewById(R.id.btnTakePicture);
        this.txtTitle.setText("Background");
        this.btnColorPicker.setVisibility(View.VISIBLE);
        this.txtTitle.setTypeface(setBoldFont());
        this.btnBack.setOnClickListener(this);
        this.btnColorPicker.setOnClickListener(this);
        this.btnGalleryPicker.setOnClickListener(this);
        this.btnTakePicture.setOnClickListener(this);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnColorPicker /* 2131296424 */:
                colorPickerDialog(false);
                return;
            case R.id.btnGalleryPicker /* 2131296432 */:
                requestStorageGalleryPermission();
                return;
            case R.id.btnTakePicture /* 2131296464 */:
                makeStickerDir();
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                this.f = new File(getFilesDir(), ".temp.jpg");
                intent.putExtra("output", FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", this.f));
                startActivityForResult(intent, SELECT_PICTURE_FROM_CAMERA);
                return;
            case R.id.btn_back /* 2131296471 */:
                onBackPressed();
                return;
            default:
                return;
        }
    }

    private void requestStorageGalleryPermission() {
        Dexter.withContext(this).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA").withListener(new MultiplePermissionsListener() { // from class: com.invitationmaker.lss.photomaker.main.BGImageActivity.12
            @Override // com.karumi.dexter.listener.multi.MultiplePermissionsListener
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    BGImageActivity.this.makeStickerDir();
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction("android.intent.action.GET_CONTENT");
                    BGImageActivity bGImageActivity = BGImageActivity.this;
                    bGImageActivity.startActivityForResult(Intent.createChooser(intent, bGImageActivity.getString(R.string.select_picture)), BGImageActivity.SELECT_PICTURE_FROM_GALLERY);
                }
                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                    BGImageActivity.this.showSettingsDialog();
                }
            }

            @Override // com.karumi.dexter.listener.multi.MultiplePermissionsListener
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() { // from class: com.invitationmaker.lss.photomaker.main.BGImageActivity.11
            @Override // com.karumi.dexter.listener.PermissionRequestErrorListener
            public void onError(DexterError dexterError) {
                Toast.makeText(BGImageActivity.this.getApplicationContext(), "Error occurred! ", 0).show();
            }
        }).onSameThread().check();
    }

    private void colorPickerDialog(boolean z) {
//        new AmbilWarnaDialog(this, this.bColor, z, new AmbilWarnaDialog.OnAmbilWarnaListener() { // from class: com.invitationmaker.lss.photomaker.main.BGImageActivity.13
//            @Override // yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
//            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
//            }
//
//            @Override // yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
//            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
//                BGImageActivity.this.updateColor(i);
//            }
//        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateColor(int i) {
        Bitmap createBitmap = Bitmap.createBitmap(720, 1280, Bitmap.Config.ARGB_8888);
        createBitmap.eraseColor(i);
        Log.e(TAG, "updateColor: ");
        try {
            File file = new File(this.preferenceClass.getString(AllConstants.sdcardPath) + "/bg/");
            File file2 = new File(new File(this.preferenceClass.getString(AllConstants.sdcardPath) + "/bg/"), "tempcolor.png");
            if (!file.exists()) {
                file.mkdirs();
            }
            Uri uri = null;
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                try {
                    createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.close();
                    uri = Uri.fromFile(file2);
                } catch (IOException unused) {
                }
            } catch (IOException e) {
                Log.e("app", e.getMessage());
            }
            if (uri != null) {
                File cacheDir = getCacheDir();
                Uri fromFile = Uri.fromFile(new File(cacheDir, "SampleCropImage" + System.currentTimeMillis() + ".png"));
                UCrop.Options options = new UCrop.Options();
                options.setToolbarColor(getResources().getColor(R.color.purple_200));
                options.setAspectRatioOptions(2, new AspectRatio("1:1", 1.0f, 1.0f), new AspectRatio("3:2", 3.0f, 2.0f), new AspectRatio("2:3", 2.0f, 3.0f), new AspectRatio("4:3", 4.0f, 3.0f), new AspectRatio("3:4", 3.0f, 4.0f), new AspectRatio("16:9", 16.0f, 9.0f), new AspectRatio("5:4", 5.0f, 4.0f), new AspectRatio("4:5", 4.0f, 5.0f));
                UCrop.of(uri, fromFile).withOptions(options).start(this);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.invitationmaker.lss.photomaker.listener.GetSnapListener
    public void onSnapFilter(int i, int i2, String str) {
        int i3;
        Uri uri;
        Log.e("cat", "===" + i2);
        if (i2 == 0) {
            Resources resources = getResources();
            i3 = resources.getIdentifier("b" + (i + 1), "drawable", getPackageName());
        } else if (i2 == 1) {
            Resources resources2 = getResources();
            i3 = resources2.getIdentifier("bo" + (i + 1), "drawable", getPackageName());
        } else if (i2 == 2) {
            Resources resources3 = getResources();
            i3 = resources3.getIdentifier("br" + (i + 1), "drawable", getPackageName());
        } else if (i2 == 3) {
            Resources resources4 = getResources();
            i3 = resources4.getIdentifier("c" + (i + 1), "drawable", getPackageName());
        } else if (i2 == 4) {
            Resources resources5 = getResources();
            i3 = resources5.getIdentifier("ch" + (i + 1), "drawable", getPackageName());
        } else if (i2 == 5) {
            Resources resources6 = getResources();
            i3 = resources6.getIdentifier("d" + (i + 1), "drawable", getPackageName());
        } else if (i2 == 6) {
            Resources resources7 = getResources();
            i3 = resources7.getIdentifier("f" + (i + 1), "drawable", getPackageName());
        } else if (i2 == 7) {
            Resources resources8 = getResources();
            i3 = resources8.getIdentifier("fl" + (i + 1), "drawable", getPackageName());
        } else if (i2 == 8) {
            Resources resources9 = getResources();
            i3 = resources9.getIdentifier("g" + (i + 1), "drawable", getPackageName());
        } else if (i2 == 9) {
            Resources resources10 = getResources();
            i3 = resources10.getIdentifier("h" + (i + 1), "drawable", getPackageName());
        } else if (i2 == 10) {
            Resources resources11 = getResources();
            i3 = resources11.getIdentifier("l" + (i + 1), "drawable", getPackageName());
        } else if (i2 == 11) {
            Resources resources12 = getResources();
            i3 = resources12.getIdentifier("w" + (i + 1), "drawable", getPackageName());
        } else if (i2 == 12) {
            Resources resources13 = getResources();
            i3 = resources13.getIdentifier("wo" + (i + 1), "drawable", getPackageName());
        } else if (i2 == 13) {
            Resources resources14 = getResources();
            i3 = resources14.getIdentifier("f" + (i + 1), "drawable", getPackageName());
        } else if (i2 == 14) {
            Resources resources15 = getResources();
            i3 = resources15.getIdentifier("m" + (i + 1), "drawable", getPackageName());
        } else {
            i3 = 1;
        }
        Log.e("position", "===" + i);
        try {
            if (str.equals("null")) {
                Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), i3);
                File file = new File(this.preferenceClass.getString(AllConstants.sdcardPath) + "/bg" + i2 + "/");
                File file2 = new File(file, String.valueOf(i));
                uri = null;
                if (file2.exists()) {
                    uri = Uri.fromFile(file2);

                } else {
                    try {
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(file2);
                        try {
                            decodeResource.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                            fileOutputStream.close();
                            uri = Uri.fromFile(file2);
                        } catch (IOException unused) {
                        }
                    } catch (IOException e) {
                        Log.e("app", e.getMessage());
                    }
                }
            } else {
                uri = Uri.fromFile(new File(new File(str).getPath()));
            }
            if (uri == null) {
                File cacheDir = getCacheDir();
                Uri fromFile = Uri.fromFile(new File(cacheDir, "SampleCropImage" + System.currentTimeMillis() + ".png"));
                UCrop.Options options = new UCrop.Options();
                options.setToolbarColor(getResources().getColor(R.color.purple_200));
                options.setAspectRatioOptions(1, new AspectRatio("1:1", 1.0f, 1.0f), new AspectRatio("3:2", 3.0f, 2.0f), new AspectRatio("2:3", 2.0f, 3.0f), new AspectRatio("4:3", 4.0f, 3.0f), new AspectRatio("3:4", 3.0f, 4.0f), new AspectRatio("16:9", 16.0f, 9.0f), new AspectRatio("5:4", 5.0f, 4.0f), new AspectRatio("4:5", 4.0f, 5.0f));
                UCrop.of(uri, fromFile).withOptions(options).start(this);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.invitationmaker.lss.photomaker.main.GetSnapListenerS
    public void onSnapFilter(int i, int i2, String str, String str2) {
        requestStorageSnapPermission(i, i2, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void makeStickerDir() {
        this.preferenceClass = new PreferenceClass(this);
        File file = new File(Configure.GetFileDir(this).getPath() + "/.Invitation Stickers/sticker");
        if (!file.exists()) {
            file.mkdirs();
        }
        this.preferenceClass.putString(AllConstants.sdcardPath, file.getPath());
        Log.e(TAG, "onCreate: " + AllConstants.sdcardPath);
    }

    private void requestStorageSnapPermission(final int i, final int i2, final String str, String str2) {
        Dexter.withContext(this).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() { // from class: com.invitationmaker.lss.photomaker.main.BGImageActivity.15
            @Override // com.karumi.dexter.listener.multi.MultiplePermissionsListener
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    BGImageActivity.this.makeStickerDir();
                    try {
                        Uri uri = null;
                        if (str.equals("null")) {
                            Bitmap decodeResource = BitmapFactory.decodeResource(BGImageActivity.this.getResources(), 1);
                            File file = new File(BGImageActivity.this.preferenceClass.getString(AllConstants.sdcardPath) + "/bg" + i2 + "/");
                            File file2 = new File(file, String.valueOf(i));
                            if (file2.exists()) {
                                uri = Uri.fromFile(file2);
                            } else {
                                try {
                                    if (!file.exists()) {
                                        file.mkdirs();
                                    }
                                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                                    try {
                                        decodeResource.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                                        fileOutputStream.close();
                                        uri = Uri.fromFile(file2);
                                    } catch (IOException unused) {
                                    }
                                } catch (IOException unused2) {
                                    multiplePermissionsReport.isAnyPermissionPermanentlyDenied();
                                }
                            }
                        } else {
                            BGImageActivity.this.onGetPosition(str);
                        }
                        if (uri != null) {
                            File cacheDir = BGImageActivity.this.getCacheDir();
                            Uri fromFile = Uri.fromFile(new File(cacheDir, "SampleCropImage" + System.currentTimeMillis() + ".png"));
                            UCrop.Options options = new UCrop.Options();
                            options.setToolbarColor(BGImageActivity.this.getResources().getColor(R.color.purple_200));
                            options.setAspectRatioOptions(2, new AspectRatio("1:1", 1.0f, 1.0f), new AspectRatio("3:2", 3.0f, 2.0f), new AspectRatio("2:3", 2.0f, 3.0f), new AspectRatio("4:3", 4.0f, 3.0f), new AspectRatio("3:4", 3.0f, 4.0f), new AspectRatio("16:9", 16.0f, 9.0f), new AspectRatio("5:4", 5.0f, 4.0f), new AspectRatio("4:5", 4.0f, 5.0f));
                            UCrop.of(uri, fromFile).withOptions(options).start(BGImageActivity.this);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                    BGImageActivity.this.showSettingsDialog();
                }
            }

            @Override // com.karumi.dexter.listener.multi.MultiplePermissionsListener
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() { // from class: com.invitationmaker.lss.photomaker.main.BGImageActivity.14
            @Override // com.karumi.dexter.listener.PermissionRequestErrorListener
            public void onError(DexterError dexterError) {
                Toast.makeText(BGImageActivity.this.getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
            }
        }).onSameThread().check();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.BGImageActivity.16
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                BGImageActivity.this.openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.main.BGImageActivity.17
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
}
