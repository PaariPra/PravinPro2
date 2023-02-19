package com.chettapps.invitationmaker.photomaker.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

/* loaded from: classes2.dex */
public class StickerListAdapter extends RecyclerView.Adapter<StickerListAdapter.ViewHolder> {
    private static final String TAG = "StickerAdapter";
    int arraySize;
    private int category;
    private String categoryName;
    Activity context;
    private OnClickCallback mSingleCallback;
    int[] offlineSticker;
    private PreferenceClass preferenceClass;
    List<String> serverSticker;
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

    public StickerListAdapter(Activity activity, int[] iArr, List<String> list, String str, int i) {
        this.arraySize = 0;
        this.context = activity;
        this.serverSticker = list;
        this.category = i;
        this.offlineSticker = iArr;
        this.categoryName = str;
        this.preferenceClass = new PreferenceClass(activity);
        if (list != null) {
            this.arraySize = list.size() + iArr.length;
        } else {
            this.arraySize = iArr.length;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.arraySize;
    }

    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        viewHolder.downloadProgress.setVisibility(View.INVISIBLE);
        int[] iArr = this.offlineSticker;
        if (i < iArr.length) {
            viewHolder.imgDownload.setVisibility(View.GONE);
            Glide.with(this.context).load(Integer.valueOf(this.offlineSticker[i])).thumbnail(0.1f).apply((BaseRequestOptions<?>) new RequestOptions().dontAnimate().fitCenter().placeholder(R.drawable.no_image).error(R.drawable.no_image)).into(viewHolder.imageView);
        } else {
            int length = i - iArr.length;
            String str = AllConstants.stickerURL + this.categoryName + "/" + this.serverSticker.get(length);
            Log.e(TAG, "sticker url : " + str);
            File file = new File(this.preferenceClass.getString(AllConstants.sdcardPath) + "/cat" + this.category + "/" + this.serverSticker.get(length));
            if (file.exists()) {
                viewHolder.imgDownload.setVisibility(View.GONE);
                Glide.with(this.context).load(file.getPath()).thumbnail(0.1f).apply((BaseRequestOptions<?>) new RequestOptions().dontAnimate().fitCenter().placeholder(R.drawable.no_image).error(R.drawable.no_image)).into(viewHolder.imageView);
            } else {
                viewHolder.imgDownload.setVisibility(View.VISIBLE);
                Glide.with(this.context).load(str).thumbnail(0.1f).apply((BaseRequestOptions<?>) new RequestOptions().dontAnimate().fitCenter().placeholder(R.drawable.no_image).error(R.drawable.no_image)).into(viewHolder.imageView);
            }
        }
        viewHolder.layout.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.StickerListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (i < StickerListAdapter.this.offlineSticker.length) {
                    StickerListAdapter.this.mSingleCallback.onClickCallBack(null, String.valueOf(i), StickerListAdapter.this.context,"");
                    return;
                }
                File file2 = new File(StickerListAdapter.this.preferenceClass.getString(AllConstants.sdcardPath) + "/cat" + StickerListAdapter.this.category + "/" + StickerListAdapter.this.serverSticker.get(i - StickerListAdapter.this.offlineSticker.length));
                if (file2.exists()) {
                    StickerListAdapter.this.mSingleCallback.onClickCallBack(null, String.valueOf(i),
                            StickerListAdapter.this.context, file2.getPath() );
                }
            }
        });
        viewHolder.imgDownload.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.StickerListAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!NetworkConnectivityReceiver.isConnected()) {
                    Toast.makeText(StickerListAdapter.this.context, "No Internet Connection!!!", Toast.LENGTH_SHORT).show();
                } else if (StickerListAdapter.this.isDownloadProgress) {
                    StickerListAdapter.this.isDownloadProgress = false;
                    viewHolder.downloadProgress.setVisibility(View.VISIBLE);
                    int length2 = i - StickerListAdapter.this.offlineSticker.length;
                    String str2 = AllConstants.stickerURL + StickerListAdapter.this.categoryName + "/" + StickerListAdapter.this.serverSticker.get(length2);
                    File file2 = new File(StickerListAdapter.this.preferenceClass.getString(AllConstants.sdcardPath) + "/cat" + StickerListAdapter.this.category + "/");
                    StringBuilder sb = new StringBuilder();
                    sb.append("download url : ");
                    sb.append(str2);
                    Log.e(StickerListAdapter.TAG, sb.toString());
                    viewHolder.imgDownload.setVisibility(View.GONE);
                    StickerListAdapter.this.DownoloadSticker(str2, file2.getPath(), StickerListAdapter.this.serverSticker.get(length2));
                } else {
                    Toast.makeText(StickerListAdapter.this.context, "Please wait..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setItemClickCallback(OnClickCallback onClickCallback) {
        this.mSingleCallback = onClickCallback;
    }

    public void setLayoutParams(int i) {
        this.size = i;
    }

    public void smallView(boolean z) {
        this.smallView = z;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sticker_listrow, viewGroup, false));
        viewGroup.setId(i);
        viewGroup.setFocusable(false);
        viewGroup.setFocusableInTouchMode(false);
        return viewHolder;
    }

    public void DownoloadSticker(String str, String str2, String str3) {
        AndroidNetworking.download(str, str2, str3).build().startDownload(new DownloadListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.StickerListAdapter.3
            @Override // com.androidnetworking.interfaces.DownloadListener
            public void onDownloadComplete() {
                StickerListAdapter.this.isDownloadProgress = true;
                StickerListAdapter.this.notifyDataSetChanged();
                Log.e(StickerListAdapter.TAG, "onDownloadComplete: ");
            }

            @Override // com.androidnetworking.interfaces.DownloadListener
            public void onError(ANError aNError) {
                StickerListAdapter.this.isDownloadProgress = true;
                Log.e(StickerListAdapter.TAG, "onError: ");
                StickerListAdapter.this.notifyDataSetChanged();
                Toast.makeText(StickerListAdapter.this.context, "Network Error", 0).show();
            }
        });
    }

    /* loaded from: classes2.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar downloadProgress;
        ImageView imageView;
        RelativeLayout imgDownload;
        RelativeLayout layout;
        TextView name;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.item_image);
            this.imgDownload = (RelativeLayout) view.findViewById(R.id.imgDownload);
            this.name = (TextView) view.findViewById(R.id.name);
            this.layout = (RelativeLayout) view.findViewById(R.id.lay);
            this.downloadProgress = (ProgressBar) view.findViewById(R.id.downloadProgress);
        }
    }
}
