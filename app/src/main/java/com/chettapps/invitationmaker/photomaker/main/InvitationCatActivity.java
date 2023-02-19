package com.chettapps.invitationmaker.photomaker.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import com.chettapps.invitationmaker.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.chettapps.invitationmaker.photomaker.InvitationApplication;

import com.chettapps.invitationmaker.photomaker.activity.ImageDownloadManager;
import com.chettapps.invitationmaker.photomaker.activity.PaginationListener;
import com.chettapps.invitationmaker.photomaker.adapterMaster.PosterSnapAdapter;

import com.chettapps.invitationmaker.photomaker.fragment.MorePoster;
import com.chettapps.invitationmaker.photomaker.network.NetworkConnectivityReceiver;
import com.chettapps.invitationmaker.photomaker.pojoClass.PosterCo;
import com.chettapps.invitationmaker.photomaker.pojoClass.PosterDataList;
import com.chettapps.invitationmaker.photomaker.pojoClass.PosterInfo;
import com.chettapps.invitationmaker.photomaker.pojoClass.PosterKey;
import com.chettapps.invitationmaker.photomaker.pojoClass.PosterThumbFull;
import com.chettapps.invitationmaker.photomaker.pojoClass.PosterWithList;
import com.chettapps.invitationmaker.photomaker.pojoClass.Snap1;
import com.chettapps.invitationmaker.photomaker.pojoClass.Sticker_info;
import com.chettapps.invitationmaker.photomaker.pojoClass.Text_info;
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
import com.qintong.library.InsLoadingView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class InvitationCatActivity extends AppCompatActivity {
    public static final String ORIENTATION = "orientation";
    private static final int PAGE_START = 1;
    private static final int REQUEST_PURCHASE = 10001;
    public static final String TAG = "PosterCatActivity";
    private int cat_ids;
    CardView cv11;
    CardView cv169;
    CardView cv34;
    CardView cv43;
    CardView cv916;
    CardView cvAll;
    CardView cvCatAll;
    Dialog dialog;
    RelativeLayout ivCatFilter;
    ImageView ivRatioFilter;
    LinearLayout llRatioFilter;
    private InsLoadingView loading_view;
    private boolean mHorizontal;
    private int pos_ids;
    private ArrayList<PosterCo> posterCos;
    private PreferenceClass preferenceClass;
    SharedPreferences preferences;
    private ProgressDialog progressDialog;
    RecyclerView recyclerView;
    RelativeLayout rlCatFilter;
    RecyclerView rvCatList;
    PosterSnapAdapter snapAdapter;
    private ArrayList<Sticker_info> stickerInfoArrayList;
    private ArrayList<Text_info> textInfoArrayList;
    TextView textView;
    private Typeface typefaceBold;
    private Typeface typefaceNormal;
    private ArrayList<String> url;
    private ArrayList<String> urlss;
    private String appkey = "";
    int catID = 0;
    private final int cnt = 0;
    private int currentPage = 1;
    private final int isFirstLoad = 0;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    int itemCount = 0;
    int openCatFlag = 1;
    int openRatioFlag = 1;
    ArrayList<PosterDataList> posterDatas = new ArrayList<>();
    String ratio = "0";
    ArrayList<Object> snapData = new ArrayList<>();
    private int totalPage = 0;

    public void afterPurchageDone() {
    }

    static int access$108(InvitationCatActivity invitationCatActivity) {
        int i = invitationCatActivity.currentPage;
        invitationCatActivity.currentPage = i + 1;
        return i;
    }

    @Override
    // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_card_cat);
        this.typefaceBold = Typeface.createFromAsset(getAssets(), "font/Montserrat-SemiBold.ttf");
        this.typefaceNormal = Typeface.createFromAsset(getAssets(), "font/Montserrat-Medium.ttf");
        TextView textView = (TextView) findViewById(R.id.txtTitle);
        this.textView = textView;
        textView.setTypeface(setBoldFont());
        this.preferenceClass = new PreferenceClass(this);
        if (NetworkConnectivityReceiver.isConnected()) {
            this.preferenceClass.getBoolean("isAdsDisabled", false);
        }
        Config.SaveInt("flow", 2, this);
        this.loading_view = (InsLoadingView) findViewById(R.id.loading_view);
        this.rvCatList = (RecyclerView) findViewById(R.id.rv_cat_list);
        this.rlCatFilter = (RelativeLayout) findViewById(R.id.rl_select_cat);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.iv_cat_sel);
        this.ivCatFilter = relativeLayout;
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.main.InvitationCatActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                InvitationCatActivity.this.onBackPressed();
            }
        });
        this.llRatioFilter = (LinearLayout) findViewById(R.id.rl_select_ratio);
        ImageView imageView = (ImageView) findViewById(R.id.iv_ratio_sel);
        this.ivRatioFilter = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.main.InvitationCatActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (InvitationCatActivity.this.openRatioFlag == 1) {
                    InvitationCatActivity.this.rlCatFilter.setVisibility(View.GONE);
                    InvitationCatActivity.this.openCatFlag = 1;
                    InvitationCatActivity.this.llRatioFilter.setVisibility(View.VISIBLE);
                    InvitationCatActivity.this.openRatioFlag = 2;
                    return;
                }
                InvitationCatActivity.this.llRatioFilter.setVisibility(View.GONE);
                InvitationCatActivity.this.openRatioFlag = 1;
            }
        });
        this.cvCatAll = (CardView) findViewById(R.id.cv_cat_all);
        this.cvAll = (CardView) findViewById(R.id.cv_image_all);
        this.cv11 = (CardView) findViewById(R.id.cv_image1);
        this.cv34 = (CardView) findViewById(R.id.cv_image2);
        this.cv43 = (CardView) findViewById(R.id.cv_image3);
        this.cv916 = (CardView) findViewById(R.id.cv_image4);
        this.cv169 = (CardView) findViewById(R.id.cv_image5);
        this.cv43.setVisibility(View.GONE);
        this.cv916.setVisibility(View.GONE);
        this.cv169.setVisibility(View.GONE);
        getPosKeyAndCall1();

    }


    public Typeface setBoldFont() {
        return this.typefaceBold;
    }

    public Typeface setNormalFont() {
        return this.typefaceNormal;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override
    // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(ORIENTATION, this.mHorizontal);
    }

    public void freeMemory() {
        try {
            new Thread(new Runnable() { // from class: com.chettapps.invitationmaker.photomaker.main.InvitationCatActivity.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Glide.get(InvitationCatActivity.this).clearDiskCache();
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

    /* JADX INFO: Access modifiers changed from: private */
    public void setupAdapter() {
        for (int i = 0; i < this.posterDatas.size(); i++) {
            if (this.posterDatas.get(i).getPoster_list() != null) {
                ArrayList<PosterThumbFull> poster_list = this.posterDatas.get(i).getPoster_list();
                Collections.reverse(poster_list);
                this.snapData.add(new Snap1(1, this.posterDatas.get(i).getCat_name(), poster_list, Integer.parseInt(this.posterDatas.get(i).getCat_id()), this.ratio));
            }
        }
        getData();
    }

    private void getData() {
        if (this.snapData != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
            this.recyclerView = recyclerView;
            recyclerView.setLayoutManager(linearLayoutManager);
            this.recyclerView.setHasFixedSize(true);
            PosterSnapAdapter posterSnapAdapter = new PosterSnapAdapter(this, new ArrayList());
            this.snapAdapter = posterSnapAdapter;
            this.recyclerView.setAdapter(posterSnapAdapter);
            getPaging();
            this.recyclerView.addOnScrollListener(new PaginationListener(linearLayoutManager) { // from class: com.chettapps.invitationmaker.photomaker.main.InvitationCatActivity.4
                @Override // com.chettapps.invitationmaker.photomaker.activity.PaginationListener
                protected void loadMoreItems() {
                    InvitationCatActivity.this.isLoading = true;
                    InvitationCatActivity.access$108(InvitationCatActivity.this);
                    InvitationCatActivity.this.getPaging();
                }

                @Override // com.chettapps.invitationmaker.photomaker.activity.PaginationListener
                public boolean isLastPage() {
                    return InvitationCatActivity.this.isLastPage;
                }

                @Override // com.chettapps.invitationmaker.photomaker.activity.PaginationListener
                public boolean isLoading() {
                    return InvitationCatActivity.this.isLoading;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getPaging() {
        final ArrayList arrayList = new ArrayList();
        double size = this.snapData.size();
        Double.isNaN(size);
        Double.isNaN(size);
        this.totalPage = Round(size / 10.0d);
        new Handler().postDelayed(new Runnable() { // from class: com.chettapps.invitationmaker.photomaker.main.InvitationCatActivity.5
            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < 10; i++) {
                    if (InvitationCatActivity.this.itemCount < InvitationCatActivity.this.snapData.size()) {
                        arrayList.add(InvitationCatActivity.this.snapData.get(InvitationCatActivity.this.itemCount));
                        InvitationCatActivity.this.itemCount++;
                    }
                }
                if (InvitationCatActivity.this.currentPage != 1) {
                    InvitationCatActivity.this.snapAdapter.removeLoadingView();
                }
                InvitationCatActivity.this.snapAdapter.addData(arrayList);
                InvitationCatActivity.this.loading_view.setVisibility(View.GONE);
                if (InvitationCatActivity.this.currentPage < InvitationCatActivity.this.totalPage) {
                    InvitationCatActivity.this.snapAdapter.addLoadingView();
                } else {
                    InvitationCatActivity.this.isLastPage = true;
                }
                InvitationCatActivity.this.isLoading = false;
            }
        }, 3000L);
    }

    public int Round(double d) {
        int i = (Math.abs(d - Math.floor(d)) > 0.1d ? 1 : (Math.abs(d - Math.floor(d)) == 0.1d ? 0 : -1));
        int i2 = (int) d;
        return i > 0 ? i2 + 1 : i2;
    }

    public void itemClickSeeMoreAdapter(ArrayList<PosterThumbFull> arrayList, int i, String str, String str2) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        MorePoster morePoster = (MorePoster) supportFragmentManager.findFragmentByTag("template_category_frgm");
        if (morePoster != null) {
            beginTransaction.remove(morePoster);
        }
        beginTransaction.add(R.id.frameContainerPoster, MorePoster.newInstance(arrayList, i, str, str2), "template_category_frgm");
        beginTransaction.addToBackStack("template_category_frgm");
        try {
            beginTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void makeStickerDir() {
        this.preferenceClass = new PreferenceClass(this);
        File file = new File(Configure.GetFileDir(this).getPath() + "/.Invitation Stickers/sticker");
        if (!file.exists()) {
            file.mkdirs();
        }
        this.preferenceClass.putString(AllConstants.sdcardPath, file.getPath());
        Log.e(TAG, "onCreate: " + AllConstants.sdcardPath);
    }

    public void closeDialog() {
        this.dialog.dismiss();
    }

    public void openPosterActivity(int i, int i2) {
        requestStoragePermission(i, i2);
    }

    private void requestStoragePermission(final int i, final int i2) {
        Dexter.withContext(this).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() { // from class: com.chettapps.invitationmaker.photomaker.main.InvitationCatActivity.7
            @Override // com.karumi.dexter.listener.multi.MultiplePermissionsListener
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    InvitationCatActivity.this.makeStickerDir();
                    InvitationCatActivity.this.setupProgress();
                    InvitationCatActivity.this.getPosKeyAndCall(i2, i);
                }
                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                    InvitationCatActivity.this.showSettingsDialog();
                }
            }

            @Override // com.karumi.dexter.listener.multi.MultiplePermissionsListener
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() { // from class: com.chettapps.invitationmaker.photomaker.main.InvitationCatActivity.6
            @Override // com.karumi.dexter.listener.PermissionRequestErrorListener
            public void onError(DexterError dexterError) {
                Toast.makeText(InvitationCatActivity.this.getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
            }
        }).onSameThread().check();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.main.InvitationCatActivity.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                InvitationCatActivity.this.openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.main.InvitationCatActivity.9
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

    public void setupProgress() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.progressDialog = progressDialog;
        progressDialog.setTitle("Downloading Templates");
        this.progressDialog.setMessage("Downloading is in progress, Please wait...");
        this.progressDialog.setIndeterminate(true);
        this.progressDialog.setProgressStyle(0);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }

    public void getPosKeyAndCall(int i, int i2) {
        loadPoster(this.appkey, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteRecursive(File file) {
        if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                deleteRecursive(file2);
            }
        }
        file.delete();
    }

    public void loadPoster(final String str, final int i, final int i2) {




        InvitationApplication instance = InvitationApplication.getInstance();
        instance.addToRequestQueue(new StringRequest(1, AllConstants.BASE_URL_POSTER + "poster/poster", new Response.Listener<String>() {



            public void onResponse(String str2) {
                try {



                    InvitationCatActivity.this.posterCos = new ArrayList();

                    Log.e("TAG", "onResponse45454545: "+str2 );


                    InvitationCatActivity.this.posterCos = ((PosterInfo) new Gson().fromJson(str2, PosterInfo.class)).getData();




                    InvitationCatActivity invitationCatActivity = InvitationCatActivity.this;
                    invitationCatActivity.textInfoArrayList = ((PosterCo) invitationCatActivity.posterCos.get(0)).getText_info();
                    InvitationCatActivity invitationCatActivity2 = InvitationCatActivity.this;
                    invitationCatActivity2.stickerInfoArrayList = ((PosterCo) invitationCatActivity2.posterCos.get(0)).getSticker_info();
                    PosterCo posterCo = (PosterCo) InvitationCatActivity.this.posterCos.get(0);






                    InvitationCatActivity.this.ratio = posterCo.getRatio();
                    InvitationCatActivity.this.url = new ArrayList();
                    InvitationCatActivity.this.url.add(posterCo.getBack_image());


                    Log.e("TAG", "onResponse11111: "+posterCo.getBack_image() );








                    for (int i3 = 0; i3 < InvitationCatActivity.this.stickerInfoArrayList.size(); i3++) {
                        if (!((Sticker_info) InvitationCatActivity.this.stickerInfoArrayList.get(0)).getSt_image().equals("")) {
                            ArrayList arrayList = InvitationCatActivity.this.url;
                            arrayList.add(AllConstants.BASE_URL_STICKER + ((Sticker_info) InvitationCatActivity.this.stickerInfoArrayList.get(i3)).getSt_image());
                        }
                    }


                    File GetTemplate = Configure.GetTemplate(InvitationCatActivity.this.getApplication());
                    if (!GetTemplate.exists()) {
                        GetTemplate.mkdir();
                    } else {
                        InvitationCatActivity.this.deleteRecursive(GetTemplate);
                        GetTemplate.mkdir();
                    }





                    Log.e("TAG", "onResponse2222: "+ InvitationCatActivity.this.url.size() );
                    Log.e("TAG", "onResponse2333: "+InvitationCatActivity.this.url.get(0));
                    Log.e("TAG", "onResponse2333: "+InvitationCatActivity.this.url.get(0));
                    Log.e("TAG", "onResponse2333: "+InvitationCatActivity.this.url.get(1));
                    Log.e("TAG", "onResponse2333: "+InvitationCatActivity.this.url.get(2));
                    Log.e("TAG", "onResponse444444: "+ GetTemplate.getPath() );





                    ImageDownloadManager.getInstance(InvitationCatActivity.this.getApplicationContext()).addTask(new
                            ImageDownloadManager.ImageDownloadTask(this, ImageDownloadManager.Task.DOWNLOAD, InvitationCatActivity.this.url,
                            GetTemplate.getPath(), new ImageDownloadManager.Callback() { // from class: com.invitationmaker.lss.photomaker.main.InvitationCatActivity.10.1
                        @Override
                        public void onSuccess(ImageDownloadManager.ImageDownloadTask imageDownloadTask, ArrayList<String> arrayList2) {



                            try {
                                if (InvitationCatActivity.this.progressDialog != null && InvitationCatActivity.this.progressDialog.isShowing()) {
                                    InvitationCatActivity.this.progressDialog.dismiss();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.d("ImageDownloadManager", "Image save success news ");
                            for (int i4 = 0; i4 < arrayList2.size(); i4++) {
                                if (i4 == 0) {
                                    try {
                                        ((PosterCo) InvitationCatActivity.this.posterCos.get(i4)).setBack_image(arrayList2.get(i4));
                                    } catch (IndexOutOfBoundsException e2) {
                                        e2.printStackTrace();
                                        return;
                                    }
                                } else {
                                    ((Sticker_info) InvitationCatActivity.this.stickerInfoArrayList.get(i4 - 1)).setSt_image(arrayList2.get(i4));
                                }
                            }
                            File file = new File(((PosterCo) InvitationCatActivity.this.posterCos.get(0)).getBack_image());
                            if (!file.exists()) {
                                Log.d("not exist", "not exist");
                            } else if (file.length() == 0) {
                                Log.d("File Empty", "File does not have any content");
                            }




                            else {


                                Intent intent = new Intent(InvitationCatActivity.this, CreateCardActivity.class);


                                Log.e("TAG", "onSuccess1111: " + InvitationCatActivity.this.posterCos.size());
                                Log.e("TAG", "onSuccess222: " + InvitationCatActivity.this.textInfoArrayList.size());
                                Log.e("TAG", "onSuccess333: " + InvitationCatActivity.this.stickerInfoArrayList.size());

                                intent.putParcelableArrayListExtra("template", InvitationCatActivity.this.posterCos);
                                intent.putParcelableArrayListExtra("text", InvitationCatActivity.this.textInfoArrayList);
                                intent.putParcelableArrayListExtra("sticker", InvitationCatActivity.this.stickerInfoArrayList);



                                intent.putExtra("profile", "Background");
                                intent.putExtra("catId", 1);
                                intent.putExtra("loadUserFrame", false);
                                intent.putExtra("Temp_Type", "MY_TEMP");
                                InvitationCatActivity.this.startActivity(intent);









                            }
                        }

                        @Override
                        // com.invitationmaker.lss.photomaker.activity.ImageDownloadManager.Callback
                        public void onFailure(ImageDownloadManager.ImageSaveFailureReason imageSaveFailureReason) {
                            Log.d("ImageDownloadManager", "Image save fail news " + imageSaveFailureReason);
                            try {
                                if (InvitationCatActivity.this.progressDialog != null && InvitationCatActivity.this.progressDialog.isShowing()) {
                                    InvitationCatActivity.this.progressDialog.dismiss();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }));
                } catch (JsonSyntaxException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // from class: com.invitationmaker.lss.photomaker.main.InvitationCatActivity.11
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(InvitationCatActivity.TAG, "Error: " + volleyError.getMessage());
                try {
                    ProgressDialog progressDialog = InvitationCatActivity.this.progressDialog;
                    if (progressDialog != null && progressDialog.isShowing()) {
                        InvitationCatActivity.this.progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) { // from class: com.invitationmaker.lss.photomaker.main.InvitationCatActivity.12
            @Override // com.android.volley.Request
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("key", str);
                hashMap.put("device", "1");
                hashMap.put("cat_id", String.valueOf(i));
                hashMap.put("post_id", String.valueOf(i2));
                return hashMap;
            }
        });
    }

    public void getPosKeyAndCall1() {
        InvitationApplication instance = InvitationApplication.getInstance();
        instance.addToRequestQueue(new StringRequest(1, AllConstants.BASE_URL_POSTER + "apps_key", new Response.Listener<String>() { // from class: com.invitationmaker.lss.photomaker.main.InvitationCatActivity.13
            public void onResponse(String str) {
                try {
                    String key = ((PosterKey) new Gson().fromJson(str, PosterKey.class)).getKey();
                    InvitationCatActivity.this.appkey = key;
                    InvitationCatActivity.this.getPosterList(key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // from class: com.invitationmaker.lss.photomaker.main.InvitationCatActivity.14
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(InvitationCatActivity.TAG, "Error: " + volleyError.getMessage());
            }
        }) { // from class: com.invitationmaker.lss.photomaker.main.InvitationCatActivity.15
            @Override // com.android.volley.Request
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("device", "1");
                return hashMap;
            }
        }, TAG);
    }

    public void getPosterList(final String str) {
        InvitationApplication instance = InvitationApplication.getInstance();
        instance.addToRequestQueue(new StringRequest(1, AllConstants.BASE_URL_POSTER + "poster/swiperCat", new Response.Listener<String>() { // from class: com.invitationmaker.lss.photomaker.main.InvitationCatActivity.16
            public void onResponse(String str2) {
                try {
                    InvitationCatActivity.this.posterDatas.addAll(((PosterWithList) new Gson().fromJson(str2, PosterWithList.class)).getData());
                    InvitationCatActivity.this.setupAdapter();
                } catch (Exception e) {
                    try {
                        e.printStackTrace();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() { // from class: com.invitationmaker.lss.photomaker.main.InvitationCatActivity.17
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(InvitationCatActivity.TAG, "Error: " + volleyError.getMessage());
            }
        }) { // from class: com.invitationmaker.lss.photomaker.main.InvitationCatActivity.18
            @Override // com.android.volley.Request
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("key", str);
                hashMap.put("device", "1");
                hashMap.put("cat_id", String.valueOf(InvitationCatActivity.this.catID));
                hashMap.put("ratio", InvitationCatActivity.this.ratio);
                return hashMap;
            }
        }, TAG);
    }

    public void lambda$getPosterList$3$InvitationCatActivity(String str) {
        try {
            this.posterDatas.addAll(((PosterWithList) new Gson().fromJson(str, PosterWithList.class)).getData());
            setupAdapter();
        } catch (Exception e) {
            try {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override
    // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == REQUEST_PURCHASE) {
            afterPurchageDone();
        }
    }

    public void toGooglePlay() {
        String packageName = getPackageName();
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)));
        } catch (ActivityNotFoundException unused) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override
    // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        freeMemory();
    }
}
