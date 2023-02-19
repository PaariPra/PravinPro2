package com.chettapps.invitationmaker.photomaker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Activity activity;
    protected LayoutInflater inflater;
    protected List<T> list = new ArrayList();

    public BaseAdapter(Activity activity) {
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public synchronized void add(T t) {
        this.list.add(t);
        sort();
        notifyItemInserted(this.list.indexOf(t));
    }

    public synchronized void clear() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public synchronized void addPosition(T t, int i) {
        this.list.add(i, t);
        sort();
        notifyItemInserted(this.list.indexOf(t));
    }

    public synchronized void removerPosition(int i) {
        this.list.get(i);
        this.list.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeRemoved(i, this.list.size());
    }

    public synchronized void updatePosition(T t, int i) {
        this.list.set(i, t);
        notifyItemChanged(i);
        notifyItemRangeChanged(i, this.list.size());
    }

    public ArrayList<T> getData() {
        return (ArrayList) this.list;
    }

    public synchronized void sort() {
    }
}
