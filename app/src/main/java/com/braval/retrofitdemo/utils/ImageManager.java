/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.braval.retrofitdemo.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * {@code ImageManager} is used to retrieve and store images
 * in the media content provider.
 */
public class ImageManager {
    private static final String TAG = "ImageManager";

    private ImageManager() {
        
    }

    //
    // Stores a bitmap or a jpeg byte array to a file (using the specified
    // directory and filename). Also add an entry to the media store for
    // this picture. The title, dateTaken, location are attributes for the
    // picture. The degree is a one element array which returns the orientation
    // of the picture.
    //
    @SuppressLint("NewApi")
    public static Bitmap storeImage(final String fileName, Bitmap source, byte[] jpegData, int[] degree) {
        // We should store image data earlier than insert it to ContentProvider,
        // otherwise we may not be able to generate thumbnail in time.
        if (fileName == null)
            return null;
        OutputStream outputStream = null;
        String filePath = null;
        Bitmap mBitmap = null;
//        try {
//            File dir = new File(Consts.ROOT_PATH.replace(Consts.FilePathPrefix, ""));
//            if (!dir.exists())
//                dir.mkdirs();
//            filePath = dir.getPath() + fileName;
//            File file = new File(dir.getPath(), fileName);
//            mBitmap = (BitmapFactory.decodeByteArray(jpegData, 0, jpegData.length));
//            ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
//            if (null != mBitmap) {
//                mBitmap.compress(CompressFormat.JPEG, Consts.JPEG_HIGH_QUALITY, outputStream2);
//                try {
//                    outputStream2.flush();
//                    outputStream2.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            //jpegData=;
//
//            outputStream = new FileOutputStream(file);
//            if (source != null) {
//                source.compress(CompressFormat.JPEG, Consts.JPEG_HIGH_QUALITY, outputStream2);
//                degree[0] = getExifOrientation(filePath);
//            } else {
////                outputStream.write(jpegData);
//                outputStream.write(outputStream2.toByteArray());
//                degree[0] = getExifOrientation(filePath);
//            }
//        } catch (FileNotFoundException ex) {
//            Log.w(TAG, ex);
//            return null;
//        } catch (IOException ex) {
//            Log.w(TAG, ex);
//            return null;
//        } finally {
//            UZoneUtils.closeSilently(outputStream);
//        }
        return mBitmap;
    }

    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            Log.e(TAG, "cannot read exif", ex);
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    break;
            }
        }
        return degree;
    }

    public static boolean hasStorage() {
        return hasStorage(true);
    }

    public static boolean hasStorage(boolean requireWriteAccess) {
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (requireWriteAccess) {
                return checkFsWritable();
            } else {
                return true;
            }
        } else if (!requireWriteAccess
                && Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    private static boolean checkFsWritable() {
        // Create a temporary file to see whether a volume is really writeable.
        // It's important not to put it in the root directory which may have a
        // limit on the number of files.
        String directoryName =
                Environment.getExternalStorageDirectory().toString() + "/DCIM";
        File directory = new File(directoryName);
        if (!directory.isDirectory()) {
            if (!directory.mkdirs()) {
                return false;
            }
        }
        return directory.canWrite();
    }
}
