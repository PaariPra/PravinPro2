package com.chettapps.invitationmaker.photomaker.adapter;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    GestureDetector mGestureDetector;
    private OnItemClickListener mListener;

    /* loaded from: classes2.dex */
    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
    }

    public RecyclerItemClickListener(Context context, OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
        this.mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { // from class: com.invitationmaker.lss.photomaker.adapter.RecyclerItemClickListener.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return true;
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        View findChildViewUnder = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if (!(findChildViewUnder == null || this.mListener == null || !this.mGestureDetector.onTouchEvent(motionEvent))) {
            this.mListener.onItemClick(findChildViewUnder, recyclerView.getChildPosition(findChildViewUnder));
        }
        return false;
    }
}
