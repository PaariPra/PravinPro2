package com.chettapps.invitationmaker.photomaker.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.adapter.RecyclerViewLoadMoreScroll;
import com.chettapps.invitationmaker.photomaker.adapter.StickerAdapter;
import com.chettapps.invitationmaker.photomaker.listener.GetSnapListenerData;
import com.chettapps.invitationmaker.photomaker.listener.OnLoadMoreListener;
import com.chettapps.invitationmaker.photomaker.main.OnClickCallback;
import com.chettapps.invitationmaker.photomaker.pojoClass.BackgroundImage;
import com.chettapps.invitationmaker.photomaker.pojoClass.YourDataProvider;
import com.chettapps.invitationmaker.photomaker.utils.PreferenceClass;
import com.chettapps.invitationmaker.photomaker.view.GridSpacingItemDecoration;

import java.util.ArrayList;


public class StickerFragmentMore extends Fragment {
    private static final String TAG = "StickerFragmentMore";
    private PreferenceClass appPreference;
    ArrayList<BackgroundImage> category_list;
    String color;
    private ProgressBar loading_view;
    private GridLayoutManager mLayoutManager;
    private int numColumns;
    GetSnapListenerData onGetSnap;
    RecyclerView recyclerView;
    RelativeLayout rlAd;
    float screenHeight;
    float screenWidth;
    private RecyclerViewLoadMoreScroll scrollListener;
    StickerAdapter stickerAdapter;
    YourDataProvider yourDataProvider;
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_LOADING = 1;
    private final ViewTreeObserver.OnGlobalLayoutListener pageLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.chettapps.invitationmaker.photomaker.fragment.StickerFragmentMore.1
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            StickerFragmentMore stickerFragmentMore = StickerFragmentMore.this;
            stickerFragmentMore.setNumColumns(stickerFragmentMore.recyclerView.getWidth() / StickerFragmentMore.this.recyclerView.getContext().getResources().getDimensionPixelSize(R.dimen.logo_image_size));
        }
    };

    public static StickerFragmentMore newInstance(ArrayList<BackgroundImage> arrayList, String str) {
        StickerFragmentMore stickerFragmentMore = new StickerFragmentMore();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data", arrayList);
        bundle.putString("color", str);
        stickerFragmentMore.setArguments(bundle);
        return stickerFragmentMore;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.main_fragment, viewGroup, false);
        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.overlay_artwork);
        this.onGetSnap = (GetSnapListenerData) getActivity();
        this.rlAd = (RelativeLayout) inflate.findViewById(R.id.rl_ad);
        this.loading_view = (ProgressBar) inflate.findViewById(R.id.loading_view);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;
        this.appPreference = new PreferenceClass(getActivity());
        this.category_list = getArguments().getParcelableArrayList("data");
        this.color = getArguments().getString("color");
        setCategory();
        return inflate;
    }

    private void setCategory() {
        YourDataProvider yourDataProvider = new YourDataProvider();
        this.yourDataProvider = yourDataProvider;
        yourDataProvider.setStickerList(this.category_list);
        this.stickerAdapter = new StickerAdapter(getActivity(), this.yourDataProvider.getLoadMoreStickerItems(), getResources().getDimensionPixelSize(R.dimen.logo_image_size), getResources().getDimensionPixelSize(R.dimen.image_padding), this.color);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        this.mLayoutManager = gridLayoutManager;
        this.recyclerView.setLayoutManager(gridLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 40, true));
        this.loading_view.setVisibility(View.GONE);
        this.recyclerView.setAdapter(this.stickerAdapter);
        this.stickerAdapter.setItemClickCallback(new OnClickCallback<ArrayList<String>, Integer, String, Activity, String>() { // from class: com.chettapps.invitationmaker.photomaker.fragment.StickerFragmentMore.2
            @Override // com.chettapps.invitationmaker.photomaker.main.OnClickCallback
            public void onClickCallBack(ArrayList<String> arrayList, ArrayList<BackgroundImage> arrayList2, String str, Activity activity, String str2) {
                StickerFragmentMore.this.onGetSnap.onSnapFilter(0, 34, str, str2);
            }
        });
        this.mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.chettapps.invitationmaker.photomaker.fragment.StickerFragmentMore.3
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i) {
                int itemViewType = StickerFragmentMore.this.stickerAdapter.getItemViewType(i);
                if (itemViewType != 0) {
                    return itemViewType != 1 ? -1 : 3;
                }
                return 1;
            }
        });
        RecyclerViewLoadMoreScroll recyclerViewLoadMoreScroll = new RecyclerViewLoadMoreScroll(this.mLayoutManager);
        this.scrollListener = recyclerViewLoadMoreScroll;
        recyclerViewLoadMoreScroll.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.chettapps.invitationmaker.photomaker.fragment.StickerFragmentMore.4
            @Override // com.chettapps.invitationmaker.photomaker.listener.OnLoadMoreListener
            public void onLoadMore() {
                StickerFragmentMore.this.LoadMoreData();
            }
        });
        this.recyclerView.addOnScrollListener(this.scrollListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void LoadMoreData() {
        this.stickerAdapter.addLoadingView();
        new Handler().postDelayed(new Runnable() { // from class: com.chettapps.invitationmaker.photomaker.fragment.StickerFragmentMore.5
            @Override // java.lang.Runnable
            public void run() {
                StickerFragmentMore.this.stickerAdapter.removeLoadingView();
                StickerFragmentMore.this.stickerAdapter.addData(StickerFragmentMore.this.yourDataProvider.getLoadMoreStickerItemsS());
                StickerFragmentMore.this.stickerAdapter.notifyDataSetChanged();
                StickerFragmentMore.this.scrollListener.setLoaded();
            }
        }, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNumColumns(int i) {
        if (this.numColumns != i) {
            this.mLayoutManager.setSpanCount(i);
            this.numColumns = i;
            StickerAdapter stickerAdapter = this.stickerAdapter;
            if (stickerAdapter != null) {
                stickerAdapter.notifyDataSetChanged();
            }
        }
    }
}
