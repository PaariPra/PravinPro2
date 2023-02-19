package com.chettapps.invitationmaker.photomaker.adapterMaster;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.main.InvitationCatActivity;
import com.chettapps.invitationmaker.photomaker.pojoClass.PosterThumbFull;
import com.chettapps.invitationmaker.photomaker.utility.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;


public class SeeMorePosterListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int PROGESS_VIEW = 0;
    private static final int VIEW_ITEM = 1;
    int catID;
    private Activity context;
    private ArrayList<Object> posterDatas;
    String ratio;
    RecyclerView recyclerView;
    private String TAG = getClass().getSimpleName();
    private boolean isLoaderVisible = false;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    public SeeMorePosterListAdapter(int i, ArrayList<Object> arrayList, String str, Activity activity, RecyclerView recyclerView) {
        this.posterDatas = arrayList;
        this.context = activity;
        this.catID = i;
        this.ratio = str;
        this.recyclerView = recyclerView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new LoadingHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progress_view, viewGroup, false));
        }
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_more_poster, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == 1) {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
            final PosterThumbFull posterThumbFull = (PosterThumbFull) this.posterDatas.get(i);
            new GlideImageLoader(myViewHolder.ivImage, myViewHolder.mProgressBar).load(posterThumbFull.getPost_thumb(), new RequestOptions().fitCenter().priority(Priority.HIGH));
            if (i > 4) {
                myViewHolder.ivLock.setVisibility(8);
            } else {
                myViewHolder.ivLock.setVisibility(8);
            }
            myViewHolder.cardView.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapterMaster.SeeMorePosterListAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((InvitationCatActivity) SeeMorePosterListAdapter.this.context).openPosterActivity(posterThumbFull.getPost_id(), SeeMorePosterListAdapter.this.catID);
                }
            });
        }
    }

    public void addData(List<Object> list) {
        if (this.posterDatas.size() > 0) {
            this.posterDatas.addAll(list);
            notifyItemChanged(list.size(), false);
            return;
        }
        this.posterDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void addLoadingView() {
        this.isLoaderVisible = true;
        this.posterDatas.add(new PosterThumbFull());
        notifyItemInserted(this.posterDatas.size() - 1);
    }

    public void removeLoadingView() {
        ArrayList<Object> arrayList = this.posterDatas;
        if (arrayList != null && arrayList.size() > 0) {
            this.isLoaderVisible = false;
            int size = this.posterDatas.size() - 1;
            if (getItem(size) != null) {
                this.posterDatas.remove(size);
                notifyItemRemoved(size);
            }
        }
    }

    private Object getItem(int i) {
        return this.posterDatas.get(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<Object> arrayList = this.posterDatas;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return (!this.isLoaderVisible || i != this.posterDatas.size() - 1) ? 1 : 0;
    }

    /* loaded from: classes2.dex */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView ivImage;
        ImageView ivLock;
        ImageView ivRateUs;
        ProgressBar mProgressBar;

        public MyViewHolder(View view) {
            super(view);
            this.ivImage = (ImageView) view.findViewById(R.id.iv_image);
            this.ivRateUs = (ImageView) view.findViewById(R.id.iv_rate_us);
            this.ivLock = (ImageView) view.findViewById(R.id.iv_lock);
            this.cardView = (CardView) view.findViewById(R.id.cv_image);
            this.mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }

    /* loaded from: classes2.dex */
    public class LoadingHolder extends RecyclerView.ViewHolder {
        public LoadingHolder(View view) {
            super(view);
        }
    }
}
