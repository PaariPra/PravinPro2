package com.chettapps.invitationmaker.photomaker.utility;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/* loaded from: classes2.dex */
public class GlideImageLoaderPoster {
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    public GlideImageLoaderPoster(ImageView imageView, ProgressBar progressBar) {
        this.mImageView = imageView;
        this.mProgressBar = progressBar;
    }

    public void load(final String str, RequestOptions requestOptions) {
        if (str != null && requestOptions != null) {
            onConnecting();
            ProgressAppGlideModule.expect(str, new ProgressAppGlideModule.UIonProgressListener() { // from class: com.chettapps.invitationmaker.photomaker.utility.GlideImageLoaderPoster.1
                @Override // com.chettapps.invitationmaker.photomaker.utility.ProgressAppGlideModule.UIonProgressListener
                public float getGranualityPercentage() {
                    return 1.0f;
                }

                @Override // com.chettapps.invitationmaker.photomaker.utility.ProgressAppGlideModule.UIonProgressListener
                public void onProgress(long j, long j2) {
                    if (GlideImageLoaderPoster.this.mProgressBar != null) {
                        GlideImageLoaderPoster.this.mProgressBar.setProgress((int) ((j * 100) / j2));
                    }
                }
            });
            GlideApp.with(this.mImageView.getContext()).load(str).transition(DrawableTransitionOptions.withCrossFade()).thumbnail(0.01f).apply((BaseRequestOptions<?>) requestOptions.skipMemoryCache(true)).listener(new RequestListener<Drawable>() { // from class: com.chettapps.invitationmaker.photomaker.utility.GlideImageLoaderPoster.2
                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    ProgressAppGlideModule.forget(str);
                    GlideImageLoaderPoster.this.onFinished();
                    return false;
                }

                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    ProgressAppGlideModule.forget(str);
                    GlideImageLoaderPoster.this.onFinished();
                    return false;
                }
            }).into(this.mImageView);
        }
    }

    private void onConnecting() {
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            progressBar.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFinished() {
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null && this.mImageView != null) {
            progressBar.setVisibility(8);
            this.mImageView.setVisibility(0);
        }
    }
}
