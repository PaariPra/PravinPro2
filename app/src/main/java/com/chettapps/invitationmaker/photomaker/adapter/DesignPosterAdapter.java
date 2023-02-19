package com.chettapps.invitationmaker.photomaker.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import androidx.core.content.FileProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.handler.BitmapDataObject;
import com.chettapps.invitationmaker.photomaker.handler.TemplateInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;


public class DesignPosterAdapter extends ArrayAdapter<TemplateInfo> {
    private static final String TAG = "DesignTemplateAdapter";
    String catName;
    Context context;
    int height;

    public DesignPosterAdapter(Context context, List<TemplateInfo> list, String str, int i) {
        super(context, 0, list);
        this.context = context;
        this.catName = str;
        this.height = i;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.grid_itemthumb, (ViewGroup) null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        TemplateInfo item = getItem(i);
        if (this.catName.equals("MY_TEMP")) {
            viewHolder.imgDeletePoster.setVisibility(View.VISIBLE);
            try {
                if (item.getTHUMB_URI().toString().contains("thumb")) {
                    Glide.with(this.context).load(new File(item.getTHUMB_URI()).getAbsoluteFile()).thumbnail(0.1f).apply((BaseRequestOptions<?>) new RequestOptions().dontAnimate().placeholder(R.drawable.no_image).error(R.drawable.no_image)).into(viewHolder.mThumbnail);
                } else if (item.getTHUMB_URI().toString().contains("raw")) {
                    Glide.with(this.context).load(getBitmapDataObject(Uri.parse(item.getTHUMB_URI()).getPath()).imageByteArray).thumbnail(0.1f).apply((BaseRequestOptions<?>) new RequestOptions().dontAnimate().placeholder(R.drawable.no_image).error(R.drawable.no_image)).into(viewHolder.mThumbnail);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                viewHolder.mThumbnail.setImageBitmap(BitmapFactory.decodeResource(this.context.getResources(), R.drawable.no_image));
            }
        } else {
            Glide.with(this.context).load(Integer.valueOf(this.context.getResources().getIdentifier(item.getTHUMB_URI(), "drawable", this.context.getPackageName()))).thumbnail(0.1f).apply((BaseRequestOptions<?>) new RequestOptions().dontAnimate().placeholder(R.drawable.no_image).error(R.drawable.no_image)).into(viewHolder.mThumbnail);
        }
        viewHolder.imgDeletePoster.setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.DesignPosterAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
//                DesignPosterAdapter.this.showOptionsDialog(i);
            }
        });
        return view;
    }

/*    *//* JADX INFO: Access modifiers changed from: private *//*
    public void showOptionsDialog(final int i) {
        final Dialog dialog = new Dialog(this.context, R.style.ThemeWithCorners);
        dialog.setContentView(R.layout.delete_popup);
        dialog.setCancelable(false);
        TextView textView = (TextView) dialog.findViewById(R.id.txtTitle);
        TextView textView2 = (TextView) dialog.findViewById(R.id.txtDescription);
        ((Button) dialog.findViewById(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.DesignPosterAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TemplateInfo item = DesignPosterAdapter.this.getItem(i);
                DatabaseHandler dbHandler = DatabaseHandler.getDbHandler(DesignPosterAdapter.this.context);
                boolean deleteTemplateInfo = dbHandler.deleteTemplateInfo(item.getTEMPLATE_ID());
                dbHandler.close();
                if (deleteTemplateInfo) {
                    DesignPosterAdapter.this.deleteFile(Uri.parse(item.getTHUMB_URI()));
                    DesignPosterAdapter.this.remove(item);
                    DesignPosterAdapter.this.notifyDataSetChanged();
                    dialog.dismiss();
                    return;
                }
                Toast.makeText(DesignPosterAdapter.this.context, DesignPosterAdapter.this.context.getResources().getString(R.string.del_error_toast), 0).show();
            }
        });
        ((Button) dialog.findViewById(R.id.btnCancel)).setOnClickListener(new View.OnClickListener() { // from class: com.chettapps.invitationmaker.photomaker.adapter.DesignPosterAdapter.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }*/

    public void deleteRecursive(File file) {
        if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                deleteRecursive(file2);
            }
        }
        file.delete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean deleteFile(Uri uri) {
        boolean z = false;
        try {
            File file = new File(uri.getPath());
            z = file.delete();
            if (file.exists()) {
                try {
                    z = file.getCanonicalFile().delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (file.exists()) {
                    z = this.context.getApplicationContext().deleteFile(file.getName());
                }
            }
            Context context = this.context;
            Context context2 = getContext();
            context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", FileProvider.getUriForFile(context2, getContext().getApplicationContext().getPackageName() + ".provider", file)));
        } catch (Exception e2) {
            Log.e(TAG, "deleteFile: " + e2);
        }
        return z;
    }

    private BitmapDataObject getBitmapDataObject(String str) {
        try {
            return (BitmapDataObject) new ObjectInputStream(new FileInputStream(new File(str))).readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        } catch (ClassNotFoundException e3) {
            e3.printStackTrace();
            return null;
        }
    }


    class ViewHolder {
        ImageView imgDeletePoster;
        ImageView mThumbnail;

        public ViewHolder(View view) {
            this.mThumbnail = (ImageView) view.findViewById(R.id.image);
            this.imgDeletePoster = (ImageView) view.findViewById(R.id.imgDeletePoster);
        }
    }
}
