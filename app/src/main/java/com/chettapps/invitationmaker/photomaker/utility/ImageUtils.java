package com.chettapps.invitationmaker.photomaker.utility;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Log;

import com.chettapps.invitationmaker.photomaker.utils.CustomTypefaceSpan;
import com.chettapps.invitationmaker.photomaker.utils.ExifUtils;

import java.io.IOException;

/* loaded from: classes2.dex */
public class ImageUtils {
    public static int[] getResizeDim(float f, float f2, int i, int i2) {
        float f3 = i;
        float f4 = i2;
        float f5 = f / f2;
        float f6 = f2 / f;
        if (f > f3) {
            return new int[]{(int) (f5 * f4), (int) f4};
        }
        if (f2 <= f4 && f5 <= 0.75f) {
            int i3 = (f6 > 1.5f ? 1 : (f6 == 1.5f ? 0 : -1));
        }
        return new int[]{(int) f3, (int) (f6 * f3)};
    }

    public static Bitmap getResampleImageBitmap(Uri uri, Context context) throws IOException {
        String realPathFromURI = getRealPathFromURI(uri, context);
        try {
            return resampleImage(realPathFromURI, 800);
        } catch (Exception e) {
            e.printStackTrace();
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(realPathFromURI));
        }
    }

    public static Bitmap getResampleImageBitmap(Uri uri, Context context, int i) throws IOException {
        try {
            return resampleImage(getRealPathFromURI(uri, context), i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap resampleImage(String str, int i) throws Exception {
        int exifRotation;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = getClosestResampleSize(options.outWidth, options.outHeight, i);
            Bitmap decodeFile = BitmapFactory.decodeFile(str, options2);
            if (decodeFile == null) {
                return null;
            }
            Matrix matrix = new Matrix();
            if (decodeFile.getWidth() > i || decodeFile.getHeight() > i) {
                BitmapFactory.Options resampling = getResampling(decodeFile.getWidth(), decodeFile.getHeight(), i);
                matrix.postScale(resampling.outWidth / decodeFile.getWidth(), resampling.outHeight / decodeFile.getHeight());
            }
            if (new Integer(Build.VERSION.SDK).intValue() > 4 && (exifRotation = ExifUtils.getExifRotation(str)) != 0) {
                matrix.postRotate(exifRotation);
            }
            return Bitmap.createBitmap(decodeFile, 0, 0, decodeFile.getWidth(), decodeFile.getHeight(), matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BitmapFactory.Options getResampling(int i, int i2, int i3) {
        float f;
        float f2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (i > i2 || i2 <= i) {
            f = i3;
            f2 = i;
        } else {
            f = i3;
            f2 = i2;
        }
        float f3 = f / f2;
        options.outWidth = (int) ((i * f3) + 0.5f);
        options.outHeight = (int) ((i2 * f3) + 0.5f);
        return options;
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

    public static BitmapFactory.Options getBitmapDims(Uri uri, Context context) {
        String realPathFromURI = getRealPathFromURI(uri, context);
        Log.i("texting", "Path " + realPathFromURI);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(realPathFromURI, options);
        return options;
    }

    public static String getRealPathFromURI(Uri uri, Context context) {
        try {
            Cursor query = context.getContentResolver().query(uri, null, null, null, null);
            if (query == null) {
                return uri.getPath();
            }
            query.moveToFirst();
            String string = query.getString(query.getColumnIndex("_data"));
            query.close();
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return uri.toString();
        }
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        float f = i;
        float f2 = i2;
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        float f3 = width / height;
        float f4 = height / width;
        if (width > f) {
            return Bitmap.createScaledBitmap(bitmap, (int) (f3 * f2), (int) f2, false);
        }
        if (height <= f2 && f3 <= 0.75f) {
            int i3 = (f4 > 1.5f ? 1 : (f4 == 1.5f ? 0 : -1));
        }
        return Bitmap.createScaledBitmap(bitmap, (int) f, (int) (f4 * f), false);
    }

    public static int dpToPx(Context context, int i) {
        context.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * i);
    }

    public static int dpToPx(Context context, float f) {
        context.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * f);
    }

    public static Bitmap mergelogo(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        float width2 = bitmap2.getWidth();
        float height2 = bitmap2.getHeight();
        float f = width2 / height2;
        float f2 = height2 / width2;
        if (width2 > width) {
            bitmap2 = Bitmap.createScaledBitmap(bitmap2, (int) width, (int) (width * f2), false);
        } else if (height2 > height) {
            bitmap2 = Bitmap.createScaledBitmap(bitmap2, (int) (f * height), (int) height, false);
        }
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = null;
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        canvas.drawBitmap(bitmap2, bitmap.getWidth() - bitmap2.getWidth(), bitmap.getHeight() - bitmap2.getHeight(), paint);
        return createBitmap;
    }

    public static Bitmap mergelogo(Bitmap bitmap, Bitmap bitmap2, float f) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = null;
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap2, bitmap.getWidth(), bitmap.getHeight(), true), 0.0f, 0.0f, paint);
        return createBitmap;
    }

    public static Bitmap CropBitmapTransparency(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = -1;
        int i2 = -1;
        for (int i3 = 0; i3 < bitmap.getHeight(); i3++) {
            for (int i4 = 0; i4 < bitmap.getWidth(); i4++) {
                if (((bitmap.getPixel(i4, i3) >> 24) & 255) > 0) {
                    if (i4 < width) {
                        width = i4;
                    }
                    if (i4 > i) {
                        i = i4;
                    }
                    if (i3 < height) {
                        height = i3;
                    }
                    if (i3 > i2) {
                        i2 = i3;
                    }
                }
            }
        }
        if (i < width || i2 < height) {
            return null;
        }
        return Bitmap.createBitmap(bitmap, width, height, (i - width) + 1, (i2 - height) + 1);
    }

    public static Bitmap bitmapmasking(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
        paint.setXfermode(null);
        return createBitmap;
    }

    public static Bitmap getTiledBitmap(Context context, int i, int i2, int i3) {
        Rect rect = new Rect(0, 0, i2, i3);
        Paint paint = new Paint();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context.getResources(), i, options), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

    public static Bitmap getColoredBitmap(int i, int i2, int i3) {
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawColor(i);
        return createBitmap;
    }

    public static CharSequence getSpannableString(Context context, Typeface typeface, int i) {
        SpannableString spannableString = new SpannableString(context.getResources().getString(i));
        spannableString.setSpan(new CustomTypefaceSpan(typeface), 0, context.getResources().getString(i).length(), 0);
        SpannableStringBuilder append = new SpannableStringBuilder().append((CharSequence) spannableString);
        return append.subSequence(0, append.length());
    }

    public static Bitmap getThumbnail(Bitmap bitmap, int i, int i2) {
        Bitmap bitmap2;
        Bitmap copy = bitmap.copy(bitmap.getConfig(), true);
        int width = copy.getWidth();
        int height = copy.getHeight();
        if (height > width) {
            bitmap2 = cropCenterBitmap(copy, width, width);
        } else {
            bitmap2 = cropCenterBitmap(copy, height, height);
        }
        return Bitmap.createScaledBitmap(bitmap2, i, i2, true);
    }

    public static Bitmap cropCenterBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width < i && height < i2) {
            return bitmap;
        }
        int i3 = 0;
        int i4 = width > i ? (width - i) / 2 : 0;
        if (height > i2) {
            i3 = (height - i2) / 2;
        }
        if (i > width) {
            i = width;
        }
        if (i2 > height) {
            i2 = height;
        }
        return Bitmap.createBitmap(bitmap, i4, i3, i, i2);
    }

    public static Bitmap scaleCenterCrop(Bitmap bitmap, int i, int i2) {
        float f = i2;
        float width = bitmap.getWidth();
        float f2 = i;
        float height = bitmap.getHeight();
        float max = Math.max(f / width, f2 / height);
        float f3 = width * max;
        float f4 = max * height;
        float f5 = (f - f3) / 2.0f;
        float f6 = (f2 - f4) / 2.0f;
        RectF rectF = new RectF(f5, f6, f3 + f5, f4 + f6);
        Bitmap createBitmap = Bitmap.createBitmap(i2, i, bitmap.getConfig());
        new Canvas(createBitmap).drawBitmap(bitmap, (Rect) null, rectF, (Paint) null);
        return createBitmap;
    }

    private void setTextSizeForWidth(Paint paint, float f, String str) {
        paint.setTextSize(48.0f);
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        paint.setTextSize((f * 48.0f) / rect.width());
    }
}
