package com.chettapps.invitationmaker.photomaker.adapterMaster;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.listener.CustomItemClickListener;
import com.chettapps.invitationmaker.photomaker.main.InvitationCatActivity;
import com.chettapps.invitationmaker.photomaker.pojoClass.PosterThumbFull;
import com.chettapps.invitationmaker.photomaker.utility.GlideImageLoaderPoster;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public class PosterCategoryWithListAdapter extends RecyclerView.Adapter<PosterCategoryWithListAdapter.ViewHolder> {
    int cat_id;
    Context context;
    CustomItemClickListener listener;
    private boolean mHorizontal;
    private boolean mPager;
    private ArrayList<PosterThumbFull> posterThumbFulls;
    String ratio;

    public PosterCategoryWithListAdapter(Context context, int i, boolean z, boolean z2, ArrayList<PosterThumbFull> arrayList, String str) {
        this.mHorizontal = z;
        this.posterThumbFulls = arrayList;
        this.mPager = z2;
        this.context = context;
        this.cat_id = i;
        this.ratio = str;
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
        viewHolder.ivLock.setVisibility(8);
        new GlideImageLoaderPoster(viewHolder.imageView, viewHolder.mProgressBar).load(this.posterThumbFulls.get(i).getPost_thumb(), new RequestOptions().centerCrop().priority(Priority.HIGH));
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapterMaster.PosterCategoryWithListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((InvitationCatActivity) PosterCategoryWithListAdapter.this.context).openPosterActivity(((PosterThumbFull) PosterCategoryWithListAdapter.this.posterThumbFulls.get(i)).getPost_id(), PosterCategoryWithListAdapter.this.cat_id);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return super.getItemViewType(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.posterThumbFulls.size();
    }

    /* loaded from: classes2.dex */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        ImageView ivLock;
        public ProgressBar mProgressBar;
        public TextView nameTextView;
        public TextView ratingTextView;
        public RelativeLayout rl_see_more;

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

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Log.d("backgroundImages", "==" + ((PosterThumbFull) PosterCategoryWithListAdapter.this.posterThumbFulls.get(getAdapterPosition())).getPost_id());
        }
    }
}
