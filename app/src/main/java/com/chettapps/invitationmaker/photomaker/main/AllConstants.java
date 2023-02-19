package com.chettapps.invitationmaker.photomaker.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.core.view.ViewCompat;

import com.chettapps.invitationmaker.R;
import com.chettapps.invitationmaker.photomaker.InvitationApplication;

import com.chettapps.invitationmaker.photomaker.utility.ImageUtils;
import com.chettapps.invitationmaker.photomaker.utils.ExifUtils;
import com.loopj.android.http.AsyncHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class AllConstants {


    public static String BASE_URL = "";
    public static String BASE_URL_BG = null;
    public static String BASE_URL_POSTER = null;
    public static String BASE_URL_POSTER_BG = null;
    public static String BASE_URL_STICKER = null;
    static int DesignerScreenHeight = 1519;
    static int DesignerScreenWidth = 1080;


    public static int Priority = 3;
    public static int aspectRatioHeight = 1;
    public static int aspectRatioWidth = 1;
    public static String bgURL = "";
    public static Bitmap bitmap = null;
    public static int currentScreenHeight = 1;
    public static int currentScreenWidth = 1;
    public static String fURL = "";
    public static String sdcardPath;
    public static int[] imageId = {R.drawable.btxt0, R.drawable.btxt1, R.drawable.btxt2, R.drawable.btxt3, R.drawable.btxt4, R.drawable.btxt5, R.drawable.btxt6, R.drawable.btxt7, R.drawable.btxt8, R.drawable.btxt9, R.drawable.btxt10, R.drawable.btxt11, R.drawable.btxt12, R.drawable.btxt13, R.drawable.btxt14, R.drawable.btxt15, R.drawable.btxt16, R.drawable.btxt17, R.drawable.btxt18, R.drawable.btxt19, R.drawable.btxt20, R.drawable.btxt21, R.drawable.btxt22, R.drawable.btxt23, R.drawable.btxt24, R.drawable.btxt25, R.drawable.btxt26, R.drawable.btxt27, R.drawable.btxt28, R.drawable.btxt29, R.drawable.btxt30, R.drawable.btxt31, R.drawable.btxt32, R.drawable.btxt33, R.drawable.btxt34, R.drawable.btxt35, R.drawable.btxt36, R.drawable.btxt37, R.drawable.btxt38, R.drawable.btxt39};
    public static String isRated = "isRated";
    public static String jsonData = "jsonData";
    static int multiplier = AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT;
    public static String onTimeHint = "onTimeHint";
    public static String onTimeLayerScroll = "onTimeLayerScroll";
    public static String onTimeRecentHint = "onTimeRecentHint";
    public static String openfirtstime = "openfirtstime";
    public static int[] overlayArr = {R.drawable.os1, R.drawable.os2, R.drawable.os3, R.drawable.os4, R.drawable.os5, R.drawable.os6, R.drawable.os7, R.drawable.os8, R.drawable.os9, R.drawable.os10, R.drawable.os11, R.drawable.os12, R.drawable.os13, R.drawable.os14, R.drawable.os15, R.drawable.os16, R.drawable.os17, R.drawable.os18, R.drawable.os19, R.drawable.os20, R.drawable.os21, R.drawable.os22, R.drawable.os23, R.drawable.os24, R.drawable.os25, R.drawable.os26, R.drawable.os27, R.drawable.os28, R.drawable.os29, R.drawable.os30, R.drawable.os31, R.drawable.os32, R.drawable.os33, R.drawable.os34, R.drawable.os35, R.drawable.os36, R.drawable.os37, R.drawable.os38, R.drawable.os39, R.drawable.os40, R.drawable.os41, R.drawable.os42, R.drawable.os43, R.drawable.os44, R.drawable.os45};
    public static String stickerURL = " ";

    public static final Uri getUriToResource(Context context, String str) throws Resources.NotFoundException {
        context.getResources();
        return Uri.parse("android.resource://" + context.getPackageName() + "/drawable/" + str);
    }

    public static int getVersionInfo() {
        try {
            PackageInfo packageInfo = InvitationApplication.getInstance().getPackageManager().getPackageInfo(InvitationApplication.getInstance().getPackageName(), 0);
            String str = packageInfo.versionName;
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Bitmap merge(Bitmap bitmap2, Bitmap bitmap3, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap2.getWidth(), bitmap2.getHeight(), Bitmap.Config.ARGB_8888);
        Drawable[] drawableArr = {new BitmapDrawable(bitmap2), new BitmapDrawable(bitmap3)};
        drawableArr[1].setAlpha(i);
        LayerDrawable layerDrawable = new LayerDrawable(drawableArr);
        Canvas canvas = new Canvas(createBitmap);
        layerDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        layerDrawable.draw(canvas);
        return createBitmap;
    }

    public static CharSequence getSpannableString(Context context, Typeface typeface, int i) {
        SpannableStringBuilder append = new SpannableStringBuilder().append((CharSequence) new SpannableString(context.getResources().getString(i)));
        return append.subSequence(0, append.length());
    }

    public static Bitmap resizeBitmap(Bitmap bitmap2, int i, int i2) {
        float f = i;
        float f2 = i2;
        float width = bitmap2.getWidth();
        float height = bitmap2.getHeight();
        Log.i("testings", f + "  " + f2 + "  and  " + width + "  " + height);
        float f3 = width / height;
        float f4 = height / width;
        if (width > f) {
            float f5 = f4 * f;
            Log.i("testings", "if (wd > wr) " + f + "  " + f5);
            if (f5 > f2) {
                float f6 = f3 * f2;
                Log.i("testings", "  if (he > hr) " + f6 + "  " + f2);
                return Bitmap.createScaledBitmap(bitmap2, (int) f6, (int) f2, false);
            }
            Log.i("testings", " in else " + f + "  " + f5);
            return Bitmap.createScaledBitmap(bitmap2, (int) f, (int) f5, false);
        }
        if (height > f2) {
            float f7 = f3 * f2;
            Log.i("testings", "  if (he > hr) " + f7 + "  " + f2);
            if (f7 > f) {
                f2 = f * f4;
            } else {
                Log.i("testings", " in else " + f7 + "  " + f2);
                f = f7;
            }
        } else if (f3 > 0.75f) {
            f2 = f * f4;
            Log.i("testings", " if (rat1 > .75f) ");
        } else if (f4 > 1.5f) {
            f = f2 * f3;
            Log.i("testings", " if (rat2 > 1.5f) ");
        } else {
            float f8 = f4 * f;
            Log.i("testings", " in else ");
            if (f8 > f2) {
                f = f2 * f3;
                Log.i("testings", "  if (he > hr) " + f + "  " + f2);
            } else {
                Log.i("testings", " in else " + f + "  " + f8);
            }
        }
        return Bitmap.createScaledBitmap(bitmap2, (int) f, (int) f2, false);
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri, float f, float f2) throws IOException {
        int exifRotation;
        try {
            ParcelFileDescriptor openFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = openFileDescriptor.getFileDescriptor();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            if (f <= f2) {
                f = f2;
            }
            int i = (int) f;
            options2.inSampleSize = ImageUtils.getClosestResampleSize(options.outWidth, options.outHeight, i);
            Bitmap decodeFileDescriptor = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options2);
            Matrix matrix = new Matrix();
            if (decodeFileDescriptor.getWidth() > i || decodeFileDescriptor.getHeight() > i) {
                BitmapFactory.Options resampling = ImageUtils.getResampling(decodeFileDescriptor.getWidth(), decodeFileDescriptor.getHeight(), i);
                matrix.postScale(resampling.outWidth / decodeFileDescriptor.getWidth(), resampling.outHeight / decodeFileDescriptor.getHeight());
            }
            String realPathFromURI = ImageUtils.getRealPathFromURI(uri, context);
            if (new Integer(Build.VERSION.SDK).intValue() > 4 && (exifRotation = ExifUtils.getExifRotation(realPathFromURI)) != 0) {
                matrix.postRotate(exifRotation);
            }
            Bitmap createBitmap = Bitmap.createBitmap(decodeFileDescriptor, 0, 0, decodeFileDescriptor.getWidth(), decodeFileDescriptor.getHeight(), matrix, true);
            openFileDescriptor.close();
            return createBitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getClosestResampleSize(int i, int i2, int i3) {
        int max = Math.max(i, i2);
        int i4 = 1;
        while (true) {
            if (i4 >= Integer.MAX_VALUE) {
                break;
            } else if (i4 * i3 > max) {
                i4--;
                break;
            } else {
                i4++;
            }
        }
        if (i4 > 0) {
            return i4;
        }
        return 1;
    }

    public static Typeface getHeaderTypeface(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "font/Montserrat-SemiBold.ttf");
    }

    public static Typeface getTextTypeface(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "font/Montserrat-Medium.ttf");
    }

    public static Typeface getTextTypefaceFont(Activity activity, String str) {
        return Typeface.createFromAsset(activity.getAssets(), str);
    }

    public static Animation getAnimTopToBootom(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.top_to_bottom_view);
    }

    public static Animation getAnimUp(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.slide_up_view);
    }

    public static Animation getAnimDown(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.slide_down_view);
    }

    public static Animation getAnimUpDown(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.left_right_slide_view);
    }

    public static Animation getAnimDownUp(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.right_left_slide_view);
    }

    public static File getSaveFileLocation(String str) {
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(externalStoragePublicDirectory, ".Invitation Card Stickers/" + str);
    }

    public static boolean saveBitmapObject(Activity activity, Bitmap bitmap2, String str) {
        Bitmap copy = bitmap2.copy(bitmap2.getConfig(), true);
        File file = new File(str);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            boolean compress = copy.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            copy.recycle();
            activity.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
            return compress;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("testing", "Exception" + e.getMessage());
            return false;
        }
    }

    public static String saveBitmapObject1(Bitmap bitmap2) {
        File saveFileLocation = getSaveFileLocation("category1");
        saveFileLocation.mkdirs();
        File file = new File(saveFileLocation, "raw1-" + System.currentTimeMillis() + ".png");
        String absolutePath = file.getAbsolutePath();
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap2.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            return absolutePath;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("testing", "Exception" + e.getMessage());
            return "";
        }
    }

    public static String saveBitmapObject(Activity activity, Bitmap bitmap2) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Invitation Card Stickers/Mydesigns");
        file.mkdirs();
        File file2 = new File(file, "thumb-" + System.currentTimeMillis() + ".png");
        if (file2.exists()) {
            file2.delete();
        }
        try {
            bitmap2.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file2));
            return file2.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("MAINACTIVITY", "Exception" + e.getMessage());

            return null;
        }
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap guidelines_bitmap(Activity activity, int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColor(-1);
        paint.setStrokeWidth(ImageUtils.dpToPx((Context) activity, 2));
        paint.setPathEffect(new DashPathEffect(new float[]{5.0f, 5.0f}, 1.0f));
        paint.setStyle(Paint.Style.STROKE);
        Paint paint2 = new Paint();
        paint2.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint2.setStrokeWidth(ImageUtils.dpToPx((Context) activity, 2));
        paint2.setPathEffect(new DashPathEffect(new float[]{5.0f, 5.0f}, 1.0f));
        paint2.setStyle(Paint.Style.STROKE);
        int i3 = i / 4;
        float f = i3;
        float f2 = i2;
        canvas.drawLine(f, 0.0f, f, f2, paint);
        int i4 = i / 2;
        float f3 = i4;
        canvas.drawLine(f3, 0.0f, f3, f2, paint);
        int i5 = (i * 3) / 4;
        float f4 = i5;
        canvas.drawLine(f4, 0.0f, f4, f2, paint);
        int i6 = i2 / 4;
        float f5 = i6;
        float f6 = i;
        canvas.drawLine(0.0f, f5, f6, f5, paint);
        int i7 = i2 / 2;
        float f7 = i7;
        canvas.drawLine(0.0f, f7, f6, f7, paint);
        int i8 = (i2 * 3) / 4;
        float f8 = i8;
        canvas.drawLine(0.0f, f8, f6, f8, paint);
        float f9 = i3 + 2;
        canvas.drawLine(f9, 0.0f, f9, f2, paint2);
        float f10 = i4 + 2;
        canvas.drawLine(f10, 0.0f, f10, f2, paint2);
        float f11 = i5 + 2;
        canvas.drawLine(f11, 0.0f, f11, f2, paint2);
        float f12 = i6 + 2;
        canvas.drawLine(0.0f, f12, f6, f12, paint2);
        float f13 = i7 + 2;
        canvas.drawLine(0.0f, f13, f6, f13, paint2);
        float f14 = i8 + 2;
        canvas.drawLine(0.0f, f14, f6, f14, paint2);
        return createBitmap;
    }

    public static Bitmap getTiledBitmap(Activity activity, int i, Bitmap bitmap2, SeekBar seekBar) {
        Rect rect = new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight());
        Paint paint = new Paint();
        int progress = CreateCardActivity.seekTailys.getProgress() + 10;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        paint.setShader(new BitmapShader(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), i, options), progress, progress, true), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(bitmap2.getWidth(), bitmap2.getHeight(), Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

    public static Bitmap getTiledBitmap(Context context, int i, int i2, int i3) {
        Rect rect = new Rect(0, 0, i2, i3);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context.getResources(), i, new BitmapFactory.Options()), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

    public static float[] getOptimumSize(int i, int i2, int i3, int i4) {
        float f = i3;
        float f2 = i4;
        float f3 = i;
        float f4 = i2;
        Log.i("testings", f + "  " + f2 + "  and  " + f3 + "  " + f4);
        float f5 = f3 / f4;
        float f6 = f4 / f3;
        if (f3 > f) {
            float f7 = f6 * f;
            Log.i("testings", "if (wd > wr) " + f + "  " + f7);
            if (f7 > f2) {
                float f8 = f5 * f2;
                Log.i("testings", "  if (he > hr) " + f8 + "  " + f2);
                return new float[]{f8, f2};
            }
            Log.i("testings", " in else " + f + "  " + f7);
            return new float[]{f, f7};
        }
        if (f4 > f2) {
            float f9 = f5 * f2;
            Log.i("testings", "  if (he > hr) " + f9 + "  " + f2);
            if (f9 > f) {
                f2 = f * f6;
            } else {
                Log.i("testings", " in else " + f9 + "  " + f2);
                f = f9;
            }
        } else if (f5 > 0.75f) {
            f2 = f * f6;
            Log.i("testings", " if (rat1 > .75f) ");
        } else if (f6 > 1.5f) {
            f = f2 * f5;
            Log.i("testings", " if (rat2 > 1.5f) ");
        } else {
            float f10 = f6 * f;
            Log.i("testings", " in else ");
            if (f10 > f2) {
                f = f2 * f5;
                Log.i("testings", "  if (he > hr) " + f + "  " + f2);
            } else {
                Log.i("testings", " in else " + f + "  " + f10);
            }
        }
        return new float[]{f, f2};
    }

    public static float getNewX(float f) {
        int i = aspectRatioWidth;
        int i2 = multiplier;
        float[] optimumSize = getOptimumSize(i * i2, aspectRatioHeight * i2, DesignerScreenWidth, DesignerScreenHeight);
        int i3 = aspectRatioWidth;
        int i4 = multiplier;
        float[] optimumSize2 = getOptimumSize(i3 * i4, aspectRatioHeight * i4, currentScreenWidth, currentScreenHeight);
        float f2 = optimumSize2[1];
        float f3 = optimumSize[1];
        return (optimumSize2[0] / optimumSize[0]) * f;
    }

    public static String getMargin(String str) {
        String[] split = str.split(",");
        int parseInt = Integer.parseInt(split[0]);
        int parseInt2 = Integer.parseInt(split[1]);
        int i = aspectRatioWidth;
        int i2 = multiplier;
        float[] optimumSize = getOptimumSize(i * i2, aspectRatioHeight * i2, DesignerScreenWidth, DesignerScreenHeight);
        int i3 = aspectRatioWidth;
        int i4 = multiplier;
        float[] optimumSize2 = getOptimumSize(i3 * i4, aspectRatioHeight * i4, currentScreenWidth, currentScreenHeight);
        return String.valueOf((int) (parseInt * (optimumSize2[0] / optimumSize[0]))) + "," + String.valueOf((int) (parseInt2 * (optimumSize2[1] / optimumSize[1])));
    }

    public static float getNewY(float f) {
        int i = aspectRatioWidth;
        int i2 = multiplier;
        float[] optimumSize = getOptimumSize(i * i2, aspectRatioHeight * i2, DesignerScreenWidth, DesignerScreenHeight);
        int i3 = aspectRatioWidth;
        int i4 = multiplier;
        float[] optimumSize2 = getOptimumSize(i3 * i4, aspectRatioHeight * i4, currentScreenWidth, currentScreenHeight);
        float f2 = optimumSize2[0];
        float f3 = optimumSize[0];
        return (optimumSize2[1] / optimumSize[1]) * f;
    }

    public static int getNewWidth(float f) {
        int i = aspectRatioWidth;
        int i2 = multiplier;
        float[] optimumSize = getOptimumSize(i * i2, aspectRatioHeight * i2, DesignerScreenWidth, DesignerScreenHeight);
        int i3 = aspectRatioWidth;
        int i4 = multiplier;
        float[] optimumSize2 = getOptimumSize(i3 * i4, aspectRatioHeight * i4, currentScreenWidth, currentScreenHeight);
        float f2 = optimumSize2[0];
        float f3 = optimumSize[0];
        return (int) ((optimumSize2[1] / optimumSize[1]) * f);
    }

    public static int getNewHeight(float f) {
        int i = aspectRatioWidth;
        int i2 = multiplier;
        float[] optimumSize = getOptimumSize(i * i2, aspectRatioHeight * i2, DesignerScreenWidth, DesignerScreenHeight);
        int i3 = aspectRatioWidth;
        int i4 = multiplier;
        float[] optimumSize2 = getOptimumSize(i3 * i4, aspectRatioHeight * i4, currentScreenWidth, currentScreenHeight);
        float f2 = optimumSize2[0];
        float f3 = optimumSize[0];
        return (int) ((optimumSize2[1] / optimumSize[1]) * f);
    }

    public static float getNewSize(Context context, float f) {
        return (context.getResources().getDisplayMetrics().density / 3.0f) * f;
    }
}
