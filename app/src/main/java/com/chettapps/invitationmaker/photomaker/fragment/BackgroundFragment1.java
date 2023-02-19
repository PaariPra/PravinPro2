package com.chettapps.invitationmaker.photomaker.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.core.view.PointerIconCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.adapter.BackgroundAdapter;
import com.chettapps.invitationmaker.photomaker.adapter.RecyclerViewLoadMoreScroll;
import com.chettapps.invitationmaker.photomaker.listener.OnLoadMoreListener;
import com.chettapps.invitationmaker.photomaker.main.GetSnapListenerS;
import com.chettapps.invitationmaker.photomaker.main.OnClickCallback;
import com.chettapps.invitationmaker.photomaker.pojoClass.BackgroundImage;
import com.chettapps.invitationmaker.photomaker.pojoClass.YourDataProvider;
import com.chettapps.invitationmaker.photomaker.utils.PreferenceClass;
import com.chettapps.invitationmaker.photomaker.view.GridSpacingItemDecoration;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public class BackgroundFragment1 extends Fragment {
    private static final String TAG = "BackgroundFragment1";
    public static final int VIEW_TYPE_ITEM = 0;
    public static final int VIEW_TYPE_LOADING = 1;
    public static int[] backgroundData;
    private PreferenceClass appPreference;
    BackgroundAdapter backgroundAdapter;
    private int category;
    private String categoryName;
    ArrayList<BackgroundImage> category_list;
    private GridLayoutManager gridLayoutManager;
    private ProgressBar loading_view;
    GetSnapListenerS onGetSnap;
    private int orientation;
    RecyclerView recyclerView;
    RelativeLayout rlAd;
    float screenHeight;
    float screenWidth;
    private RecyclerViewLoadMoreScroll scrollListener;
    int size;
    YourDataProvider yourDataProvider;

    public static BackgroundFragment1 newInstance(ArrayList<BackgroundImage> arrayList) {
        BackgroundFragment1 backgroundFragment1 = new BackgroundFragment1();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data", arrayList);
        backgroundFragment1.setArguments(bundle);
        return backgroundFragment1;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.main_fragment, viewGroup, false);
        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.overlay_artwork);
        this.onGetSnap = (GetSnapListenerS) getActivity();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;
        this.appPreference = new PreferenceClass(getActivity());
        this.category_list = getArguments().getParcelableArrayList("data");
        this.loading_view = (ProgressBar) inflate.findViewById(R.id.loading_view);
        this.rlAd = (RelativeLayout) inflate.findViewById(R.id.rl_ad);
        setCategory();
        return inflate;
    }

    private void setCategory() {
        YourDataProvider yourDataProvider = new YourDataProvider();
        this.yourDataProvider = yourDataProvider;
        yourDataProvider.setBackgroundList(this.category_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        this.gridLayoutManager = gridLayoutManager;
        this.recyclerView.setLayoutManager(gridLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 40, true));
        this.backgroundAdapter = new BackgroundAdapter(getActivity(), this.yourDataProvider.getLoadMoreItems());
        this.loading_view.setVisibility(8);
        this.recyclerView.setAdapter(this.backgroundAdapter);
        this.backgroundAdapter.setItemClickCallback(new OnClickCallback<ArrayList<String>, Integer, String, Activity, String>() { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment1.1
            @Override // com.chettapps.invitationmaker.photomaker.main.OnClickCallback
            public void onClickCallBack(ArrayList<String> arrayList, ArrayList<BackgroundImage> arrayList2, String str, Activity activity, String str2) {
                BackgroundFragment1.this.onGetSnap.onSnapFilter(0, PointerIconCompat.TYPE_CONTEXT_MENU, str, "");
            }
        });
        this.gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment1.2
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i) {
                int itemViewType = BackgroundFragment1.this.backgroundAdapter.getItemViewType(i);
                if (itemViewType != 0) {
                    return itemViewType != 1 ? -1 : 3;
                }
                return 1;
            }
        });
        RecyclerViewLoadMoreScroll recyclerViewLoadMoreScroll = new RecyclerViewLoadMoreScroll(this.gridLayoutManager);
        this.scrollListener = recyclerViewLoadMoreScroll;
        recyclerViewLoadMoreScroll.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment1.3
            @Override // com.chettapps.invitationmaker.photomaker.listener.OnLoadMoreListener
            public void onLoadMore() {
                BackgroundFragment1.this.LoadMoreData();
            }
        });
        this.recyclerView.addOnScrollListener(this.scrollListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void LoadMoreData() {
        this.backgroundAdapter.addLoadingView();
        new Handler().postDelayed(new Runnable() { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment1.4
            @Override // java.lang.Runnable
            public void run() {
                BackgroundFragment1.this.backgroundAdapter.removeLoadingView();
                BackgroundFragment1.this.backgroundAdapter.addData(BackgroundFragment1.this.yourDataProvider.getLoadMoreItemsS());
                BackgroundFragment1.this.backgroundAdapter.notifyDataSetChanged();
                BackgroundFragment1.this.scrollListener.setLoaded();
            }
        }, 3000L);
    }
}
