package com.zpan.webviewuploadfiledemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 处理图片工具类
 */
public class ZpImageUtils {

    /**
     * 压缩图片到指定宽高及大小
     */
    public static File compressImage(Context context, String filePath, int destWidth, int destHeight, int imageSize) {
        int degree = getBitmapDegree(filePath);

        Bitmap bitmap = decodeSampledBitmapFromFile(filePath, destWidth, destHeight);
        if (degree != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        String destFilePath = getImageCheDir(context) + System.nanoTime() + ".jpg";
        return saveImage(destFilePath, bitmap, imageSize);
    }

    private static String getImageCheDir(Context ctx) {
        String filePath = getCacheDir(ctx);
        return filePath + File.separator + "img" + File.separator;
    }

    private static String getCacheDir(Context context) {
        File extCache = getExternalCacheDir(context);
        boolean isSdcardOk = Environment.getExternalStorageState() == "mounted" || !isExternalStorageRemovable();
        return isSdcardOk && null != extCache ? extCache.getPath() : context.getCacheDir().getPath();
    }

    @SuppressLint({"NewApi"})
    public static boolean isExternalStorageRemovable() {
        return Build.VERSION.SDK_INT >= 9 ? Environment.isExternalStorageRemovable() : true;
    }

    @SuppressLint({"NewApi"})
    private static File getExternalCacheDir(Context context) {
        if (hasExternalCacheDir()) {
            return context.getExternalCacheDir();
        } else {
            String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
            return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
        }
    }

    private static boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= 8;
    }

    /**
     * 保存图片到指定大小
     *
     * @param destFile  最终图片路径
     * @param bitmap    原图
     * @param imageSize 输出图片大小
     */
    private static File saveImage(String destFile, Bitmap bitmap, long imageSize) {
        // 判断父目录是否存在，不存在则创建
        File result = new File(destFile.substring(0, destFile.lastIndexOf("/")));
        if (!result.exists() && !result.mkdirs()) {
            return null;
        }

        if (imageSize <= 0) {
            imageSize = 400;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        int options = 100;
        if (bos.toByteArray().length / 1024 > 1024) {
            bos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            options = 50;
        }

        while ((long) bos.toByteArray().length / 1024 > imageSize * 1.1D && options > 20) {
            bos.reset();
            options -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, bos);
        }

        try {
            FileOutputStream e = new FileOutputStream(destFile);
            e.write(bos.toByteArray());
            e.flush();
            e.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            bitmap.recycle();
            bitmap = null;
        }
        return new File(destFile);
    }

    public static int getBitmapDegree(String path) {
        short degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt("Orientation", 1);
            switch (orientation) {
                case 3:
                    degree = 180;
                    break;
                case 6:
                    degree = 90;
                    break;
                case 8:
                    degree = 270;
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }
        return degree;
    }

    private static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round((float) height / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

}
