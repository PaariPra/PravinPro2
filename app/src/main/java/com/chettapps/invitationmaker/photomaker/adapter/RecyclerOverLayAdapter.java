package com.chettapps.invitationmaker.photomaker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chettapps.invitationmaker.R;

/* loaded from: classes2.dex */
public class RecyclerOverLayAdapter extends RecyclerView.Adapter<RecyclerOverLayAdapter.ViewHolder> {
    Context context;
    int[] makeUpEditImage;
    int selected_position = 500;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return i;
    }

    /* loaded from: classes2.dex */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        LinearLayout layout;
        ImageView viewImage;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.item_image);
            this.viewImage = (ImageView) view.findViewById(R.id.view_image);
            this.layout = (LinearLayout) view.findViewById(R.id.lay);
        }
    }

    public RecyclerOverLayAdapter(Context context, int[] iArr) {
        this.context = context;
        this.makeUpEditImage = iArr;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.makeUpEditImage.length;
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Glide.with(this.context).load(Integer.valueOf(this.makeUpEditImage[i])).thumbnail(0.1f).apply((BaseRequestOptions<?>) new RequestOptions().dontAnimate().centerCrop().placeholder(R.drawable.no_image).error(R.drawable.no_image)).into(viewHolder.imageView);
        if (this.selected_position == i) {
            viewHolder.viewImage.setVisibility(0);
        } else {
            viewHolder.viewImage.setVisibility(4);
        }
        viewHolder.layout.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.adapter.RecyclerOverLayAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RecyclerOverLayAdapter recyclerOverLayAdapter = RecyclerOverLayAdapter.this;
                recyclerOverLayAdapter.notifyItemChanged(recyclerOverLayAdapter.selected_position);
                RecyclerOverLayAdapter.this.selected_position = i;
                RecyclerOverLayAdapter recyclerOverLayAdapter2 = RecyclerOverLayAdapter.this;
                recyclerOverLayAdapter2.notifyItemChanged(recyclerOverLayAdapter2.selected_position);
            }
        });
    }

    public void setSelected(int i) {
        this.selected_position = i;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_adapter, viewGroup, false));
        viewGroup.setId(i);
        viewGroup.setFocusable(false);
        viewGroup.setFocusableInTouchMode(false);
        return viewHolder;
    }
}
