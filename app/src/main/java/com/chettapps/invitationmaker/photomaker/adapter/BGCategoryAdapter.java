package com.chettapps.invitationmaker.photomaker.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.listener.OnClickCallback;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public class BGCategoryAdapter extends RecyclerView.Adapter<BGCategoryAdapter.ViewHolder> {
    String[] artworkIconNormal;
    Activity context;
    private OnClickCallback mSingleCallback;
    int selected_position = 0;
    int[] stickerImgNormal;
    int[] stickerImgSelected;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return i;
    }

    public BGCategoryAdapter(Activity activity, String[] strArr, int[] iArr, int[] iArr2) {
        this.context = activity;
        this.artworkIconNormal = strArr;
        this.stickerImgNormal = iArr;
        this.stickerImgSelected = iArr2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.artworkIconNormal.length;
    }

    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        if (this.selected_position == i) {
            viewHolder.name.setText(this.artworkIconNormal[i]);
            viewHolder.name.setTextColor(this.context.getResources().getColor(R.color.purple_200));
            viewHolder.imageView.setImageResource(this.stickerImgSelected[i]);
        } else {
            viewHolder.name.setText(this.artworkIconNormal[i]);
            viewHolder.name.setTextColor(this.context.getResources().getColor(R.color.black));
            viewHolder.imageView.setImageResource(this.stickerImgNormal[i]);
        }
        viewHolder.layout.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.BGCategoryAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BGCategoryAdapter.this.mSingleCallback.onClickCallBack(null, String.valueOf(i), BGCategoryAdapter.this.context,"" );
                BGCategoryAdapter bGCategoryAdapter = BGCategoryAdapter.this;
                bGCategoryAdapter.notifyItemChanged(bGCategoryAdapter.selected_position);
                BGCategoryAdapter.this.selected_position = i;
                BGCategoryAdapter bGCategoryAdapter2 = BGCategoryAdapter.this;
                bGCategoryAdapter2.notifyItemChanged(bGCategoryAdapter2.selected_position);
            }
        });
    }

    public void setItemClickCallback(OnClickCallback onClickCallback) {
        this.mSingleCallback = onClickCallback;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sticker_cat_list, viewGroup, false));
        viewGroup.setId(i);
        viewGroup.setFocusable(false);
        viewGroup.setFocusableInTouchMode(false);
        return viewHolder;
    }

    public void setMyFontNormal(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                setMyFontNormal((ViewGroup) childAt);
            } else if (childAt instanceof TextView) {
                ((TextView) childAt).setTypeface(Typeface.createFromAsset(this.context.getAssets(), "font/Montserrat-Medium.ttf"));
            } else if (childAt instanceof Button) {
                ((TextView) childAt).setTypeface(Typeface.createFromAsset(this.context.getAssets(), "font/Montserrat-Medium.ttf"));
            } else if (childAt instanceof EditText) {
                ((TextView) childAt).setTypeface(Typeface.createFromAsset(this.context.getAssets(), "font/Montserrat-Medium.ttf"));
            }
        }
    }

    /* loaded from: classes2.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        LinearLayout layout;
        TextView name;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.item_image);
            this.name = (TextView) view.findViewById(R.id.name);
            this.layout = (LinearLayout) view.findViewById(R.id.lay);
            BGCategoryAdapter.this.setMyFontNormal((ViewGroup) view.findViewById(R.id.main));
        }
    }
}
