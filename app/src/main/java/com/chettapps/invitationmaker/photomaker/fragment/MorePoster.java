package com.chettapps.invitationmaker.photomaker.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.activity.PaginationListener;
import com.chettapps.invitationmaker.photomaker.adapterMaster.SeeMorePosterListAdapter;
import com.chettapps.invitationmaker.photomaker.pojoClass.PosterThumbFull;
import com.chettapps.invitationmaker.photomaker.utils.PreferenceClass;
import com.chettapps.invitationmaker.photomaker.view.GridSpacingItemDecoration;
import com.qintong.library.InsLoadingView;

import java.util.ArrayList;


public class MorePoster extends Fragment {
    private static final int PAGE_START = 1;
    private static final int PROGESS_VIEW = 0;
    private static final int VIEW_ITEM = 1;
    String catName;
    int cat_id;
    private InsLoadingView loading_view;
    public GridLayoutManager mLayoutManager;
    ArrayList<PosterThumbFull> posterThumbFulls;
    public PreferenceClass preferenceClass;
    String ratio;
    RecyclerView recyclerView;
    SeeMorePosterListAdapter seeMorePosterListAdapter;
    private int cnt = 0;
    private int currentPage = 1;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    int itemCount = 0;
    ArrayList<Object> snapDataThumb = new ArrayList<>();
    int totalPage = 0;

    static int access$108(MorePoster morePoster) {
        int i = morePoster.currentPage;
        morePoster.currentPage = i + 1;
        return i;
    }

    public static MorePoster newInstance(ArrayList<PosterThumbFull> arrayList, int i, String str, String str2) {
        MorePoster morePoster = new MorePoster();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data", arrayList);
        bundle.putInt("cat_id", i);
        bundle.putString("cateName", str);
        bundle.putString("ratio", str2);
        morePoster.setArguments(bundle);
        return morePoster;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.main_poster_fragment, viewGroup, false);
        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.rv_list);
        this.loading_view = (InsLoadingView) inflate.findViewById(R.id.loading_view);
        this.posterThumbFulls = getArguments().getParcelableArrayList("data");
        this.cat_id = getArguments().getInt("cat_id");
        this.catName = getArguments().getString("cateName");
        this.ratio = getArguments().getString("ratio");
        ArrayList<Object> arrayList = new ArrayList<>();
        this.snapDataThumb = arrayList;
        arrayList.addAll(this.posterThumbFulls);
        this.preferenceClass = new PreferenceClass(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        this.mLayoutManager = gridLayoutManager;
        this.recyclerView.setLayoutManager(gridLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 5, true));
        loadData();
        return inflate;
    }

    private void loadData() {
        getData();
    }

    private void getData() {
        RecyclerView recyclerView;
        if (this.snapDataThumb != null && (recyclerView = this.recyclerView) != null) {
            if (this.seeMorePosterListAdapter == null) {
                recyclerView.addOnScrollListener(new PaginationListener(this.mLayoutManager) { // from class: com.invitationmaker.lss.photomaker.fragment.MorePoster.1
                    @Override // com.invitationmaker.lss.photomaker.activity.PaginationListener
                    protected void loadMoreItems() {
                        MorePoster.this.isLoading = true;
                        MorePoster.access$108(MorePoster.this);
                        MorePoster.this.getPagingData();
                    }

                    @Override // com.invitationmaker.lss.photomaker.activity.PaginationListener
                    public boolean isLastPage() {
                        return MorePoster.this.isLastPage;
                    }

                    @Override // com.invitationmaker.lss.photomaker.activity.PaginationListener
                    public boolean isLoading() {
                        return MorePoster.this.isLoading;
                    }
                });
            }
            if (this.snapDataThumb.size() > 0) {
                SeeMorePosterListAdapter seeMorePosterListAdapter = new SeeMorePosterListAdapter(this.cat_id, new ArrayList(), this.ratio, getActivity(), this.recyclerView);
                this.seeMorePosterListAdapter = seeMorePosterListAdapter;
                this.recyclerView.setAdapter(seeMorePosterListAdapter);
                getPagingData();
                this.mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.invitationmaker.lss.photomaker.fragment.MorePoster.2
                    @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                    public int getSpanSize(int i) {
                        return MorePoster.this.seeMorePosterListAdapter.getItemViewType(i) != 1 ? 2 : 1;
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getPagingData() {
        final ArrayList arrayList = new ArrayList();
        double size = this.snapDataThumb.size();
        Double.isNaN(size);
        Double.isNaN(size);
        this.totalPage = Round(size / 10.0d);
        new Handler().postDelayed(new Runnable() { // from class: com.invitationmaker.lss.photomaker.fragment.MorePoster.3
            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < 10; i++) {
                    if (MorePoster.this.itemCount < MorePoster.this.snapDataThumb.size()) {
                        arrayList.add(MorePoster.this.snapDataThumb.get(MorePoster.this.itemCount));
                        MorePoster.this.itemCount++;
                    }
                }
                if (MorePoster.this.currentPage != 1) {
                    MorePoster.this.seeMorePosterListAdapter.removeLoadingView();
                }
                MorePoster.this.seeMorePosterListAdapter.addData(arrayList);
                MorePoster.this.loading_view.setVisibility(8);
                if (MorePoster.this.currentPage < MorePoster.this.totalPage) {
                    MorePoster.this.seeMorePosterListAdapter.addLoadingView();
                } else {
                    MorePoster.this.isLastPage = true;
                }
                MorePoster.this.isLoading = false;
            }
        }, 1500L);
    }

    public int Round(double d) {
        int i = (Math.abs(d - Math.floor(d)) > 0.1d ? 1 : (Math.abs(d - Math.floor(d)) == 0.1d ? 0 : -1));
        int i2 = (int) d;
        return i > 0 ? i2 + 1 : i2;
    }
}
