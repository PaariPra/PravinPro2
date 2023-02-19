package com.chettapps.invitationmaker.photomaker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.activity.MainActivity;
import com.chettapps.invitationmaker.photomaker.listener.OnClickCallback;
import com.chettapps.invitationmaker.photomaker.main.AllConstants;
import com.chettapps.invitationmaker.photomaker.network.NetworkConnectivityReceiver;
import com.chettapps.invitationmaker.photomaker.utils.Configure;
import com.chettapps.invitationmaker.photomaker.utils.PreferenceClass;
import java.io.File;




public class TextFontAdapter extends BaseAdapter {
    private static final String TAG = "FontAdapter";
    private final String[] Imageid;
    int arraySize;
    private Activity mContext;
    private OnClickCallback mSingleCallback;
    private PreferenceClass preferenceClass;
    private boolean isDownloadProgress = true;
    int selPos = -1;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return 0L;
    }

    public TextFontAdapter(Activity activity, String[] strArr) {
        this.mContext = activity;
        this.Imageid = strArr;
        this.preferenceClass = new PreferenceClass(activity);
        this.arraySize = strArr.length;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.arraySize;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return 0;
    }

    public void setItemClickCallback(OnClickCallback onClickCallback) {
        this.mSingleCallback = onClickCallback;
    }

    @Override // android.widget.Adapter
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            view = ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.fonts_text_grid, (ViewGroup) null);
            viewHolder = new ViewHolder();
            viewHolder.layItem = (RelativeLayout) view.findViewById(R.id.layItem);
            viewHolder.txtView = (TextView) view.findViewById(R.id.grid_text);
            viewHolder.txtDownloadFont = (ImageView) view.findViewById(R.id.txtDownloadFont);
            viewHolder.downloadProgress = (ProgressBar) view.findViewById(R.id.downloadProgress);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.downloadProgress.setVisibility(View.INVISIBLE);
        String[] strArr = this.Imageid;
        if (i < strArr.length) {
            viewHolder.txtDownloadFont.setVisibility(View.GONE);
            if (i == 0) {
                viewHolder.txtView.setTypeface(Typeface.DEFAULT);
            } else {
                if (i > 24) {
                    viewHolder.txtView.setText(this.Imageid[i]);
                }
                MimeTypeMap.getFileExtensionFromUrl(this.Imageid[i]);
                try {
                    TextView textView = viewHolder.txtView;
                    AssetManager assets = this.mContext.getAssets();
                    textView.setTypeface(Typeface.createFromAsset(assets, "font/" + this.Imageid[i]));
                } catch (Exception unused) {
                    Log.e(TAG, "getView: font not found");
                }
            }
        } else {
            int length = i - strArr.length;
            StringBuilder sb = new StringBuilder();
            sb.append(new File(Configure.GetFileDir(this.mContext).getPath() + File.separator + "font/").getPath());
            sb.append("/");
            sb.append(MainActivity.allStickerArrayList.get(0).getFonts().get(length));
            File file = new File(sb.toString());
            TextView textView2 = viewHolder.txtView;
            textView2.setText(MainActivity.allStickerArrayList.get(0).getFonts().get(length) + "");
            if (file.exists()) {
                viewHolder.txtDownloadFont.setVisibility(View.GONE);
                try {
                    viewHolder.txtView.setTypeface(Typeface.createFromFile(file));
                } catch (RuntimeException unused2) {
                    Log.e(TAG, "getView: RuntimeException font not found");
                    viewHolder.txtView.setTypeface(Typeface.DEFAULT);
                }
            } else {
                viewHolder.txtDownloadFont.setVisibility(View.VISIBLE);
            }
        }
        viewHolder.txtView.setTextColor(this.mContext.getResources().getColor(R.color.white));
        int i2 = this.selPos;
        if (i2 >= 0 && i == i2) {
            viewHolder.txtView.setTextColor(this.mContext.getResources().getColor(R.color.purple_200));
        }
        viewHolder.txtDownloadFont.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view2) {
                if (!NetworkConnectivityReceiver.isConnected()) {
                    Toast.makeText(TextFontAdapter.this.mContext, "No Internet Connection!!!", Toast.LENGTH_SHORT).show();
                } else if (TextFontAdapter.this.isDownloadProgress) {
                    TextFontAdapter.this.isDownloadProgress = false;
                    viewHolder.downloadProgress.setVisibility(View.VISIBLE);
                    int length2 = i - TextFontAdapter.this.Imageid.length;
                    String str = AllConstants.fURL + MainActivity.allStickerArrayList.get(0).getFonts().get(length2);
                    File file2 = new File(Configure.GetFileDir(TextFontAdapter.this.mContext).getPath() + File.separator + "font/");
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Download URL : ");
                    sb2.append(str);
                    Log.e(TextFontAdapter.TAG, sb2.toString());
                    TextFontAdapter.this.DownoloadSticker(str, file2.getPath(), MainActivity.allStickerArrayList.get(0).getFonts().get(length2));
                } else {
                    Toast.makeText(TextFontAdapter.this.mContext, "Please wait..", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewHolder.txtView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view2) {
                if (i < TextFontAdapter.this.Imageid.length) {
                    TextFontAdapter.this.mSingleCallback.onClickCallBack(null, String.valueOf(i),  TextFontAdapter.this.mContext,TextFontAdapter.this.Imageid[i]);
                    return;
                }
                int length2 = i - TextFontAdapter.this.Imageid.length;
                if (new File(new File(Configure.GetFileDir(TextFontAdapter.this.mContext).getPath() + File.separator + "font/").getPath() + "/" + MainActivity.allStickerArrayList.get(0).getFonts().get(length2)).exists()) {
                    TextFontAdapter.this.mSingleCallback.onClickCallBack(null, String.valueOf(i), TextFontAdapter.this.mContext,
                            MainActivity.allStickerArrayList.get(0).getFonts().get(length2));
                }
            }
        });
        return view;
    }

    public void DownoloadSticker(String str, String str2, String str3) {
        AndroidNetworking.download(str, str2, str3).build().startDownload(new DownloadListener() {

            @Override
            public void onDownloadComplete() {
                TextFontAdapter.this.isDownloadProgress = true;
                TextFontAdapter.this.notifyDataSetChanged();
                Log.e(TextFontAdapter.TAG, "onDownloadComplete: ");
            }

            @Override
            public void onError(ANError aNError) {
                TextFontAdapter.this.isDownloadProgress = true;
                Log.e(TextFontAdapter.TAG, "onError: ");
                Toast.makeText(TextFontAdapter.this.mContext, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setSelected(int i) {
        this.selPos = i;
        notifyDataSetChanged();
    }

    /* loaded from: classes2.dex */
    public class ViewHolder {
        ProgressBar downloadProgress;
        RelativeLayout layItem;
        ImageView txtDownloadFont;
        TextView txtView;

        public ViewHolder() {
        }
    }
}
