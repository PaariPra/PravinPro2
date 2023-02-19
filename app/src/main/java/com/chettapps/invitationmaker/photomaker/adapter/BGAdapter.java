package com.chettapps.invitationmaker.photomaker.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;

import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.listener.OnClickCallback;
import com.chettapps.invitationmaker.photomaker.main.AllConstants;
import com.chettapps.invitationmaker.photomaker.network.NetworkConnectivityReceiver;
import com.chettapps.invitationmaker.photomaker.utils.PreferenceClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class BGAdapter extends RecyclerView.Adapter<BGAdapter.ViewHolder> {
    private static final String TAG = "BackgroundAdapter";
    int arraySize;
    private int category;
    private String categoryName;
    Activity context;
    private OnClickCallback mSingleCallback;
    int[] offlineBgImage;
    private PreferenceClass preferenceClass;
    List<String> serverbg;
    private boolean isDownloadProgress = true;
    int size = 0;
    boolean smallView = false;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return i;
    }

    public BGAdapter(Activity activity, int[] iArr, int i) {
        this.arraySize = 0;
        this.context = activity;
        this.offlineBgImage = iArr;
        this.arraySize = i;
        this.preferenceClass = new PreferenceClass(activity);
    }

    public BGAdapter(Activity activity, int[] iArr, List<String> list, String str, int i) {
        this.arraySize = 0;
        this.context = activity;
        this.offlineBgImage = iArr;
        this.serverbg = list;
        this.arraySize = 0;
        this.preferenceClass = new PreferenceClass(activity);
        this.categoryName = str;
        this.category = i;
        this.preferenceClass = new PreferenceClass(this.context);
        if (list != null) {
            this.arraySize = list.size() + iArr.length;
        } else {
            this.arraySize = iArr.length;
        }
    }

    public void setLayoutParams(int i) {
        this.size = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.arraySize;
    }

    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        viewHolder.downloadProgress.setVisibility(View.GONE);
        int[] iArr = this.offlineBgImage;
        if (i < iArr.length) {
            viewHolder.imgDownload.setVisibility(View.GONE);
            Glide.with(this.context).load(Integer.valueOf(this.offlineBgImage[i])).thumbnail(0.1f).apply((BaseRequestOptions<?>) new RequestOptions().dontAnimate().override(200, 200).fitCenter().placeholder(R.drawable.no_image).error(R.drawable.no_image)).into(viewHolder.imageView);
        } else {
            int length = i - iArr.length;
            String str = AllConstants.bgURL + this.categoryName + "/thumb/" + this.serverbg.get(length);
            Log.e(TAG, "sticker url : " + AllConstants.bgURL + this.categoryName + "/" + this.serverbg.get(length));
            File file = new File(this.preferenceClass.getString(AllConstants.sdcardPath) + "/bg" + this.category + "/" + this.serverbg.get(length));
            if (file.exists()) {
                viewHolder.imgDownload.setVisibility(View.GONE);
                Glide.with(this.context).load(file.getPath()).thumbnail(0.1f).apply((BaseRequestOptions<?>) new RequestOptions().dontAnimate().override(200, 200).fitCenter().placeholder(R.drawable.no_image).error(R.drawable.no_image)).into(viewHolder.imageView);
            } else {
                viewHolder.imgDownload.setVisibility(View.VISIBLE);
                Glide.with(this.context).load(str).thumbnail(0.1f).apply((BaseRequestOptions<?>) new RequestOptions().dontAnimate().override(200, 200).fitCenter().placeholder(R.drawable.no_image).error(R.drawable.no_image)).into(viewHolder.imageView);
            }
        }
        viewHolder.layout.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.BGAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (i < BGAdapter.this.offlineBgImage.length) {
                    BGAdapter.this.mSingleCallback.onClickCallBack(null, String.valueOf(i), context,"null");
                    return;
                }
                File file2 = new File(BGAdapter.this.preferenceClass.getString(AllConstants.sdcardPath) + "/bg" + BGAdapter.this.category + "/" + BGAdapter.this.serverbg.get(i - BGAdapter.this.offlineBgImage.length));
                if (file2.exists()) {
                    BGAdapter.this.mSingleCallback.onClickCallBack(null, String.valueOf(i),  BGAdapter.this.context, file2.getPath());
                }
            }
        });
        viewHolder.imgDownload.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.BGAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!NetworkConnectivityReceiver.isConnected()) {
                    Toast.makeText(BGAdapter.this.context, "No Internet Connection!!!", Toast.LENGTH_SHORT).show();
                } else if (BGAdapter.this.isDownloadProgress) {
                    BGAdapter.this.isDownloadProgress = false;
                    viewHolder.downloadProgress.setVisibility(View.VISIBLE);
                    int length2 = i - BGAdapter.this.offlineBgImage.length;
                    String str2 = AllConstants.bgURL + BGAdapter.this.categoryName + "/" + BGAdapter.this.serverbg.get(length2);
                    File file2 = new File(BGAdapter.this.preferenceClass.getString(AllConstants.sdcardPath) + "/bg" + BGAdapter.this.category + "/");
                    StringBuilder sb = new StringBuilder();
                    sb.append("download url : ");
                    sb.append(str2);
                    Log.e(BGAdapter.TAG, sb.toString());
                    viewHolder.imgDownload.setVisibility(View.GONE);
                    BGAdapter.this.DownoloadSticker(str2, file2.getPath(), BGAdapter.this.serverbg.get(length2));
                } else {
                    Toast.makeText(BGAdapter.this.context, "Please wait..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setItemClickCallback(OnClickCallback onClickCallback) {
        this.mSingleCallback = onClickCallback;
    }

    public void smallView(boolean z) {
        this.smallView = z;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bg_cat_list, viewGroup, false));
        viewGroup.setId(i);
        viewGroup.setFocusable(false);
        viewGroup.setFocusableInTouchMode(false);
        return viewHolder;
    }

    public void DownoloadSticker(String str, String str2, String str3) {
        AndroidNetworking.download(str, str2, str3).build().startDownload(new DownloadListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.BGAdapter.3
            @Override // com.androidnetworking.interfaces.DownloadListener
            public void onDownloadComplete() {
                BGAdapter.this.isDownloadProgress = true;
                BGAdapter.this.notifyDataSetChanged();
                Log.e(BGAdapter.TAG, "onDownloadComplete: ");
            }

            @Override // com.androidnetworking.interfaces.DownloadListener
            public void onError(ANError aNError) {
                BGAdapter.this.isDownloadProgress = true;
                BGAdapter.this.notifyDataSetChanged();
                Log.e(BGAdapter.TAG, "onError: ");
                Toast.makeText(BGAdapter.this.context, "Network Error", 0).show();
            }
        });
    }

    /* loaded from: classes2.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar downloadProgress;
        ImageView imageView;
        RelativeLayout imgDownload;
        LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
            this.imgDownload = (RelativeLayout) view.findViewById(R.id.imgDownload);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail_image);
            this.layout = (LinearLayout) view.findViewById(R.id.main);
            this.downloadProgress = (ProgressBar) view.findViewById(R.id.downloadProgress);
        }
    }
}
