package com.chettapps.invitationmaker.photomaker.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chettapps.invitationmaker.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.chettapps.invitationmaker.photomaker.InvitationApplication;
import com.chettapps.invitationmaker.photomaker.adapter.RecyclerViewLoadMoreScroll;
import com.chettapps.invitationmaker.photomaker.adapter.StickerAdapter;
import com.chettapps.invitationmaker.photomaker.adapter.VerticalStickerAdapter;
import com.chettapps.invitationmaker.photomaker.listener.GetSnapListenerData;
import com.chettapps.invitationmaker.photomaker.listener.OnLoadMoreListener;
import com.chettapps.invitationmaker.photomaker.main.AllConstants;
import com.chettapps.invitationmaker.photomaker.main.OnClickCallback;
import com.chettapps.invitationmaker.photomaker.pojoClass.BackgroundImage;
import com.chettapps.invitationmaker.photomaker.pojoClass.MainBG;
import com.chettapps.invitationmaker.photomaker.pojoClass.Snap;
import com.chettapps.invitationmaker.photomaker.pojoClass.ThumbBG;
import com.chettapps.invitationmaker.photomaker.pojoClass.YourDataProvider;
import com.chettapps.invitationmaker.photomaker.utils.PreferenceClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class StickerFragment extends Fragment {
    private static final String TAG = "StickerFragment";
    public static int[] stickerData;
    private int category;
    private String categoryName;
    private ProgressBar loading_view;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerView mRecyclerView;
    GetSnapListenerData onGetSnap;
    private int orientation;
    private PreferenceClass preferenceClass;
    RelativeLayout rlAd;
    private RecyclerViewLoadMoreScroll scrollListener;
    private VerticalStickerAdapter snapAdapter;
    StickerAdapter stickerAdapter;
    private ArrayList<MainBG> thumbnail_bg;
    private int totalAds;
    YourDataProvider yourDataProvider;
    private int cnt = 0;
    ArrayList<Object> snapData = new ArrayList<>();

    public static StickerFragment newInstance() {
        return new StickerFragment();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.mainart_fragment, viewGroup, false);
        this.preferenceClass = new PreferenceClass(getActivity());
        this.mLinearLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.overlay_artwork);
        this.mRecyclerView = recyclerView;
        recyclerView.setLayoutManager(this.mLinearLayoutManager);
        this.mRecyclerView.setHasFixedSize(true);
        this.loading_view = (ProgressBar) inflate.findViewById(R.id.loading_view);
        this.onGetSnap = (GetSnapListenerData) getActivity();
        this.rlAd = (RelativeLayout) inflate.findViewById(R.id.rl_ad);
        if (this.thumbnail_bg != null) {
            loardData();
        } else {
            getData();
        }
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loardData() {
        for (int i = 0; i < this.thumbnail_bg.size(); i++) {
            this.snapData.add(new Snap(1, this.thumbnail_bg.get(i).getCategory_name(), this.thumbnail_bg.get(i).getCategory_list()));
        }
        this.loading_view.setVisibility(View.GONE);
        YourDataProvider yourDataProvider = new YourDataProvider();
        this.yourDataProvider = yourDataProvider;
        yourDataProvider.setPosterList(this.snapData);
        if (this.snapData != null) {
            VerticalStickerAdapter verticalStickerAdapter = new VerticalStickerAdapter(getActivity(), this.yourDataProvider.getLoadMorePosterItems(), this.mRecyclerView, 0);
            this.snapAdapter = verticalStickerAdapter;
            this.mRecyclerView.setAdapter(verticalStickerAdapter);
            this.snapAdapter.setItemClickCallback(new OnClickCallback<ArrayList<String>, Integer, String, Activity, String>() { // from class: com.chettapps.invitationmaker.photomaker.fragment.StickerFragment.1
                @Override // com.chettapps.invitationmaker.photomaker.main.OnClickCallback
                public void onClickCallBack(ArrayList<String> arrayList, ArrayList<BackgroundImage> arrayList2, String str, Activity activity, String str2) {
                    if (str.equals("")) {
                        StickerFragment.this.onGetSnap.onSnapFilter(arrayList2, 0, str2);
                    } else {
                        StickerFragment.this.onGetSnap.onSnapFilter(0, 34, str, str2);
                    }
                }
            });
            RecyclerViewLoadMoreScroll recyclerViewLoadMoreScroll = new RecyclerViewLoadMoreScroll(this.mLinearLayoutManager);
            this.scrollListener = recyclerViewLoadMoreScroll;
            recyclerViewLoadMoreScroll.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.chettapps.invitationmaker.photomaker.fragment.StickerFragment.2
                @Override // com.chettapps.invitationmaker.photomaker.listener.OnLoadMoreListener
                public void onLoadMore() {
                    StickerFragment.this.LoadMoreData();
                }
            });
            this.mRecyclerView.addOnScrollListener(this.scrollListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void LoadMoreData() {
        this.snapAdapter.addLoadingView();
        new Handler().postDelayed(new Runnable() { // from class: com.chettapps.invitationmaker.photomaker.fragment.StickerFragment.3
            @Override // java.lang.Runnable
            public void run() {
                StickerFragment.this.snapAdapter.removeLoadingView();
                StickerFragment.this.snapAdapter.addData(StickerFragment.this.yourDataProvider.getLoadMorePosterItemsS());
                StickerFragment.this.snapAdapter.notifyDataSetChanged();
                StickerFragment.this.scrollListener.setLoaded();
            }
        }, 3000L);
    }

    private void getData() {
        InvitationApplication instance = InvitationApplication.getInstance();
        instance.addToRequestQueue(new StringRequest(1, AllConstants.BASE_URL_POSTER_BG + "poster/stickerlatest", new Response.Listener<String>() { // from class: com.chettapps.invitationmaker.photomaker.fragment.StickerFragment.4
            public void onResponse(String str) {
                try {
                    StickerFragment.this.thumbnail_bg = ((ThumbBG) new Gson().fromJson(str, ThumbBG.class)).getThumbnail_bg();
                    StickerFragment.this.loardData();
                } catch (JsonSyntaxException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(StickerFragment.TAG, "Error: " + volleyError.getMessage());
            }
        }) { // from class: com.chettapps.invitationmaker.photomaker.fragment.StickerFragment.6
            @Override // com.android.volley.Request
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("device", "1");
                return hashMap;
            }
        });
    }
}
