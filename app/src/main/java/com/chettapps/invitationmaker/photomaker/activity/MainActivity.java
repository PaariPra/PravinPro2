package com.chettapps.invitationmaker.photomaker.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chettapps.invitationmaker.R;


import com.chettapps.invitationmaker.photomaker.main.AllConstants;

import com.chettapps.invitationmaker.photomaker.main.InvitationCatActivity;

import com.chettapps.invitationmaker.photomaker.network.NetworkConnectivityReceiver;
import com.chettapps.invitationmaker.photomaker.pojoClass.StickerWork;
import com.chettapps.invitationmaker.photomaker.utils.Configure;
import com.chettapps.invitationmaker.photomaker.utils.PreferenceClass;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    public static ArrayList<StickerWork> allStickerArrayList;
    public static float ratio;
    public static int width;
    AnimationDrawable animation;
    private SharedPreferences appPreferences;
    public SharedPreferences.Editor editor;
    private RelativeLayout layTemplate;
    private PreferenceClass preferenceClass;
    private SharedPreferences preferences;
    public SharedPreferences prefs;
    private TextView txtMoreApp;
    private TextView txtRateApp;
    boolean isAppInstalled = false;
    boolean isRegister = false;
    private boolean lay_photos = false;
    private boolean lay_poster = false;
    private boolean lay_templates = false;


    public void createShortCut() {
    }



    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle bundle) {

        getWindow().setFlags(1024, 1024);
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);



        PreferenceClass preferenceClass = new PreferenceClass(this);
        this.preferenceClass = preferenceClass;
        SharedPreferences prefernce = preferenceClass.getPrefernce();
        this.preferences = prefernce;
        this.editor = prefernce.edit();
        this.prefs = this.preferenceClass.getPrefernce();
        setMyFontNormal((ViewGroup) findViewById(16908290));
        findViews();
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.appPreferences = defaultSharedPreferences;
        boolean z = defaultSharedPreferences.getBoolean("isAppInstalled", false);
        this.isAppInstalled = z;
        if (!z) {
            createShortCut();
            SharedPreferences.Editor edit = this.appPreferences.edit();
            edit.putBoolean("isAppInstalled", true);
            edit.commit();
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

    private void findViews() {

        this.layTemplate = (RelativeLayout) findViewById(R.id.lay_template);
        this.layTemplate.setOnClickListener(this);

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        requestStoragePermissionOnclick(view.getId());
    }

    private void requestStoragePermissionOnclick(final int i) {
        Dexter.withContext(this).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA").withListener(new MultiplePermissionsListener() { // from class: com.chettapps.invitationmaker.photomaker.activity.MainActivity.6
            @Override // com.karumi.dexter.listener.multi.MultiplePermissionsListener
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    MainActivity.this.makeStickerDir();
                    if (NetworkConnectivityReceiver.isConnected()) {
                        MainActivity.this.getSticker();
                        switch (i) {



                            case R.id.lay_template :
                                MainActivity.this.lay_poster = false;
                                MainActivity.this.lay_templates = true;
                                MainActivity.this.lay_photos = false;
                                if (NetworkConnectivityReceiver.isConnected()) {
                                    MainActivity.this.startActivity(new Intent(MainActivity.this, InvitationCatActivity.class));
                                    return;


                                } else {
//                                    MainActivity.this.networkError();
                                    return;
                                }





                            default:
                                return;
                        }
                    }

                    else {
                       /* MainActivity.this.networkError();*/
                    }
                } else if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                    MainActivity.this.showSettingsDialog();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() { // from class: com.chettapps.invitationmaker.photomaker.activity.MainActivity.5
            @Override
            public void onError(DexterError dexterError) {
                Toast.makeText(MainActivity.this.getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
            }
        }).onSameThread().check();
    }


    public void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.activity.MainActivity.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                MainActivity.this.openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.activity.MainActivity.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }


    public void openSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivityForResult(intent, 101);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
