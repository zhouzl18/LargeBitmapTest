package com.example.oneday.largebitmaptest.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by One Day on 2016/6/28.
 */

public class BitmapUtil {


    /**
     * Calculate inSampleSize value.
     * @param options   of BitmapFactory.Options
     * @param reqWidth of requested width.
     * @param reqHeight of requested height.
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        //Raw width and height of image.
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if(width > reqWidth || height > reqHeight){
            final int halfWidth = width / 2;
            final int halfHeight = height / 2;

            //Calculate the largest inSampleSize value that is a power of 2 and keeps both
            //width and height larger than the requested width and height.
            while((halfWidth / inSampleSize) > reqWidth && (halfHeight / inSampleSize) > reqHeight){
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSimpleBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight){
        //First decode with inJustDecodeBounds=true to check dimensions.
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        //Calculate inSampleSize.
        int inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inSampleSize = inSampleSize;

        //Decode bitmap with inSamplesize set.
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


}
