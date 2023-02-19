package com.chettapps.invitationmaker.photomaker.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.InvitationApplication;

import com.chettapps.invitationmaker.photomaker.adapter.BackgroundAdapter;
import com.chettapps.invitationmaker.photomaker.adapter.RecyclerViewLoadMoreScroll;
import com.chettapps.invitationmaker.photomaker.listener.GetSnapListenerData;
import com.chettapps.invitationmaker.photomaker.listener.OnLoadMoreListener;
import com.chettapps.invitationmaker.photomaker.main.OnClickCallback;
import com.chettapps.invitationmaker.photomaker.pojoClass.BackgroundImage;
import com.chettapps.invitationmaker.photomaker.pojoClass.YourDataProvider;
import com.chettapps.invitationmaker.photomaker.utils.PreferenceClass;
import com.chettapps.invitationmaker.photomaker.view.GridSpacingItemDecoration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class BackgroundFragment2 extends Fragment {
    private static final String TAG = "BackgroundFragment2";
    public static int[] backgroundData;
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_LOADING = 1;
    private PreferenceClass appPreference;
    BackgroundAdapter backgroundAdapter;
    private int category;
    private String categoryName;
    ArrayList<BackgroundImage> category_list;
    private GridLayoutManager gridLayoutManager;
    private ProgressBar loading_view;
    GetSnapListenerData onGetSnap;
    private int orientation;
    RecyclerView recyclerView;
    RelativeLayout rlAd;
    float screenHeight;
    float screenWidth;
    private RecyclerViewLoadMoreScroll scrollListener;
    int size;
    YourDataProvider yourDataProvider;

    public static BackgroundFragment2 newInstance(ArrayList<BackgroundImage> arrayList) {
        BackgroundFragment2 backgroundFragment2 = new BackgroundFragment2();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data", arrayList);
        backgroundFragment2.setArguments(bundle);
        return backgroundFragment2;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.main_fragment, viewGroup, false);
        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.overlay_artwork);
        this.loading_view = (ProgressBar) inflate.findViewById(R.id.loading_view);
        this.onGetSnap = (GetSnapListenerData) getActivity();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;
        this.appPreference = new PreferenceClass(getActivity());
        this.category_list = getArguments().getParcelableArrayList("data");
        this.rlAd = (RelativeLayout) inflate.findViewById(R.id.rl_ad);
        setCategory();
        return inflate;
    }

    public File getCacheFolder(Context context) {
        File file;
        if (Environment.getExternalStorageState().equals("mounted")) {
            file = new File(Environment.getExternalStorageDirectory(), "cachefolder");
            if (!file.isDirectory()) {
                file.mkdirs();
            }
        } else {
            file = null;
        }
        return !file.isDirectory() ? context.getCacheDir() : file;
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
        this.loading_view.setVisibility(View.GONE);
        this.recyclerView.setAdapter(this.backgroundAdapter);
        this.backgroundAdapter.setItemClickCallback(new OnClickCallback<ArrayList<String>, Integer, String, Activity, String>() { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment2.1
            @Override // com.chettapps.invitationmaker.photomaker.main.OnClickCallback
            public void onClickCallBack(ArrayList<String> arrayList, ArrayList<BackgroundImage> arrayList2, String str, Activity activity, String str2) {
                Log.e("data", "==" + str);
                final ProgressDialog progressDialog = new ProgressDialog(BackgroundFragment2.this.getContext());
                progressDialog.setMessage("Plase wait");
                progressDialog.setCancelable(false);
                progressDialog.show();
                BackgroundFragment2 backgroundFragment2 = BackgroundFragment2.this;
                final File cacheFolder = backgroundFragment2.getCacheFolder(backgroundFragment2.getContext());
                InvitationApplication.getInstance().addToRequestQueue(new ImageRequest(str, new Response.Listener<Bitmap>() { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment2.1.1
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
                                    BackgroundFragment2.this.onGetSnap.onSnapFilter(0, 104, file.getAbsolutePath(), "");
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            } catch (FileNotFoundException e2) {
                                e2.printStackTrace();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                    }
                }, 0, 0, null, new Response.ErrorListener() { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment2.1.2
                    @Override // com.android.volley.Response.ErrorListener
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                    }
                }));
            }
        });
        this.gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment2.2
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i) {
                int itemViewType = BackgroundFragment2.this.backgroundAdapter.getItemViewType(i);
                if (itemViewType != 0) {
                    return itemViewType != 1 ? -1 : 3;
                }
                return 1;
            }
        });
        RecyclerViewLoadMoreScroll recyclerViewLoadMoreScroll = new RecyclerViewLoadMoreScroll(this.gridLayoutManager);
        this.scrollListener = recyclerViewLoadMoreScroll;
        recyclerViewLoadMoreScroll.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment2.3
            @Override // com.chettapps.invitationmaker.photomaker.listener.OnLoadMoreListener
            public void onLoadMore() {
                BackgroundFragment2.this.LoadMoreData();
            }
        });
        this.recyclerView.addOnScrollListener(this.scrollListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void LoadMoreData() {
        this.backgroundAdapter.addLoadingView();
        new Handler().postDelayed(new Runnable() { // from class: com.chettapps.invitationmaker.photomaker.fragment.BackgroundFragment2.4
            @Override // java.lang.Runnable
            public void run() {
                BackgroundFragment2.this.backgroundAdapter.removeLoadingView();
                BackgroundFragment2.this.backgroundAdapter.addData(BackgroundFragment2.this.yourDataProvider.getLoadMoreItemsS());
                BackgroundFragment2.this.backgroundAdapter.notifyDataSetChanged();
                BackgroundFragment2.this.scrollListener.setLoaded();
            }
        }, 3000L);
    }
}
