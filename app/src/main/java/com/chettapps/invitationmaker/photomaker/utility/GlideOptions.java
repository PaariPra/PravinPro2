package com.chettapps.invitationmaker.photomaker.utility;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;

/* loaded from: classes2.dex */
public final class GlideOptions extends RequestOptions implements Cloneable {
    private static GlideOptions centerCropTransform2;
    private static GlideOptions centerInsideTransform1;
    private static GlideOptions circleCropTransform3;
    private static GlideOptions fitCenterTransform0;
    private static GlideOptions noAnimation5;
    private static GlideOptions noTransformation4;

    public static GlideOptions sizeMultiplierOf(float f) {
        return (GlideOptions) new GlideOptions().sizeMultiplier(f);
    }

    public static GlideOptions diskCacheStrategyOf(DiskCacheStrategy diskCacheStrategy) {
        return (GlideOptions) new GlideOptions().diskCacheStrategy(diskCacheStrategy);
    }

    public static GlideOptions priorityOf(Priority priority) {
        return (GlideOptions) new GlideOptions().priority(priority);
    }

    public static GlideOptions placeholderOf(Drawable drawable) {
        return (GlideOptions) new GlideOptions().placeholder(drawable);
    }

    public static GlideOptions placeholderOf(int i) {
        return (GlideOptions) new GlideOptions().placeholder(i);
    }

    public static GlideOptions errorOf(Drawable drawable) {
        return (GlideOptions) new GlideOptions().error(drawable);
    }

    public static GlideOptions errorOf(int i) {
        return (GlideOptions) new GlideOptions().error(i);
    }

    public static GlideOptions skipMemoryCacheOf(boolean z) {
        return (GlideOptions) new GlideOptions().skipMemoryCache(z);
    }

    public static GlideOptions overrideOf(int i, int i2) {
        return (GlideOptions) new GlideOptions().override(i, i2);
    }

    public static GlideOptions overrideOf(int i) {
        return (GlideOptions) new GlideOptions().override(i);
    }

    public static GlideOptions signatureOf(Key key) {
        return (GlideOptions) new GlideOptions().signature(key);
    }

    public static GlideOptions fitCenterTransform() {
        if (fitCenterTransform0 == null) {
            fitCenterTransform0 = (GlideOptions) new GlideOptions().fitCenter().autoClone();
        }
        return fitCenterTransform0;
    }

    public static GlideOptions centerInsideTransform() {
        if (centerInsideTransform1 == null) {
            centerInsideTransform1 = (GlideOptions) new GlideOptions().centerInside().autoClone();
        }
        return centerInsideTransform1;
    }

    public static GlideOptions centerCropTransform() {
        if (centerCropTransform2 == null) {
            centerCropTransform2 = (GlideOptions) new GlideOptions().centerCrop().autoClone();
        }
        return centerCropTransform2;
    }

    public static GlideOptions circleCropTransform() {
        if (circleCropTransform3 == null) {
            circleCropTransform3 = (GlideOptions) new GlideOptions().circleCrop().autoClone();
        }
        return circleCropTransform3;
    }

    public static RequestOptions bitmapTransform(Transformation<Bitmap> transformation) {
        return new GlideOptions().transform2(transformation);
    }

    public static GlideOptions noTransformation() {
        if (noTransformation4 == null) {
            noTransformation4 = (GlideOptions) new GlideOptions().dontTransform().autoClone();
        }
        return noTransformation4;
    }

    public static <T> GlideOptions option(Option<T> option, T t) {
        return (GlideOptions) new GlideOptions().set2((Option<Option<T>>) option, (Option<T>) t);
    }

    public static GlideOptions decodeTypeOf(Class<?> cls) {
        return (GlideOptions) new GlideOptions().decode2(cls);
    }

    public static GlideOptions formatOf(DecodeFormat decodeFormat) {
        return (GlideOptions) new GlideOptions().format(decodeFormat);
    }

    public static GlideOptions frameOf(long j) {
        return (GlideOptions) new GlideOptions().frame(j);
    }

    public static GlideOptions downsampleOf(DownsampleStrategy downsampleStrategy) {
        return (GlideOptions) new GlideOptions().downsample(downsampleStrategy);
    }

    public static GlideOptions timeoutOf(int i) {
        return (GlideOptions) new GlideOptions().timeout(i);
    }

    public static GlideOptions encodeQualityOf(int i) {
        return (GlideOptions) new GlideOptions().encodeQuality(i);
    }

    public static GlideOptions encodeFormatOf(Bitmap.CompressFormat compressFormat) {
        return (GlideOptions) new GlideOptions().encodeFormat(compressFormat);
    }

    public static GlideOptions noAnimation() {
        if (noAnimation5 == null) {
            noAnimation5 = (GlideOptions) new GlideOptions().dontAnimate().autoClone();
        }
        return noAnimation5;
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions sizeMultiplier(float f) {
        return (GlideOptions) super.sizeMultiplier(f);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions useUnlimitedSourceGeneratorsPool(boolean z) {
        return (GlideOptions) super.useUnlimitedSourceGeneratorsPool(z);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions useAnimationPool(boolean z) {
        return (GlideOptions) super.useAnimationPool(z);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions onlyRetrieveFromCache(boolean z) {
        return (GlideOptions) super.onlyRetrieveFromCache(z);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
        return (GlideOptions) super.diskCacheStrategy(diskCacheStrategy);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions priority(Priority priority) {
        return (GlideOptions) super.priority(priority);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions placeholder(Drawable drawable) {
        return (GlideOptions) super.placeholder(drawable);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions placeholder(int i) {
        return (GlideOptions) super.placeholder(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions fallback(Drawable drawable) {
        return (GlideOptions) super.fallback(drawable);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions fallback(int i) {
        return (GlideOptions) super.fallback(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions error(Drawable drawable) {
        return (GlideOptions) super.error(drawable);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions error(int i) {
        return (GlideOptions) super.error(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions theme(Resources.Theme theme) {
        return (GlideOptions) super.theme(theme);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions skipMemoryCache(boolean z) {
        return (GlideOptions) super.skipMemoryCache(z);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions override(int i, int i2) {
        return (GlideOptions) super.override(i, i2);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions override(int i) {
        return (GlideOptions) super.override(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions signature(Key key) {
        return (GlideOptions) super.signature(key);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions clone() {
        return (GlideOptions) super.clone();
    }

    public <Y> RequestOptions set2(Option<Y> option, Y y) {
        return (GlideOptions) super.set((Option<Option<Y>>) option, (Option<Y>) y);
    }


    public RequestOptions decode2(Class<?> cls) {
        return (GlideOptions) super.decode(cls);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions encodeFormat(Bitmap.CompressFormat compressFormat) {
        return (GlideOptions) super.encodeFormat(compressFormat);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions encodeQuality(int i) {
        return (GlideOptions) super.encodeQuality(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions frame(long j) {
        return (GlideOptions) super.frame(j);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions format(DecodeFormat decodeFormat) {
        return (GlideOptions) super.format(decodeFormat);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions disallowHardwareConfig() {
        return (GlideOptions) super.disallowHardwareConfig();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions downsample(DownsampleStrategy downsampleStrategy) {
        return (GlideOptions) super.downsample(downsampleStrategy);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions timeout(int i) {
        return (GlideOptions) super.timeout(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions optionalCenterCrop() {
        return (GlideOptions) super.optionalCenterCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions centerCrop() {
        return (GlideOptions) super.centerCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions optionalFitCenter() {
        return (GlideOptions) super.optionalFitCenter();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions fitCenter() {
        return (GlideOptions) super.fitCenter();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions optionalCenterInside() {
        return (GlideOptions) super.optionalCenterInside();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions centerInside() {
        return (GlideOptions) super.centerInside();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions optionalCircleCrop() {
        return (GlideOptions) super.optionalCircleCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions circleCrop() {
        return (GlideOptions) super.circleCrop();
    }


    public RequestOptions transform2(Transformation<Bitmap> transformation) {
        return (GlideOptions) super.transform(transformation);
    }


    /* renamed from: transform  reason: avoid collision after fix types in other method */
    public final RequestOptions transform2(Transformation<Bitmap>... transformationArr) {
        return (GlideOptions) super.transform(transformationArr);
    }


    @SafeVarargs
    @Deprecated
    /* renamed from: transforms  reason: avoid collision after fix types in other method */
    public final RequestOptions transforms2(Transformation<Bitmap>... transformationArr) {
        return (GlideOptions) super.transforms(transformationArr);
    }

    /* renamed from: optionalTransform  reason: avoid collision after fix types in other method */
    public RequestOptions optionalTransform2(Transformation<Bitmap> transformation) {
        return (GlideOptions) super.optionalTransform(transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public <Y> RequestOptions optionalTransform(Class<Y> cls, Transformation<Y> transformation) {
        return (GlideOptions) super.optionalTransform((Class) cls, (Transformation) transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public <Y> RequestOptions transform(Class<Y> cls, Transformation<Y> transformation) {
        return (GlideOptions) super.transform((Class) cls, (Transformation) transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions dontTransform() {
        return (GlideOptions) super.dontTransform();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions dontAnimate() {
        return (GlideOptions) super.dontAnimate();
    }


    public RequestOptions apply2(BaseRequestOptions<?> baseRequestOptions) {
        return (GlideOptions) super.apply(baseRequestOptions);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions lock() {
        return (GlideOptions) super.lock();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    public RequestOptions autoClone() {
        return (GlideOptions) super.autoClone();
    }
}
