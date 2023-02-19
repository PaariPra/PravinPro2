package com.chettapps.invitationmaker.photomaker.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.util.Pair;

import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.textWork.AutofitTextRel;
import com.chettapps.invitationmaker.photomaker.utility.ImageUtils;
import com.chettapps.invitationmaker.photomaker.view.ResizeTextView;
import com.chettapps.invitationmaker.photomaker.view.StickerView;
import com.woxthebox.draglistview.DragItemAdapter;
import java.util.ArrayList;


public class ItemControlAdapter extends DragItemAdapter<Pair<Long, View>, ItemControlAdapter.ViewHolder> {
    Activity activity;
    private boolean mDragOnLongPress;
    private int mGrabHandleId;
    private int mLayoutId;

    public ItemControlAdapter(Activity activity, ArrayList<Pair<Long, View>> arrayList, int i, int i2, boolean z) {
        this.mLayoutId = i;
        this.mGrabHandleId = i2;
        this.activity = activity;
        this.mDragOnLongPress = z;
        setItemList(arrayList);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(this.mLayoutId, viewGroup, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        super.onBindViewHolder( viewHolder, i);
        View view = (View) ((Pair) this.mItemList.get(i)).second;
        try {
            if (view instanceof StickerView) {
                View childAt = ((StickerView) view).getChildAt(1);
                Bitmap createBitmap = Bitmap.createBitmap(childAt.getWidth(), childAt.getHeight(), Bitmap.Config.ARGB_8888);
                childAt.draw(new Canvas(createBitmap));
                float[] fArr = new float[9];
                ((ImageView) childAt).getImageMatrix().getValues(fArr);
                float f = fArr[0];
                float f2 = fArr[4];
                Drawable drawable = ((ImageView) childAt).getDrawable();
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                int round = Math.round(intrinsicWidth * f);
                int round2 = Math.round(intrinsicHeight * f2);
                viewHolder.mImage.setImageBitmap(Bitmap.createBitmap(createBitmap, (createBitmap.getWidth() - round) / 2, (createBitmap.getHeight() - round2) / 2, round, round2));
                viewHolder.mImage.setRotationY(childAt.getRotationY());
                viewHolder.mImage.setTag(this.mItemList.get(i));
                viewHolder.mImage.setAlpha(1.0f);
                viewHolder.textView.setText(" ");
            }
            if (view instanceof AutofitTextRel) {
                viewHolder.textView.setText(((ResizeTextView) ((AutofitTextRel) view).getChildAt(2)).getText());
                viewHolder.textView.setTypeface(((ResizeTextView) ((AutofitTextRel) view).getChildAt(2)).getTypeface());
                viewHolder.textView.setTextColor(((ResizeTextView) ((AutofitTextRel) view).getChildAt(2)).getTextColors());
                viewHolder.textView.setGravity(17);
                viewHolder.textView.setMinTextSize(10.0f);
                if (((AutofitTextRel) view).getTextInfo().getBG_COLOR() != 0) {
                    Bitmap createBitmap2 = Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888);
                    new Canvas(createBitmap2).drawColor(((AutofitTextRel) view).getTextInfo().getBG_COLOR());
                    viewHolder.mImage.setImageBitmap(createBitmap2);
                    viewHolder.mImage.setAlpha(((AutofitTextRel) view).getTextInfo().getBG_ALPHA() / 255.0f);
                } else if (((AutofitTextRel) view).getTextInfo().getBG_DRAWABLE().equals("0")) {
                    viewHolder.mImage.setAlpha(1.0f);
                    viewHolder.mImage.setImageResource(R.drawable.trans);
                } else {
                    ImageView imageView = viewHolder.mImage;
                    Activity activity = this.activity;
                    imageView.setImageBitmap(ImageUtils.getTiledBitmap(activity, activity.getResources().getIdentifier(((AutofitTextRel) view).getTextInfo().getBG_DRAWABLE(), "drawable", this.activity.getPackageName()), 150, 150));
                    viewHolder.mImage.setAlpha(((AutofitTextRel) view).getTextInfo().getBG_ALPHA() / 255.0f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (view instanceof StickerView) {
            if (((StickerView) view).isMultiTouchEnabled) {
                viewHolder.img_lock.setImageResource(R.drawable.ic_unlock);
            } else {
                viewHolder.img_lock.setImageResource(R.drawable.ic_lock);
            }
        }
        if (view instanceof AutofitTextRel) {
            if (((AutofitTextRel) view).isMultiTouchEnabled) {
                viewHolder.img_lock.setImageResource(R.drawable.ic_unlock);
            } else {
                viewHolder.img_lock.setImageResource(R.drawable.ic_lock);
            }
        }
        viewHolder.img_lock.setOnClickListener(new View.OnClickListener() { // from class: com.invitationmaker.lss.photomaker.adapter.ItemControlAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (view2 instanceof StickerView) {
                    StickerView stickerView = (StickerView) view2;
                    if (stickerView.isMultiTouchEnabled) {
                        stickerView.isMultiTouchEnabled = stickerView.setDefaultTouchListener(false);
                        viewHolder.img_lock.setImageResource(R.drawable.ic_lock);
                    } else {
                        stickerView.isMultiTouchEnabled = stickerView.setDefaultTouchListener(true);
                        viewHolder.img_lock.setImageResource(R.drawable.ic_unlock);
                    }
                }
                if (view2 instanceof AutofitTextRel) {
                    AutofitTextRel autofitTextRel = (AutofitTextRel) view2;
                    if (autofitTextRel.isMultiTouchEnabled) {
                        autofitTextRel.isMultiTouchEnabled = autofitTextRel.setDefaultTouchListener(false);
                        viewHolder.img_lock.setImageResource(R.drawable.ic_lock);
                        return;
                    }
                    autofitTextRel.isMultiTouchEnabled = autofitTextRel.setDefaultTouchListener(true);
                    viewHolder.img_lock.setImageResource(R.drawable.ic_unlock);
                }
            }
        });
    }

    @Override // com.woxthebox.draglistview.DragItemAdapter
    public long getUniqueItemId(int i) {
        return ((Long) ((Pair) this.mItemList.get(i)).first).longValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class ViewHolder extends DragItemAdapter.ViewHolder {
        ImageView img_lock;
        ImageView mImage;
        TextView mText;
        ResizeTextView textView;

        @Override // com.woxthebox.draglistview.DragItemAdapter.ViewHolder
        public void onItemClicked(View view) {
        }

        @Override // com.woxthebox.draglistview.DragItemAdapter.ViewHolder
        public boolean onItemLongClicked(View view) {
            return true;
        }

        ViewHolder(View view) {
            super(view, ItemControlAdapter.this.mGrabHandleId, ItemControlAdapter.this.mDragOnLongPress);
//            this.mText = (TextView) view.findViewById(R.id.text);
            this.mImage = (ImageView) view.findViewById(R.id.image1);
            this.img_lock = (ImageView) view.findViewById(R.id.img_lock);
            this.textView = (ResizeTextView) view.findViewById(R.id.auto_fit_edit_text);
        }
    }
}
