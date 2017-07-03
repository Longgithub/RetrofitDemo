package com.braval.retrofitdemo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 图片处理工具类。
 * Author: bianhaipeng
 * Date:   2016/8/17
 */
public class BitmapUtils {
    /**
     * 重新设置view的尺寸。
     * @param view 要重设的view。
     */
    public static void resizeBitmap(final View view) {
        if (view != null) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    int width = view.getMeasuredWidth();
                    int height = view.getMeasuredHeight();
                    if (width > 0 && height > 0) {
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
                        int size = (width < height)?width:height;
                        params.width = size;
                        params.height = size;
                        if (height > width) {
                            params.topMargin = height - width + params.topMargin;
                        }
                        view.setLayoutParams(params);
                    }
                }
            });
        }
    }
    /**
     * 压缩图片。
     * @param image 要压缩的图像。
     * @param targetSize 目标尺寸，单位kb。
     * @return 压缩后的图像。
     */
    public static byte[] compressImageToBytes(Bitmap image, int targetSize) {
        if (image == null) {
            return null;
        }
        ByteArrayOutputStream stream = getCompressedImageOutputStream(image, targetSize);
        return stream.toByteArray();
    }

    /**
     * 获取压缩后的图像输出流。
     * @param image image 要压缩的图像。
     * @param targetSize 目标尺寸，单位kb。
     * @return 输出流。
     */
    public static ByteArrayOutputStream getCompressedImageOutputStream(Bitmap image, int targetSize) {
        if (image == null) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到stream中
        int options = 100;
        while ( stream.toByteArray().length / 1024>targetSize) {  //循环判断如果压缩后图片是否大于targetSize,大于继续压缩
            stream.reset();//重置stream即清空stream
            image.compress(Bitmap.CompressFormat.JPEG, options, stream);//这里压缩options%，把压缩后的数据存放到stream中
            options -= 10;//每次都减少10
        }
        return stream;
    }

    /**
     * 压缩图片。
     * @param imagePath 图像路径。
     * @param targetSize 目标尺寸，单位kb。
     * @return 压缩后的图像。
     */
    public static Bitmap compressImage(String imagePath, int targetSize) {
        return compressImage(getScaledBitmap(imagePath, 800f, 480f), targetSize);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 压缩图片。
     * @param image 要压缩的图像。
     * @param targetSize 目标尺寸，单位kb。
     * @return 压缩后的图像。
     */
    public static Bitmap compressImage(Bitmap image, int targetSize) {
        ByteArrayOutputStream stream = getCompressedImageOutputStream(image, targetSize);
        ByteArrayInputStream isBm = new ByteArrayInputStream(stream.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);
    }

    /**
     * 压缩图片。
     * @param imagePath 图像路径。
     * @param targetSize 目标尺寸，单位kb。
     * @return 压缩后的图像。
     */
    public static byte[] compressImageToBytes(String imagePath, int targetSize) {
        return compressImageToBytes(getScaledBitmap(imagePath, 800f, 480f), targetSize);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 获取缩放后的图像。
     * @param imagePath 图像路径。
     * @param targetHeight 目标图像高度。
     * @param targetWidth 目标图像宽度。
     * @return 缩放后的图像。
     */
    public static Bitmap getScaledBitmap(String imagePath, float targetHeight, float targetWidth) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > targetWidth) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / targetWidth);
        } else if (w < h && h > targetHeight) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / targetHeight);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        return BitmapFactory.decodeFile(imagePath, newOpts);
    }

    /**
     * 获取图像的水平镜像。
     * @param bmp 原始图像。
     * @return 水平镜像图像。
     */
    public static Bitmap filpHorizontal(@NonNull Bitmap bmp) {
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        Bitmap convertBmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(convertBmp);
        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1); //镜像水平翻转
        Bitmap newBmp = Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true);
        cv.drawBitmap(newBmp, new Rect(0, 0,newBmp.getWidth(), newBmp.getHeight()),new Rect(0, 0, w, h), null);
        return convertBmp;
    }
}
