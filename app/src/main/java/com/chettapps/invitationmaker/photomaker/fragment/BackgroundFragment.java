package com.chettapps.invitationmaker.photomaker.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.chettapps.invitationmaker.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.chettapps.invitationmaker.photomaker.InvitationApplication;

import com.chettapps.invitationmaker.photomaker.adapter.BackgroundAdapter;
import com.chettapps.invitationmaker.photomaker.adapter.RecyclerViewLoadMoreScroll;
import com.chettapps.invitationmaker.photomaker.adapter.VeticalViewBgAdapter;
import com.chettapps.invitationmaker.photomaker.listener.GetSnapListenerData;
import com.chettapps.invitationmaker.photomaker.listener.OnLoadMoreListener;
import com.chettapps.invitationmaker.photomaker.main.AllConstants;
import com.chettapps.invitationmaker.photomaker.main.OnClickCallback;
import com.chettapps.invitationmaker.photomaker.pojoClass.BackgroundImage;
import com.chettapps.invitationmaker.photomaker.pojoClass.MainBG;
import com.chettapps.invitationmaker.photomaker.pojoClass.Snap;
import com.chettapps.invitationmaker.photomaker.pojoClass.ThumbBG;
import com.chettapps.invitationmaker.photomaker.pojoClass.YourDataProvider;
import com.chettapps.invitationmaker.photomaker.utils.Configure;
import com.chettapps.invitationmaker.photomaker.utils.PreferenceClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class BackgroundFragment extends Fragment {
    private static final String TAG = "BackgroundFragment";
    public static int[] backgroundData;
    private PreferenceClass appPreference;
    BackgroundAdapter backgroundAdapter;
    private int category;
    private String categoryName;
    private ProgressBar loading_view;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerView mRecyclerView;
    GetSnapListenerData onGetSnap;
    private int orientation;
    private PreferenceClass preferenceClass;
    RelativeLayout rlAd;
    float screenHeight;
    float screenWidth;
    private RecyclerViewLoadMoreScroll scrollListener;
    int size;
    private ArrayList<MainBG> thumbnail_bg;
    private int totalAds;
    private VeticalViewBgAdapter veticalViewAdapter;
    YourDataProvider yourDataProvider;
    private int cnt = 0;
    ArrayList<Object> snapData = new ArrayList<>();

    public static BackgroundFragment newInstance() {
        return new BackgroundFragment();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.main_fragment, viewGroup, false);
        this.onGetSnap = (GetSnapListenerData) getActivity();
        this.rlAd = (RelativeLayout) inflate.findViewById(R.id.rl_ad);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;
        this.preferenceClass = new PreferenceClass(getActivity());
        this.mRecyclerView = (RecyclerView) inflate.findViewById(R.id.overlay_artwork);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        this.mLinearLayoutManager = linearLayoutManager;
        this.mRecyclerView.setLayoutManager(linearLayoutManager);
        this.mRecyclerView.setHasFixedSize(true);
        this.loading_view = (ProgressBar) inflate.findViewById(R.id.loading_view);
        if (this.thumbnail_bg != null) {
            lordData();
        } else {
            getData();
        }
        return inflate;
    }


    public void lordData() {
        for (int i = 0; i < this.thumbnail_bg.size(); i++) {
            this.snapData.add(new Snap(1, this.thumbnail_bg.get(i).getCategory_name(), this.thumbnail_bg.get(i).getCategory_list()));
        }
        this.loading_view.setVisibility(View.GONE);
        YourDataProvider yourDataProvider = new YourDataProvider();
        this.yourDataProvider = yourDataProvider;
        yourDataProvider.setPosterList(this.snapData);
        if (this.snapData != null) {
            VeticalViewBgAdapter veticalViewBgAdapter = new VeticalViewBgAdapter(getActivity(), this.yourDataProvider.getLoadMorePosterItems(), this.mRecyclerView, 0);
            this.veticalViewAdapter = veticalViewBgAdapter;
            this.mRecyclerView.setAdapter(veticalViewBgAdapter);
            RecyclerViewLoadMoreScroll recyclerViewLoadMoreScroll = new RecyclerViewLoadMoreScroll(this.mLinearLayoutManager);
            this.scrollListener = recyclerViewLoadMoreScroll;
            recyclerViewLoadMoreScroll.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment.1
                @Override // com.chettapps.invitationmaker.photomaker.listener.OnLoadMoreListener
                public void onLoadMore() {
                    BackgroundFragment.this.LoadMoreData();
                }
            });
            this.mRecyclerView.addOnScrollListener(this.scrollListener);
        }
        this.mRecyclerView.setAdapter(this.veticalViewAdapter);
        this.veticalViewAdapter.setItemClickCallback(new OnClickCallback<ArrayList<String>, Integer, String, Activity, String>() { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment.2
            @Override // com.chettapps.invitationmaker.photomaker.main.OnClickCallback
            public void onClickCallBack(ArrayList<String> arrayList, ArrayList<BackgroundImage> arrayList2, String str, Activity activity, String str2) {
                if (str.equals("")) {
                    BackgroundFragment.this.onGetSnap.onSnapFilter(arrayList2, 1, "");
                    return;
                }
                final ProgressDialog progressDialog = new ProgressDialog(BackgroundFragment.this.getContext());
                progressDialog.setMessage("Plase wait");
                progressDialog.setCancelable(false);
                progressDialog.show();
                BackgroundFragment backgroundFragment = BackgroundFragment.this;
                final File cacheFolder = backgroundFragment.getCacheFolder(backgroundFragment.getContext());
                InvitationApplication.getInstance().addToRequestQueue(new ImageRequest(str, new Response.Listener<Bitmap>() { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment.2.1
                    public void onResponse(Bitmap bitmap) {
                        try {
                            progressDialog.dismiss();
                            try {
                                File file = new File(cacheFolder, "localFileName.png");
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                                fileOutputStream.flush();
                                fileOutputStream.close();
                                try {
                                    BackgroundFragment.this.onGetSnap.onSnapFilter(0, 104, file.getAbsolutePath(), "");
                                } catch (Exception e) {
                                    try {
                                        e.printStackTrace();
                                    } catch (NullPointerException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                            } catch (FileNotFoundException e3) {
                                e3.printStackTrace();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        } catch (Exception e5) {
                            e5.printStackTrace();
                        }
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565, new Response.ErrorListener() { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment.2.2
                    @Override // com.android.volley.Response.ErrorListener
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                    }
                }));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void LoadMoreData() {
        this.veticalViewAdapter.addLoadingView();
        new Handler().postDelayed(new Runnable() { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment.3
            @Override // java.lang.Runnable
            public void run() {
                BackgroundFragment.this.veticalViewAdapter.removeLoadingView();
                BackgroundFragment.this.veticalViewAdapter.addData(BackgroundFragment.this.yourDataProvider.getLoadMorePosterItemsS());
                BackgroundFragment.this.veticalViewAdapter.notifyDataSetChanged();
                BackgroundFragment.this.scrollListener.setLoaded();
            }
        }, 3000L);
    }

    private void getData() {
        InvitationApplication instance = InvitationApplication.getInstance();
        instance.addToRequestQueue(new StringRequest(1, AllConstants.BASE_URL_POSTER_BG + "poster/backgroundlatest", new Response.Listener<String>() { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment.4
            public void onResponse(String str) {
                try {
                    BackgroundFragment.this.thumbnail_bg = ((ThumbBG) new Gson().fromJson(str, ThumbBG.class)).getThumbnail_bg();
                    BackgroundFragment.this.lordData();
                } catch (JsonSyntaxException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment.5
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(BackgroundFragment.TAG, "Error: " + volleyError.getMessage());
            }
        }) { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment.6
            @Override // com.android.volley.Request
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("device", "1");
                return hashMap;
            }
        });
    }

    public File getCacheFolder(Context context) {
        File GetFileDir = Configure.GetFileDir(context);
        GetFileDir.mkdir();
        return GetFileDir;
    }
}
