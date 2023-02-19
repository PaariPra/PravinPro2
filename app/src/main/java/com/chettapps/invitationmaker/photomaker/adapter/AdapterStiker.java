package com.chettapps.invitationmaker.photomaker.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.activity.IntegerVersionSignature;
import com.chettapps.invitationmaker.photomaker.listener.CustomItemClickListener;
import com.chettapps.invitationmaker.photomaker.listener.OnClickCallback;
import com.chettapps.invitationmaker.photomaker.main.AllConstants;
import com.chettapps.invitationmaker.photomaker.network.NetworkConnectivityReceiver;
import com.chettapps.invitationmaker.photomaker.pojoClass.BackgroundImage;
import com.chettapps.invitationmaker.photomaker.utils.PreferenceClass;


import java.io.File;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class AdapterStiker extends RecyclerView.Adapter<AdapterStiker.ViewHolder> {
    private PreferenceClass appPreference;
    private ArrayList<BackgroundImage> backgroundImages;
    String color;
    Context context;
    int flagForActivity;
    private boolean isDownloadProgress = true;
    CustomItemClickListener listener;
    private boolean mHorizontal;
    private boolean mPager;
    private OnClickCallback mSingleCallback;
    SharedPreferences preferences;

    public AdapterStiker(Context context, boolean z, boolean z2, ArrayList<BackgroundImage> arrayList, int i, String str, CustomItemClickListener customItemClickListener) {
        this.mHorizontal = z;
        this.backgroundImages = arrayList;
        this.mPager = z2;
        this.context = context;
        this.flagForActivity = i;
        this.appPreference = new PreferenceClass(context);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.color = str;
        this.listener = customItemClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (this.mPager) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_pager, viewGroup, false));
        }
        if (this.mHorizontal) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapters, viewGroup, false));
        }
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_vertical, viewGroup, false));
    }

    public static String getFileNameFromUrl(String str) {
        return str.substring(str.lastIndexOf(47) + 1).split("\\?")[0].split("#")[0];
    }

    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        String[] split = new String[0];
        final BackgroundImage backgroundImage = this.backgroundImages.get(i);
        if (i == this.backgroundImages.size() - 1) {
            viewHolder.imgDownload.setVisibility(View.GONE);
            viewHolder.mProgressBar.setVisibility(View.GONE);
            viewHolder.ivLock.setVisibility(View.GONE);
            viewHolder.imageView.setVisibility(View.INVISIBLE);
            viewHolder.rl_see_more.setVisibility(View.VISIBLE);
            viewHolder.rl_see_more.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.AdapterStiker.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (AdapterStiker.this.listener != null) {
                        AdapterStiker.this.listener.onItemClick(i);
                    }
                }
            });
            return;
        }
        String str = AllConstants.BASE_URL_BG + "/Sticker/" + backgroundImage.getImage_url();
        final String str2 = Uri.parse(str).getPath().split("/")[split.length - 2];
        viewHolder.rl_see_more.setVisibility(View.GONE);
        viewHolder.imageView.setVisibility(View.VISIBLE);
        File file = new File(this.appPreference.getString(AllConstants.sdcardPath) + "/cat/" + str2 + "/" + getFileNameFromUrl(str));
        if (file.exists()) {
            viewHolder.imgDownload.setVisibility(View.GONE);
            viewHolder.mProgressBar.setVisibility(View.GONE);
            Glide.with(this.context).load(file.getPath()).thumbnail(0.1f).apply((BaseRequestOptions<?>)
                    new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).signature(new IntegerVersionSignature(AllConstants.getVersionInfo())).dontAnimate().fitCenter().placeholder(R.drawable.no_image).error(R.drawable.no_image)).into(viewHolder.imageView);
        } else {
            viewHolder.imgDownload.setVisibility(View.VISIBLE);
            viewHolder.mProgressBar.setVisibility(View.GONE);
            Glide.with(this.context).load(str).thumbnail(0.1f).apply((BaseRequestOptions<?>) new
                    RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).signature(new IntegerVersionSignature(AllConstants.getVersionInfo())).dontAnimate().fitCenter().placeholder(R.drawable.no_image).error(R.drawable.no_image)).into(viewHolder.imageView);
        }
        viewHolder.imgDownload.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.AdapterStiker.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!NetworkConnectivityReceiver.isConnected()) {
                    Toast.makeText(AdapterStiker.this.context, "No Internet Connection!!!", Toast.LENGTH_SHORT).show();
                } else if (AdapterStiker.this.isDownloadProgress) {
                    AdapterStiker.this.isDownloadProgress = false;
                    viewHolder.mProgressBar.setVisibility(View.VISIBLE);
                    String str3 = AllConstants.BASE_URL_BG + "/Sticker/" + backgroundImage.getImage_url();
                    File file2 = new File(AdapterStiker.this.appPreference.getString(AllConstants.sdcardPath) + "/cat/" + str2 + "/");
                    String fileNameFromUrl = AdapterStiker.getFileNameFromUrl(str3);
                    viewHolder.imgDownload.setVisibility(View.GONE);
                    AdapterStiker.this.DownoloadSticker(str3, file2.getPath(), fileNameFromUrl);
                } else {
                    Toast.makeText(AdapterStiker.this.context, "Please wait..", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.AdapterStiker.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String str3 = AllConstants.BASE_URL_BG + "/Sticker/" + backgroundImage.getImage_url();
                File file2 = new File(AdapterStiker.this.appPreference.getString(AllConstants.sdcardPath) + "/cat/" + str2 + "/" + AdapterStiker.getFileNameFromUrl(str3));
                if (file2.exists()) {
                    AdapterStiker.this.mSingleCallback.onClickCallBack( AdapterStiker.this.backgroundImages, file2.getPath(), (FragmentActivity) AdapterStiker.this.context, AdapterStiker.this.color);
                }
            }
        });
    }

    public void DownoloadSticker(String str, String str2, String str3) {
        AndroidNetworking.download(str, str2, str3).build().startDownload(new DownloadListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.AdapterStiker.4
            @Override // com.androidnetworking.interfaces.DownloadListener
            public void onDownloadComplete() {
                AdapterStiker.this.isDownloadProgress = true;
                AdapterStiker.this.notifyDataSetChanged();
            }

            @Override // com.androidnetworking.interfaces.DownloadListener
            public void onError(ANError aNError) {
                AdapterStiker.this.isDownloadProgress = true;
                AdapterStiker.this.notifyDataSetChanged();
                Toast.makeText(AdapterStiker.this.context, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setItemClickCallback(OnClickCallback onClickCallback) {
        this.mSingleCallback = onClickCallback;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return super.getItemViewType(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.backgroundImages.size();
    }

    /* loaded from: classes2.dex */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        RelativeLayout imgDownload;
        ImageView ivLock;
        public ProgressBar mProgressBar;
        public TextView nameTextView;
        public TextView ratingTextView;
        public RelativeLayout rl_see_more;

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
        }

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.imgDownload = (RelativeLayout) view.findViewById(R.id.imgDownload);
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
            this.ivLock = (ImageView) view.findViewById(R.id.iv_lock);
            this.nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            this.ratingTextView = (TextView) view.findViewById(R.id.ratingTextView);
            this.mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
            this.rl_see_more = (RelativeLayout) view.findViewById(R.id.rl_see_more);
        }
    }
}
