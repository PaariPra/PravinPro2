package com.chettapps.invitationmaker.photomaker.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ImageDownloadManager {
    private static final String DOT = ".";
    private static final String FORWARD_SLASH = "/";
    private static final Object LOCK = new Object();
    private static final String LOG_TAG = "ImageDownloadManager";
    public static ArrayList<String> downloadImages;
    private static ImageDownloadManager sInstance;
    private Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());
    private HashMap<ImageDownloadTask, Callback> callbacks = new HashMap<>();
    private Context context;
    private ThreadPoolExecutor threadPoolExecutor;

    /* loaded from: classes2.dex */
    public interface Callback {
        void onFailure(ImageSaveFailureReason imageSaveFailureReason);

        void onSuccess(ImageDownloadTask imageDownloadTask, ArrayList<String> arrayList);
    }

    /* loaded from: classes2.dex */
    public interface Extensions {
        public static final String JPEG = "jpeg";
        public static final String PNG = "png";
        public static final String WEBP = "webp";
    }

    /* loaded from: classes2.dex */
    public enum ImageSaveFailureReason {
        NETWORK,
        FILE
    }

    /* loaded from: classes2.dex */
    public enum Task {
        DOWNLOAD,
        DELETE
    }

    private ImageDownloadManager(Context context) {
        if (sInstance == null) {
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            this.context = context.getApplicationContext();
            int i = availableProcessors * 2;
            this.threadPoolExecutor = new ThreadPoolExecutor(i, i, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
            return;
        }
        throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
    }

    public static ImageDownloadManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new ImageDownloadManager(context);
                }
            }
        }
        return sInstance;
    }

    public void addTask(ImageDownloadTask imageDownloadTask) {
        if (this.callbacks.containsKey(imageDownloadTask)) {
            Log.e(LOG_TAG, "Have another task to process with same Tag. Rejecting");
            return;
        }
        this.threadPoolExecutor.execute(new ImageDownloadRunnable(imageDownloadTask));
        this.callbacks.put(imageDownloadTask, imageDownloadTask.callback.get());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class ImageDownloadRunnable implements Runnable {
        ImageDownloadTask imageDownloadTask;

        ImageDownloadRunnable(ImageDownloadTask imageDownloadTask) {
            this.imageDownloadTask = imageDownloadTask;
            if (imageDownloadTask == null) {
                throw new InvalidParameterException("Task is null");
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.imageDownloadTask.task == Task.DELETE) {
                ImageDownloadManager.this.deleteFolder(new File(this.imageDownloadTask.folderPath));
                ImageDownloadManager.this.postSuccess(this.imageDownloadTask);
            } else if (this.imageDownloadTask.task == Task.DOWNLOAD) {
                downloadImages(this.imageDownloadTask);
            }
        }

        private void downloadImages(ImageDownloadTask imageDownloadTask) {
            for (String str : imageDownloadTask.urls) {
                Bitmap startDownload = ImageDownloadManager.this.startDownload(str);
                if (startDownload == null) {
                    ImageDownloadManager.this.postFailure(this.imageDownloadTask, ImageSaveFailureReason.NETWORK);
                    return;
                } else if (!ImageDownloadManager.saveBitmapImage(startDownload, this.imageDownloadTask.folderPath, str)) {
                    ImageDownloadManager.this.postFailure(imageDownloadTask, ImageSaveFailureReason.FILE);
                    return;
                }
            }
            ImageDownloadManager.this.postSuccess(imageDownloadTask);
        }

        public boolean equals(Object obj) {
            if (obj instanceof ImageDownloadRunnable) {
                return this.imageDownloadTask.equals(((ImageDownloadRunnable) obj).imageDownloadTask);
            }
            return super.equals(obj);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteFolder(File file) {
        try {
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.list().length == 0) {
                file.delete();
            } else {
                for (String str : file.list()) {
                    deleteFolder(new File(file, str));
                }
                if (file.list().length == 0) {
                    file.delete();
                }
            }
        } catch (Exception unused) {
        }
    }

    public static String getFileNameFromUrl(String str) {
        return str.substring(str.lastIndexOf(FORWARD_SLASH), str.length());
    }

    public static String getHashCodeBasedFileName(String str) {
        return str.hashCode() + DOT + getExtension(getFileNameFromUrl(str));
    }

    public static String getExtension(String str) {
        int lastIndexOf;
        return (str == null || str.isEmpty() || (lastIndexOf = str.lastIndexOf(DOT)) == -1 || lastIndexOf >= str.length()) ? "" : str.substring(lastIndexOf + 1);
    }

    public static boolean saveBitmapImage(Bitmap bitmap, String str, String str2) {
        File file = new File(str);
        if (!file.exists() && !file.mkdir()) {
            return false;
        }
        try {
            String hashCodeBasedFileName = getHashCodeBasedFileName(str2);
            String str3 = str + FORWARD_SLASH + hashCodeBasedFileName;
            FileOutputStream fileOutputStream = new FileOutputStream(str3);
            bitmap.compress(getCompressFormatFromFileName(hashCodeBasedFileName), 100, fileOutputStream);
            fileOutputStream.close();
            downloadImages.add(str3);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Bitmap.CompressFormat getCompressFormatFromFileName(String str) {
        String extension = getExtension(str);
        extension.hashCode();
        if (extension.equals(Extensions.JPEG)) {
            return Bitmap.CompressFormat.JPEG;
        }
        if (!extension.equals(Extensions.WEBP)) {
            return Bitmap.CompressFormat.PNG;
        }
        return Bitmap.CompressFormat.WEBP;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap startDownload(String str) {
        try {
            RequestCreator load = Picasso.get().load(Uri.parse(str));
            load.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
            return load.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* loaded from: classes2.dex */
    public static class ImageDownloadTask {
        public WeakReference<Callback> callback;
        public String folderPath;
        private Object tag;
        public Task task;
        public List<String> urls;

        public ImageDownloadTask(Object obj, Task task, List<String> list, String str, Callback callback) {
            this.tag = obj;
            this.task = task;
            this.urls = list;
            this.folderPath = str;
            this.callback = new WeakReference<>(callback);
            ImageDownloadManager.downloadImages = new ArrayList<>();
        }

        public boolean equals(Object obj) {
            if (obj instanceof ImageDownloadTask) {
                return this.tag.equals(((ImageDownloadTask) obj).tag);
            }
            return super.equals(obj);
        }

        public int hashCode() {
            return this.tag.hashCode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postSuccess(final ImageDownloadTask imageDownloadTask) {
        final Callback callback = imageDownloadTask.callback.get();
        if (callback != null) {
            this.MAIN_HANDLER.post(new Runnable() { // from class: com.chettapps.invitationmaker.photomaker.activity.ImageDownloadManager.1
                @Override // java.lang.Runnable
                public void run() {
                    callback.onSuccess(imageDownloadTask, ImageDownloadManager.downloadImages);
                }
            });
        }
        this.callbacks.remove(imageDownloadTask);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postFailure(ImageDownloadTask imageDownloadTask, final ImageSaveFailureReason imageSaveFailureReason) {
        final Callback callback = imageDownloadTask.callback.get();
        if (callback != null) {
            this.MAIN_HANDLER.post(new Runnable() { // from class: com.chettapps.invitationmaker.photomaker.activity.ImageDownloadManager.2
                @Override // java.lang.Runnable
                public void run() {
                    callback.onFailure(imageSaveFailureReason);
                }
            });
        }
        this.callbacks.remove(imageDownloadTask);
    }
}
