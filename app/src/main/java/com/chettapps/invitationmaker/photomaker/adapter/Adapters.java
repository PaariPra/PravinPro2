package com.chettapps.invitationmaker.photomaker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.listener.CustomItemClickListener;
import com.chettapps.invitationmaker.photomaker.listener.OnClickCallback;
import com.chettapps.invitationmaker.photomaker.main.AllConstants;
import com.chettapps.invitationmaker.photomaker.main.BGImageActivity;
import com.chettapps.invitationmaker.photomaker.pojoClass.BackgroundImage;
import com.chettapps.invitationmaker.photomaker.utility.GlideImageLoader1;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public class Adapters extends RecyclerView.Adapter<Adapters.ViewHolder> {
    private ArrayList<BackgroundImage> backgroundImages;
    Context context;
    int flagForActivity;
    private int index;
    CustomItemClickListener listener;
    private boolean mHorizontal;
    private boolean mPager;
    private OnClickCallback mSingleCallback;
    SharedPreferences preferences;

    public Adapters(Context context, boolean z, boolean z2, ArrayList<BackgroundImage> arrayList, int i, int i2, CustomItemClickListener customItemClickListener) {
        this.mHorizontal = z;
        this.backgroundImages = arrayList;
        this.mPager = z2;
        this.context = context;
        this.flagForActivity = i;
        this.index = i2;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.listener = customItemClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (this.mPager) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_pager, viewGroup, false));
        }
        if (this.mHorizontal) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter, viewGroup, false));
        }
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_vertical, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        RequestOptions requestOptions;
        this.backgroundImages.get(i);
        if (i > 4) {
            viewHolder.ivLock.setVisibility(View.GONE);
            viewHolder.imageView.setVisibility(View.INVISIBLE);
            viewHolder.rl_see_more.setVisibility(View.VISIBLE);
            viewHolder.rl_see_more.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.Adapters.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (Adapters.this.listener != null) {
                        Adapters.this.listener.onItemClick(i);
                    }
                }
            });
            return;
        }
        if (this.index > 1) {
            requestOptions = new RequestOptions().priority(Priority.HIGH);
        } else {
            requestOptions = new RequestOptions().priority(Priority.HIGH);
        }
        final String str = AllConstants.BASE_URL_BG + "/" + this.backgroundImages.get(i).getImage_url();
        new GlideImageLoader1(viewHolder.imageView, viewHolder.mProgressBar).load(AllConstants.BASE_URL_BG + "/" + this.backgroundImages.get(i).getThumb_url(), requestOptions);
        if (i <= 11 || this.preferences.getBoolean("isAdsDisabled", false)) {
            viewHolder.ivLock.setVisibility(View.GONE);
        } else {
            viewHolder.ivLock.setVisibility(View.GONE);
        }
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.Adapters.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Adapters.this.flagForActivity == 1) {
                    ((BGImageActivity) Adapters.this.context).onGetPosition(str);
                } else {
                    Adapters.this.mSingleCallback.onClickCallBack( Adapters.this.backgroundImages, str, (FragmentActivity) Adapters.this.context, "");
                }
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
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
            this.ivLock = (ImageView) view.findViewById(R.id.iv_lock);
            this.nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            this.ratingTextView = (TextView) view.findViewById(R.id.ratingTextView);
            this.mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
            this.rl_see_more = (RelativeLayout) view.findViewById(R.id.rl_see_more);
        }
    }
}
