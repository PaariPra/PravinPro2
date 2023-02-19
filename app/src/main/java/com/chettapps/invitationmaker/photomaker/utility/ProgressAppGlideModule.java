package com.chettapps.invitationmaker.photomaker.utility;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/* loaded from: classes2.dex */
public class ProgressAppGlideModule extends AppGlideModule {

    /* loaded from: classes2.dex */
    private interface ResponseProgressListener {
        void update(HttpUrl httpUrl, long j, long j2);
    }

    /* loaded from: classes2.dex */
    public interface UIonProgressListener {
        float getGranualityPercentage();

        void onProgress(long j, long j2);
    }

    @Override // com.bumptech.glide.module.AppGlideModule
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override // com.bumptech.glide.module.LibraryGlideModule, com.bumptech.glide.module.RegistersComponents
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() { // from class: com.chettapps.invitationmaker.photomaker.utility.ProgressAppGlideModule.1
            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request();
                Response proceed = chain.proceed(request);
                return proceed.newBuilder().body(new OkHttpProgressResponseBody(request.url(), proceed.body(), new DispatchingProgressListener())).build();
            }
        }).build()));
    }

    public static void forget(String str) {
        DispatchingProgressListener.forget(str);
    }

    public static void expect(String str, UIonProgressListener uIonProgressListener) {
        DispatchingProgressListener.expect(str, uIonProgressListener);
    }

    /* loaded from: classes2.dex */
    private static class DispatchingProgressListener implements ResponseProgressListener {
        private static final Map<String, UIonProgressListener> LISTENERS = new HashMap();
        private static final Map<String, Long> PROGRESSES = new HashMap();
        private final Handler handler = new Handler(Looper.getMainLooper());

        DispatchingProgressListener() {
        }

        static void forget(String str) {
            LISTENERS.remove(str);
            PROGRESSES.remove(str);
        }

        static void expect(String str, UIonProgressListener uIonProgressListener) {
            LISTENERS.put(str, uIonProgressListener);
        }

        @Override // com.chettapps.invitationmaker.photomaker.utility.ProgressAppGlideModule.ResponseProgressListener
        public void update(HttpUrl httpUrl, final long j, final long j2) {
            String httpUrl2 = httpUrl.toString();
            final UIonProgressListener uIonProgressListener = LISTENERS.get(httpUrl2);
            if (uIonProgressListener != null) {
                if (j2 <= j) {
                    forget(httpUrl2);
                }
                if (needsDispatch(httpUrl2, j, j2, uIonProgressListener.getGranualityPercentage())) {
                    this.handler.post(new Runnable() { // from class: com.chettapps.invitationmaker.photomaker.utility.ProgressAppGlideModule.DispatchingProgressListener.1
                        @Override // java.lang.Runnable
                        public void run() {
                            uIonProgressListener.onProgress(j, j2);
                        }
                    });
                }
            }
        }

        private boolean needsDispatch(String str, long j, long j2, float f) {
            if (f == 0.0f || j == 0 || j2 == j) {
                return true;
            }
            long j3 = (long) (((((float) j) * 100.0f) / ((float) j2)) / f);
            Map<String, Long> map = PROGRESSES;
            Long l = map.get(str);
            if (l != null && j3 == l.longValue()) {
                return false;
            }
            map.put(str, Long.valueOf(j3));
            return true;
        }
    }

    /* loaded from: classes2.dex */
    private static class OkHttpProgressResponseBody extends ResponseBody {
        private BufferedSource bufferedSource;
        private final ResponseProgressListener progressListener;
        private final ResponseBody responseBody;
        private final HttpUrl url;

        OkHttpProgressResponseBody(HttpUrl httpUrl, ResponseBody responseBody, ResponseProgressListener responseProgressListener) {
            this.url = httpUrl;
            this.responseBody = responseBody;
            this.progressListener = responseProgressListener;
        }

        @Override // okhttp3.ResponseBody
        public MediaType contentType() {
            return this.responseBody.contentType();
        }

        @Override // okhttp3.ResponseBody
        public long contentLength() {
            return this.responseBody.contentLength();
        }

        @Override // okhttp3.ResponseBody
        public BufferedSource source() {
            if (this.bufferedSource == null) {
                this.bufferedSource = Okio.buffer(source(this.responseBody.source()));
            }
            return this.bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) { // from class: com.chettapps.invitationmaker.photomaker.utility.ProgressAppGlideModule.OkHttpProgressResponseBody.1
                long totalBytesRead = 0;

                @Override // okio.ForwardingSource, okio.Source
                public long read(Buffer buffer, long j) throws IOException {
                    long read = super.read(buffer, j);
                    long contentLength = OkHttpProgressResponseBody.this.responseBody.contentLength();
                    if (read == -1) {
                        this.totalBytesRead = contentLength;
                    } else {
                        this.totalBytesRead += read;
                    }
                    OkHttpProgressResponseBody.this.progressListener.update(OkHttpProgressResponseBody.this.url, this.totalBytesRead, contentLength);
                    return read;
                }
            };
        }
    }
}
