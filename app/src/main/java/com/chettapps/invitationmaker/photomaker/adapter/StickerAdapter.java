package com.chettapps.invitationmaker.photomaker.adapter;

import android.app.Activity;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
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
import com.chettapps.invitationmaker.photomaker.main.AllConstants;
import com.chettapps.invitationmaker.photomaker.main.OnClickCallback;
import com.chettapps.invitationmaker.photomaker.network.NetworkConnectivityReceiver;
import com.chettapps.invitationmaker.photomaker.pojoClass.BackgroundImage;
import com.chettapps.invitationmaker.photomaker.utils.PreferenceClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class StickerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "StickerAdapter";
    private PreferenceClass appPreference;
    ArrayList<BackgroundImage> category_list;
    private int cellPadding;
    private final int cellSize;
    String color;
    Activity context;
    private OnClickCallback<ArrayList<String>, Integer, String, Activity, String> mSingleCallback;
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_LOADING = 1;
    private boolean isDownloadProgress = true;
    int size = 0;
    private int cellLimit = 0;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    public StickerAdapter(Activity activity, ArrayList<BackgroundImage> arrayList, int i, int i2, String str) {
        this.context = activity;
        this.appPreference = new PreferenceClass(activity);
        this.category_list = arrayList;
        this.cellSize = i;
        this.cellPadding = i2;
        this.color = str;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        int size = this.category_list.size();
        int i = this.cellLimit;
        return i > 0 ? Math.min(size, i) : size;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        String[] split = new String[0];
        if (getItemViewType(i) == 0) {
            final ViewHolder viewHolder2 = (ViewHolder) viewHolder;
            final BackgroundImage backgroundImage = this.category_list.get(i);
            String str = AllConstants.BASE_URL_BG + "/Sticker/" + backgroundImage.getImage_url();
            final String str2 = Uri.parse(str).getPath().split("/")[split.length - 2];
            File file = new File(this.appPreference.getString(AllConstants.sdcardPath) + "/cat/" + str2 + "/" + getFileNameFromUrl(str));
            if (file.exists()) {
                viewHolder2.downloadProgress.setVisibility(View.GONE);
                viewHolder2.imgDownload.setVisibility(View.GONE);
                Glide.with(this.context).load(file.getPath()).thumbnail(0.1f).apply((BaseRequestOptions<?>) new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).signature(new IntegerVersionSignature(AllConstants.getVersionInfo())).dontAnimate().fitCenter().placeholder(R.drawable.no_image).error(R.drawable.no_image)).into(viewHolder2.imageView);
            } else {
                viewHolder2.downloadProgress.setVisibility(View.GONE);
                viewHolder2.imgDownload.setVisibility(View.VISIBLE);
                Glide.with(this.context).load(str).thumbnail(0.1f).apply((BaseRequestOptions<?>) new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).signature(new IntegerVersionSignature(AllConstants.getVersionInfo())).dontAnimate().fitCenter().placeholder(R.drawable.no_image).error(R.drawable.no_image)).into(viewHolder2.imageView);
            }
            viewHolder2.imgDownload.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.StickerAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (!NetworkConnectivityReceiver.isConnected()) {
                        Toast.makeText(StickerAdapter.this.context, "No Internet Connection!!!", Toast.LENGTH_SHORT).show();
                    } else if (StickerAdapter.this.isDownloadProgress) {
                        StickerAdapter.this.isDownloadProgress = false;
                        viewHolder2.downloadProgress.setVisibility(0);
                        String str3 = AllConstants.BASE_URL_BG + "/Sticker/" + backgroundImage.getImage_url();
                        StickerAdapter.this.DownoloadSticker(str3, new File(StickerAdapter.this.appPreference.getString(AllConstants.sdcardPath) + "/cat/" + str2 + "/").getPath(), StickerAdapter.getFileNameFromUrl(str3));
                    } else {
                        Toast.makeText(StickerAdapter.this.context, "Please wait..", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            viewHolder2.layout.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.StickerAdapter.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    String str3 = AllConstants.BASE_URL_BG + "/Sticker/" + backgroundImage.getImage_url();
                    File file2 = new File(StickerAdapter.this.appPreference.getString(AllConstants.sdcardPath) + "/cat/" + str2 + "/" + StickerAdapter.getFileNameFromUrl(str3));
                    if (file2.exists()) {
                        StickerAdapter.this.mSingleCallback.onClickCallBack(null, StickerAdapter.this.category_list, file2.getPath(), StickerAdapter.this.context, StickerAdapter.this.color);
                    }
                }
            });
        }
    }

    public void addData(List<BackgroundImage> list) {
        notifyDataSetChanged();
    }

    public void addLoadingView() {
        new Handler().post(new Runnable() { // from class: com.chettapps.invitationmaker.photomaker.adapter.StickerAdapter.3
            @Override // java.lang.Runnable
            public void run() {
                StickerAdapter.this.category_list.add(null);
                StickerAdapter stickerAdapter = StickerAdapter.this;
                stickerAdapter.notifyItemInserted(stickerAdapter.category_list.size() - 1);
            }
        });
    }

    public void removeLoadingView() {
        ArrayList<BackgroundImage> arrayList = this.category_list;
        arrayList.remove(arrayList.size() - 1);
        notifyItemRemoved(this.category_list.size());
    }

    public static String getFileNameFromUrl(String str) {
        return str.substring(str.lastIndexOf(47) + 1).split("\\?")[0].split("#")[0];
    }

    public void setItemClickCallback(OnClickCallback onClickCallback) {
        this.mSingleCallback = onClickCallback;
    }

    public void setLayoutParams(int i) {
        this.size = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sticker_listrowwr, viewGroup, false));
        }
        return new LoadingHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progress_view, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.category_list.get(i) == null ? 1 : 0;
    }

    public void DownoloadSticker(String str, String str2, String str3) {
        AndroidNetworking.download(str, str2, str3).build().startDownload(new DownloadListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.StickerAdapter.4
            @Override // com.androidnetworking.interfaces.DownloadListener
            public void onDownloadComplete() {
                StickerAdapter.this.isDownloadProgress = true;
                StickerAdapter.this.notifyDataSetChanged();
                Log.e(StickerAdapter.TAG, "onDownloadComplete: ");
            }

            @Override // com.androidnetworking.interfaces.DownloadListener
            public void onError(ANError aNError) {
                StickerAdapter.this.isDownloadProgress = true;
                Log.e(StickerAdapter.TAG, "onError: ");
                StickerAdapter.this.notifyDataSetChanged();
                Toast.makeText(StickerAdapter.this.context, "Network Error", 0).show();
            }
        });
    }

    /* loaded from: classes2.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView = (CardView) this.itemView.findViewById(R.id.cv_image);
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

    /* loaded from: classes2.dex */
    public class LoadingHolder extends RecyclerView.ViewHolder {
        public LoadingHolder(View view) {
            super(view);
        }
    }
}
