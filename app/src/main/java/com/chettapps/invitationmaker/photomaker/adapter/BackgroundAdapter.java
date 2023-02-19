package com.chettapps.invitationmaker.photomaker.adapter;



import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.activity.IntegerVersionSignature;
import com.chettapps.invitationmaker.photomaker.main.AllConstants;
import com.chettapps.invitationmaker.photomaker.main.OnClickCallback;
import com.chettapps.invitationmaker.photomaker.pojoClass.BackgroundImage;
import com.chettapps.invitationmaker.photomaker.utils.PreferenceClass;
import java.util.ArrayList;
import java.util.List;





public class BackgroundAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "BackgroundAdapter";
    private PreferenceClass appPreference;
    ArrayList<BackgroundImage> category_list;
    Activity context;
    private OnClickCallback<ArrayList<String>, Integer, String, Activity, String> mSingleCallback;
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_LOADING = 1;
    boolean isDownloadProgress = true;

    @Override
    public long getItemId(int i) {
        return i;
    }

    public BackgroundAdapter(Activity activity, ArrayList<BackgroundImage> arrayList) {
        this.context = activity;
        this.appPreference = new PreferenceClass(activity);
        this.category_list = arrayList;
    }

    public static String getFileNameFromUrl(String str) {
        return str.substring(str.lastIndexOf(47) + 1).split("\\?")[0].split("#")[0];
    }

    @Override
    public int getItemCount() {
        ArrayList<BackgroundImage> arrayList = this.category_list;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == 0) {
            ViewHolder viewHolder2 = (ViewHolder) viewHolder;
            viewHolder2.downloadProgress.setVisibility(View.GONE);
            final String str = AllConstants.BASE_URL_BG + "/" + this.category_list.get(i).getImage_url();
            viewHolder2.imgDownload.setVisibility(View.GONE);
            Glide.with(this.context).load(AllConstants.BASE_URL_BG + "/" + this.category_list.get(i).getThumb_url()).thumbnail(0.1f).
                    apply((BaseRequestOptions<?>) new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).signature(new
                            IntegerVersionSignature(AllConstants.getVersionInfo())).dontAnimate().override(200, 200).fitCenter().
                            placeholder(R.drawable.no_image).error(R.drawable.no_image)).into(viewHolder2.imageView);
            viewHolder2.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        BackgroundAdapter.this.mSingleCallback.onClickCallBack(null, BackgroundAdapter.this.category_list, str,
                                BackgroundAdapter.this.context, "");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.background_image_listrow, viewGroup, false));
        }
        return new LoadingHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progress_view, viewGroup, false));
    }

    @Override
    public int getItemViewType(int i) {
        return this.category_list.get(i) == null ? 1 : 0;
    }

    public void addData(List<BackgroundImage> list) {
        notifyDataSetChanged();
    }

    public void removeLoadingView() {
        ArrayList<BackgroundImage> arrayList = this.category_list;
        arrayList.remove(arrayList.size() - 1);
        notifyItemRemoved(this.category_list.size());
    }

    public void addLoadingView() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                BackgroundAdapter.this.category_list.add(null);
                BackgroundAdapter backgroundAdapter = BackgroundAdapter.this;
                backgroundAdapter.notifyItemInserted(backgroundAdapter.category_list.size() - 1);
            }
        });
    }

    public void DownoloadSticker(String str, String str2, String str3) {
        AndroidNetworking.download(str, str2, str3).build().startDownload(new DownloadListener() {

            @Override
            public void onDownloadComplete() {
                BackgroundAdapter.this.isDownloadProgress = true;
                BackgroundAdapter.this.notifyDataSetChanged();
                Log.e(BackgroundAdapter.TAG, "onDownloadComplete: ");
            }

            @Override
            public void onError(ANError aNError) {
                BackgroundAdapter.this.isDownloadProgress = true;
                BackgroundAdapter.this.notifyDataSetChanged();
                Log.e(BackgroundAdapter.TAG, "onError: ");
                Toast.makeText(BackgroundAdapter.this.context, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setItemClickCallback(OnClickCallback onClickCallback) {
        this.mSingleCallback = onClickCallback;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar downloadProgress;
        ImageView imageView;
        RelativeLayout imgDownload;
        LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
            this.imgDownload = (RelativeLayout) view.findViewById(R.id.imgDownload);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail_image);
            this.layout = (LinearLayout) view.findViewById(R.id.main);
            this.downloadProgress = (ProgressBar) view.findViewById(R.id.downloadProgress);
        }
    }


    public class LoadingHolder extends RecyclerView.ViewHolder {
        public LoadingHolder(View view) {
            super(view);
        }
    }
}
