package com.chettapps.invitationmaker.photomaker.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chettapps.invitationmaker.R;

import com.chettapps.invitationmaker.photomaker.adapter.ItemControlAdapter;
import com.chettapps.invitationmaker.photomaker.network.NetworkConnectivityReceiver;
import com.woxthebox.draglistview.DragItem;
import com.woxthebox.draglistview.DragListView;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public class ListFragment extends Fragment {
    public static View HintView;
    public static RelativeLayout lay_Notext;
    private DragListView mDragListView;
    private ArrayList<View> arrView = new ArrayList<>();
    private ArrayList<Pair<Long, View>> mItemArray = new ArrayList<>();

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.list_layout, viewGroup, false);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.swipe_refresh_layout);
        NetworkConnectivityReceiver.isConnected();
        DragListView dragListView = (DragListView) inflate.findViewById(R.id.drag_list_view);
        this.mDragListView = dragListView;
        dragListView.getRecyclerView().setVerticalScrollBarEnabled(true);
        this.mDragListView.setDragListListener(new DragListView.DragListListenerAdapter() { // from class: com.chettapps.invitationmaker.photomaker.main.ListFragment.1
            @Override // com.woxthebox.draglistview.DragListView.DragListListenerAdapter, com.woxthebox.draglistview.DragListView.DragListListener
            public void onItemDragStarted(int i) {
            }

            @Override // com.woxthebox.draglistview.DragListView.DragListListenerAdapter, com.woxthebox.draglistview.DragListView.DragListListener
            public void onItemDragEnded(int i, int i2) {
                if (i != i2) {
                    for (int size = ListFragment.this.mItemArray.size() - 1; size >= 0; size--) {
                        ((View) ((Pair) ListFragment.this.mItemArray.get(size)).second).bringToFront();
                    }
                    CreateCardActivity.txtStkrRel.requestLayout();
                    CreateCardActivity.txtStkrRel.postInvalidate();
                }
            }
        });
        ((TextView) inflate.findViewById(R.id.txt_Nolayers)).setTypeface(AllConstants.getTextTypeface(getActivity()));
        lay_Notext = (RelativeLayout) inflate.findViewById(R.id.lay_text);
        HintView = inflate.findViewById(R.id.HintView);
        ((RelativeLayout) inflate.findViewById(R.id.lay_frame)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (CreateCardActivity.layContainer.getVisibility() == View.VISIBLE)
                {
                    CreateCardActivity.layContainer.animate().translationX(-CreateCardActivity.layContainer.getRight()).setDuration(200L).setInterpolator(new AccelerateInterpolator()).start();
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override // java.lang.Runnable
                        public void run() {
                            CreateCardActivity.layContainer.setVisibility(View.GONE);
                            CreateCardActivity.btnLayControls.setVisibility(View.VISIBLE);
                        }
                    }, 200L);
                }
            }
        });
        return inflate;
    }

    public void getLayoutChild() {
        this.arrView.clear();
        this.mItemArray.clear();
        if (CreateCardActivity.txtStkrRel.getChildCount() != 0) {
            lay_Notext.setVisibility(8);
            for (int childCount = CreateCardActivity.txtStkrRel.getChildCount() - 1; childCount >= 0; childCount--) {
                this.mItemArray.add(new Pair<>(Long.valueOf(childCount), CreateCardActivity.txtStkrRel.getChildAt(childCount)));
                this.arrView.add(CreateCardActivity.txtStkrRel.getChildAt(childCount));
            }
        } else {
            lay_Notext.setVisibility(0);
        }
        setupListRecyclerView();
    }

    private void setupListRecyclerView() {
        this.mDragListView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mDragListView.setAdapter(new ItemControlAdapter(getActivity(), this.mItemArray, R.layout.list_item, R.id.touch_rel, false), true);
        this.mDragListView.setCanDragHorizontally(false);
    }

    /* loaded from: classes2.dex */
    private static class MyDragItem extends DragItem {
        MyDragItem(Context context, int i) {
            super(context, i);
        }

        @Override // com.woxthebox.draglistview.DragItem
        public void onBindDragView(View view, View view2) {
            Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            view.draw(new Canvas(createBitmap));
            ((ImageView) view2.findViewById(R.id.backimg)).setImageBitmap(createBitmap);
        }
    }
}
