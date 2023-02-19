package com.chettapps.invitationmaker.photomaker.adapterMaster;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chettapps.invitationmaker.R;
import com.github.rubensousa.gravitysnaphelper.GravityPagerSnapHelper;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.chettapps.invitationmaker.photomaker.main.InvitationCatActivity;
import com.chettapps.invitationmaker.photomaker.pojoClass.PosterThumbFull;
import com.chettapps.invitationmaker.photomaker.pojoClass.Snap1;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class PosterSnapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements GravitySnapHelper.SnapListener {
    private static final int PROGESS_VIEW = 0;
    private static final int VIEW_ITEM = 1;
    Activity context;
    private boolean isLoaderVisible = false;
    private ArrayList<Object> mSnaps;

    public PosterSnapAdapter(Activity activity, ArrayList<Object> arrayList) {
        this.mSnaps = arrayList;
        this.context = activity;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return (!this.isLoaderVisible || i != this.mSnaps.size() - 1) ? 1 : 0;
    }

    public void addSnap(Snap1 snap1) {
        this.mSnaps.add(snap1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new LoadingHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progress_view, viewGroup, false));
        }
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_snap, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == 1) {
            ViewHolder viewHolder2 = (ViewHolder) viewHolder;
            final Snap1 snap1 = (Snap1) this.mSnaps.get(i);
            if (snap1.getPosterThumbFulls().size() == 0) {
                viewHolder2.llItem.setVisibility(View.GONE);
            } else {
                viewHolder2.llItem.setVisibility(View.VISIBLE);
            }
            viewHolder2.snapTextView.setText(snap1.getText().toUpperCase());
            viewHolder2.recyclerView.setOnFlingListener(null);
            if (snap1.getGravity() == 8388611 || snap1.getGravity() == 8388613) {
                viewHolder2.recyclerView.setLayoutManager(new LinearLayoutManager(viewHolder2.recyclerView.getContext(), RecyclerView.HORIZONTAL, false));
                new GravitySnapHelper(snap1.getGravity(), false, this).attachToRecyclerView(viewHolder2.recyclerView);
            } else if (snap1.getGravity() == 1 || snap1.getGravity() == 16) {
                viewHolder2.recyclerView.setLayoutManager(new LinearLayoutManager(viewHolder2.recyclerView.getContext(), (snap1.getGravity() == 1) ? RecyclerView.HORIZONTAL : RecyclerView.VERTICAL, false));
                new LinearSnapHelper().attachToRecyclerView(viewHolder2.recyclerView);
            } else if (snap1.getGravity() == 17) {
                viewHolder2.recyclerView.setLayoutManager(new LinearLayoutManager(viewHolder2.recyclerView.getContext(), RecyclerView.HORIZONTAL, false));
                new GravityPagerSnapHelper(GravityCompat.START).attachToRecyclerView(viewHolder2.recyclerView);
            } else {
                viewHolder2.recyclerView.setLayoutManager(new LinearLayoutManager(viewHolder2.recyclerView.getContext()));
                new GravitySnapHelper(snap1.getGravity()).attachToRecyclerView(viewHolder2.recyclerView);
            }
            ArrayList<PosterThumbFull> arrayList = new ArrayList<>();
            if (snap1.getPosterThumbFulls().size() >= 5) {
                for (int i2 = 0; i2 < 5; i2++) {
                    arrayList.add(snap1.getPosterThumbFulls().get(i2));
                }
            } else {
                arrayList = snap1.getPosterThumbFulls();
            }
            viewHolder2.recyclerView.setAdapter(new PosterCategoryWithListAdapter(this.context, snap1.getCat_id(), snap1.getGravity() == 8388611 || snap1.getGravity() == 8388613 || snap1.getGravity() == 1, snap1.getGravity() == 17, arrayList, snap1.getRatio()));
            viewHolder2.seeMoreTextView.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapterMaster.PosterSnapAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((InvitationCatActivity) PosterSnapAdapter.this.context).itemClickSeeMoreAdapter(snap1.getPosterThumbFulls(), snap1.getCat_id(), snap1.getText(), snap1.getRatio());
                }
            });
        }
    }

    public void addData(List<Object> list) {
        if (this.mSnaps.size() > 0) {
            this.mSnaps.addAll(list);
            notifyItemChanged(list.size(), false);
            return;
        }
        this.mSnaps.addAll(list);
        notifyDataSetChanged();
    }

    public void addLoadingView() {
        this.isLoaderVisible = true;
        this.mSnaps.add(new PosterThumbFull());
        notifyItemInserted(this.mSnaps.size() - 1);
    }

    public void removeLoadingView() {
        this.isLoaderVisible = false;
        int size = this.mSnaps.size() - 1;
        if (getItem(size) != null) {
            this.mSnaps.remove(size);
            notifyItemRemoved(size);
        }
    }

    private Object getItem(int i) {
        return this.mSnaps.get(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<Object> arrayList = this.mSnaps;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    @Override // com.github.rubensousa.gravitysnaphelper.GravitySnapHelper.SnapListener
    public void onSnap(int i) {
        Log.d("Snapped: ", i + "");
    }

    /* loaded from: classes2.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llItem;
        public RecyclerView recyclerView;
        public TextView seeMoreTextView;
        public TextView snapTextView;

        ViewHolder(View view) {
            super(view);
            this.snapTextView = (TextView) view.findViewById(R.id.snapTextView);
            this.seeMoreTextView = (TextView) view.findViewById(R.id.seeMoreTextView);
            this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            this.llItem = (LinearLayout) view.findViewById(R.id.ll_item);
        }
    }

    /* loaded from: classes2.dex */
    public class LoadingHolder extends RecyclerView.ViewHolder {
        public LoadingHolder(View view) {
            super(view);
        }
    }
}
