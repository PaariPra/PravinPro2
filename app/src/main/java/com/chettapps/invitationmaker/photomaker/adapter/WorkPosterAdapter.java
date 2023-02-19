package com.chettapps.invitationmaker.photomaker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;

import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.listener.OnClickCallback;

import java.io.File;
import java.util.ArrayList;


public class WorkPosterAdapter extends BaseAdapter {
    Context context;
    File[] listFile;
    private OnClickCallback mSingleCallback;
    int screenWidth;

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return 0L;
    }

    public WorkPosterAdapter(Context context, File[] fileArr, int i) {
        this.context = context;
        this.listFile = fileArr;
        this.screenWidth = i;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.listFile.length;
    }

    @Override // android.widget.Adapter
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View view2;
        ViewHolder2 viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            viewHolder = new ViewHolder2();
            view2 = layoutInflater.inflate(R.layout.grid_itemthumb, (ViewGroup) null);
            viewHolder.imageview = (ImageView) view2.findViewById(R.id.image);
            viewHolder.imgDeletePoster = (ImageView) view2.findViewById(R.id.imgDeletePoster);
            view2.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder2) view.getTag();
            view2 = view;
        }
        viewHolder.imageview.setId(i);
        viewHolder.imgDeletePoster.setVisibility(View.VISIBLE);
        Glide.with(this.context).load(this.listFile[i]).thumbnail(0.1f).apply((BaseRequestOptions<?>)
                new RequestOptions().dontAnimate().placeholder(R.drawable.no_image).error(R.drawable.no_image)).into(viewHolder.imageview);
        viewHolder.imgDeletePoster.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.WorkPosterAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view3) {
                WorkPosterAdapter.this.mSingleCallback.onClickCallBack(null, String.valueOf(i),  WorkPosterAdapter.this.context,"");
            }
        });
        return view2;
    }

    public void setItemClickCallback(OnClickCallback onClickCallback) {
        this.mSingleCallback = onClickCallback;
    }
}
