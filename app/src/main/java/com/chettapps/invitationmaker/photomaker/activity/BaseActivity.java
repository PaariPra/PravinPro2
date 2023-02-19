package com.chettapps.invitationmaker.photomaker.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.androidnetworking.interfaces.DownloadListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.main.AllConstants;
import com.chettapps.invitationmaker.photomaker.pojoClass.StickerWork;
import com.chettapps.invitationmaker.photomaker.utils.Configure;
import com.chettapps.invitationmaker.photomaker.utils.PreferenceClass;
import com.google.gson.Gson;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private int isFirstLoad = 0;
    private PreferenceClass preferenceClass;
    public Typeface typefaceTextBold;
    public Typeface typefaceTextNormal;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.typefaceTextNormal = Typeface.createFromAsset(getAssets(), "font/Montserrat-Medium.ttf");
        this.typefaceTextBold = Typeface.createFromAsset(getAssets(), "font/Montserrat-SemiBold.ttf");
        this.preferenceClass = new PreferenceClass(this);
    }

    public void setMyFontNormal(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                setMyFontNormal((ViewGroup) childAt);
            } else if (childAt instanceof TextView) {
                ((TextView) childAt).setTypeface(this.typefaceTextNormal);
            } else if (childAt instanceof Button) {
                ((Button) childAt).setTypeface(this.typefaceTextNormal);
            } else if (childAt instanceof EditText) {
                ((EditText) childAt).setTypeface(this.typefaceTextNormal);
            }
        }
    }

    public void setMyFontBold(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                setMyFontNormal((ViewGroup) childAt);
            } else if (childAt instanceof TextView) {
                ((TextView) childAt).setTypeface(this.typefaceTextBold);
            } else if (childAt instanceof Button) {
                ((Button) childAt).setTypeface(this.typefaceTextBold);
            } else if (childAt instanceof EditText) {
                ((EditText) childAt).setTypeface(this.typefaceTextBold);
            }
        }
    }

    public void toGooglePlay() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("market://details?id=" + getPackageName()));
        if (getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() >= 1) {
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void copyFile(String str, String str2) {
        try {
            InputStream open = getAssets().open("font/" + str);
            String str3 = str2 + "/" + str;
            if (new File(str3).exists()) {
                Log.e(TAG, "copyAssets: font exist   " + str3);
                return;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(str3);
            Log.e(TAG, "copyFile: " + str);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = open.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    open.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void moreApp(String str) {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (ActivityNotFoundException unused) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/search?q=pub:" + getResources().getString(R.string.app_name))));
        }
    }
//
//    public void toShare() {
//        try {
//            File file = new File(getExternalCacheDir() + "/shareimg.png");
//            Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.btn_more_app);
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            decodeResource.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
//            fileOutputStream.flush();
//            fileOutputStream.close();
//            Uri uriForFile = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
//            Intent intent = new Intent("android.intent.action.SEND");
//            intent.addFlags(524288);
//            intent.setType("*/*");
//            intent.putExtra("android.intent.extra.STREAM", uriForFile);
//            intent.putExtra("android.intent.extra.TEXT", getResources().getString(R.string.share_text) + "\nhttps://play.google.com/store/apps/details?id=" + getPackageName());
//            startActivity(Intent.createChooser(intent, "Share via"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public Typeface setBoldFont() {
        return this.typefaceTextBold;
    }

    public Typeface setNormalFont() {
        return this.typefaceTextNormal;
    }

    public void getSticker() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        asyncHttpClient.post(AllConstants.BASE_URL, null, new getAllSticker());
    }

    public void DownoloadSticker(String str, String str2, String str3) {
        AndroidNetworking.download(str, str2, str3).build().startDownload(new DownloadListener() { // from class: com.chettapps.invitationmaker.photomaker.activity.BaseActivity.1
            @Override // com.androidnetworking.interfaces.DownloadListener
            public void onDownloadComplete() {
                Log.e(BaseActivity.TAG, "onDownloadComplete: ");
            }

            @Override // com.androidnetworking.interfaces.DownloadListener
            public void onError(ANError aNError) {
                Log.e(BaseActivity.TAG, "onError: ");
            }
        });
    }

    /* loaded from: classes2.dex */
    public class copyFontBG extends AsyncTask<String, Void, String> {
        /* JADX INFO: Access modifiers changed from: protected */
        public void onPostExecute(String str) {
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onProgressUpdate(Void... voidArr) {
        }

        public copyFontBG() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public String doInBackground(String... strArr) {
            File file = new File(Configure.GetFileDir(BaseActivity.this.getApplicationContext()).getPath() + File.separator + "font");
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                String[] list = BaseActivity.this.getAssets().list("font");
                if (!file.exists() && !file.mkdir()) {
                    Log.e(BaseActivity.TAG, "No create external directory: " + file);
                }
                for (String str : list) {
                    BaseActivity.this.copyFile(str, file.getPath());
                }
                return "Executed";
            } catch (IOException e) {
                Log.e(BaseActivity.TAG, "I/O Exception", e);
                return "Executed";
            }
        }
    }

    /* loaded from: classes2.dex */
    public class getAllSticker extends AsyncHttpResponseHandler {


        public getAllSticker() {
        }

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onFinish() {
            Log.e(BaseActivity.TAG, "onFinish: ");
            super.onFinish();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {
                String str = new String(responseBody);
                BaseActivity.this.preferenceClass.putString(AllConstants.jsonData, str);
                MainActivity.allStickerArrayList = new ArrayList<>();
                MainActivity.allStickerArrayList.add((StickerWork) new Gson().fromJson(str, StickerWork.class));
                Log.e(BaseActivity.TAG, "onSuccess: ");
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.e(BaseActivity.TAG, "onFailure: " + error);
        }
//
//        @Override // com.loopj.android.http.AsyncHttpResponseHandler
//        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
//            try {
//                String str = new String(bArr);
//                BaseActivity.this.preferenceClass.putString(AllConstants.jsonData, str);
//                MainActivity.allStickerArrayList = new ArrayList<>();
//                MainActivity.allStickerArrayList.add((StickerWork) new Gson().fromJson(str, (Class<Object>) StickerWork.class));
//                Log.e(BaseActivity.TAG, "onSuccess: ");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
//            Log.e(BaseActivity.TAG, "onFailure: " + th);
//        }





    }
}
