package com.chettapps.invitationmaker.photomaker.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.chettapps.invitationmaker.R;
import com.github.rubensousa.gravitysnaphelper.GravityPagerSnapHelper;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.chettapps.invitationmaker.photomaker.listener.CustomItemClickListener;
import com.chettapps.invitationmaker.photomaker.main.BGImageActivity;
import com.chettapps.invitationmaker.photomaker.main.OnClickCallback;
import com.chettapps.invitationmaker.photomaker.pojoClass.BackgroundImage;
import com.chettapps.invitationmaker.photomaker.pojoClass.Snap;

import java.util.ArrayList;
import java.util.List;


public class VerticalStickerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements GravitySnapHelper.SnapListener {
    private static final int PROGESS_VIEW = 0;
    private static final int VIEW_ITEM = 1;
    Activity context;
    int flagForActivity;
    RecyclerView mRecyclerView;
    private OnClickCallback<ArrayList<String>, Integer, String, Activity, String> mSingleCallback;
    private ArrayList<Object> mSnaps;
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.VerticalStickerAdapter.1
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        }
    };

    public VerticalStickerAdapter(Activity activity, ArrayList<Object> arrayList, RecyclerView recyclerView, int i) {
        this.mSnaps = arrayList;
        this.context = activity;
        this.flagForActivity = i;
        this.mRecyclerView = recyclerView;
    }

    public void addSnap(Snap snap) {
        this.mSnaps.add(snap);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.mSnaps.get(i) == null ? 0 : 1;
    }

    public void addData(List<Object> list) {
        notifyDataSetChanged();
    }

    public void addLoadingView() {
        new Handler().post(new Runnable() { // from class: com.chettapps.invitationmaker.photomaker.adapter.VerticalStickerAdapter.2
            @Override // java.lang.Runnable
            public void run() {
                VerticalStickerAdapter.this.mSnaps.add(null);
                VerticalStickerAdapter verticalStickerAdapter = VerticalStickerAdapter.this;
                verticalStickerAdapter.notifyItemInserted(verticalStickerAdapter.mSnaps.size() - 1);
            }
        });
    }

    public void removeLoadingView() {
        ArrayList<Object> arrayList = this.mSnaps;
        arrayList.remove(arrayList.size() - 1);
        notifyItemRemoved(this.mSnaps.size());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new LoadingHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progress_view, viewGroup, false));
        }
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_snap, viewGroup, false));
    }

    public void setItemClickCallback(OnClickCallback onClickCallback) {
        this.mSingleCallback = onClickCallback;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == 1) {
            final ViewHolder viewHolder2 = (ViewHolder) viewHolder;
            final Snap snap = (Snap) this.mSnaps.get(i);
            final String str = snap.getText().toUpperCase().contains("WHITE") ? "white" : "colored";
            viewHolder2.snapTextView.setText(snap.getText().replace("white", "").toUpperCase());
            viewHolder2.recyclerView.setOnFlingListener(null);
            boolean z = false;
            if (snap.getGravity() == 8388611 || snap.getGravity() == 8388613) {
                viewHolder2.recyclerView.setLayoutManager(new LinearLayoutManager(viewHolder2.recyclerView.getContext(), RecyclerView.HORIZONTAL, false));
                new GravitySnapHelper(snap.getGravity(), false, this).attachToRecyclerView(viewHolder2.recyclerView);
            } else if (snap.getGravity() == 1 || snap.getGravity() == 16) {
                viewHolder2.recyclerView.setLayoutManager(new LinearLayoutManager(viewHolder2.recyclerView.getContext(), snap.getGravity() == 1 ? RecyclerView.HORIZONTAL : RecyclerView.VERTICAL, false));
                new LinearSnapHelper().attachToRecyclerView(viewHolder2.recyclerView);
            } else if (snap.getGravity() == 17) {
                viewHolder2.recyclerView.setLayoutManager(new LinearLayoutManager(viewHolder2.recyclerView.getContext(), RecyclerView.HORIZONTAL,
                        false));
                new GravityPagerSnapHelper(GravityCompat.START).attachToRecyclerView(viewHolder2.recyclerView);
            } else {
                viewHolder2.recyclerView.setLayoutManager(new LinearLayoutManager(viewHolder2.recyclerView.getContext()));
                new GravitySnapHelper(snap.getGravity()).attachToRecyclerView(viewHolder2.recyclerView);
            }
            if (((Snap) this.mSnaps.get(i)).getBackgroundImages().size() > 3) {
                viewHolder2.seeMoreTextView.setVisibility(View.VISIBLE);
            } else {
                viewHolder2.seeMoreTextView.setVisibility(View.GONE);
            }
            ArrayList<BackgroundImage> arrayList = new ArrayList<>();
            if (snap.getBackgroundImages().size() >= 6) {
                for (int i2 = 0; i2 < 6; i2++) {
                    arrayList.add(snap.getBackgroundImages().get(i2));
                }
            } else {
                arrayList = snap.getBackgroundImages();
            }
            if (this.flagForActivity == 1) {
                Activity activity = this.context;
                boolean z2 = snap.getGravity() == 8388611 || snap.getGravity() == 8388613 || snap.getGravity() == 1;
                if (snap.getGravity() == 17) {
                    z = true;
                }
                viewHolder2.recyclerView.setAdapter(new AdapterStiker(activity, z2, z, arrayList, this.flagForActivity, str, new CustomItemClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.VerticalStickerAdapter.3
                    @Override // com.chettapps.invitationmaker.photomaker.listener.CustomItemClickListener
                    public void onItemClick(int i3) {
                        viewHolder2.seeMoreTextView.performClick();
                    }
                }));
            } else {
                Activity activity2 = this.context;
                boolean z3 = snap.getGravity() == 8388611 || snap.getGravity() == 8388613 || snap.getGravity() == 1;
                if (snap.getGravity() == 17) {
                    z = true;
                }
                AdapterStiker adapterStiker = new AdapterStiker(activity2, z3, z, arrayList, this.flagForActivity, str, new CustomItemClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.VerticalStickerAdapter.4
                    @Override // com.chettapps.invitationmaker.photomaker.listener.CustomItemClickListener
                    public void onItemClick(int i3) {
                        viewHolder2.seeMoreTextView.performClick();
                    }
                });
                viewHolder2.recyclerView.setAdapter(adapterStiker);
                adapterStiker.setItemClickCallback(new com.chettapps.invitationmaker.photomaker.listener.OnClickCallback() {
                    @Override
                    public void onClickCallBack(ArrayList<BackgroundImage> backgroundImages, String str, Context context, String s) {

                        VerticalStickerAdapter.this.mSingleCallback.onClickCallBack(null, snap.getBackgroundImages(), str, (FragmentActivity) VerticalStickerAdapter.this.context, s);
                    }
                });

//


            }
            viewHolder2.seeMoreTextView.setOnClickListener(new View.OnClickListener() {

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (VerticalStickerAdapter.this.flagForActivity == 1) {
                        ((BGImageActivity) VerticalStickerAdapter.this.context).itemClickSeeMoreAdapter(snap.getBackgroundImages(), snap.getText());
                    } else {
                        VerticalStickerAdapter.this.mSingleCallback.onClickCallBack(null, snap.getBackgroundImages(), "", (FragmentActivity) VerticalStickerAdapter.this.context, str);
                    }
                }
            });
        }
    }

    public void removeAt(final int i) {
        new Handler().post(new Runnable() { // from class: com.chettapps.invitationmaker.photomaker.adapter.VerticalStickerAdapter.7
            @Override // java.lang.Runnable
            public void run() {
                if (VerticalStickerAdapter.this.mRecyclerView != null && !VerticalStickerAdapter.this.mRecyclerView.isComputingLayout()) {
                    VerticalStickerAdapter.this.mSnaps.remove(i);
                    VerticalStickerAdapter.this.notifyItemRemoved(i);
                    VerticalStickerAdapter verticalStickerAdapter = VerticalStickerAdapter.this;
                    verticalStickerAdapter.notifyItemRangeChanged(i, verticalStickerAdapter.mSnaps.size());
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mSnaps.size();
    }

    @Override // com.github.rubensousa.gravitysnaphelper.GravitySnapHelper.SnapListener
    public void onSnap(int i) {
        Log.d("Snapped: ", i + "");
    }

    /* loaded from: classes2.dex */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView recyclerView;
        public TextView seeMoreTextView;
        public TextView snapTextView;

        public ViewHolder(View view) {
            super(view);
            this.snapTextView = (TextView) view.findViewById(R.id.snapTextView);
            this.seeMoreTextView = (TextView) view.findViewById(R.id.seeMoreTextView);
            this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        }
    }

    /* loaded from: classes2.dex */
    public class LoadingHolder extends RecyclerView.ViewHolder {
        public LoadingHolder(View view) {
            super(view);
        }
    }
}
