package com.example.oneday.largebitmaptest.task;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by One Day on 2016/6/28.
 */

public class AsyncDrawable extends BitmapDrawable {

    /**
     * 绑定BitmapWorkerTask到引用
     */
    private WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

    /**
     * 构造AsyncDrawable
     * @param res
     * @param bitmap
     * @param bitmapWorkerTask of AysncTask is BitmapWorkerTask
     */
    public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask){
        super(res, bitmap);
        bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
    }

    /**
     * 外部不能直接访问
     * @return
     */
    private BitmapWorkerTask getBitmapWorkerTask(){
        return bitmapWorkerTaskReference.get();
    }

    /**
     * 获取BitmapWorkerTask
     * @param imageView
     * @return
     */
    public BitmapWorkerTask getBitmapWorkerTask(ImageView imageView){
        if(imageView != null){
            final Drawable drawable = imageView.getDrawable();
            if(drawable instanceof AsyncDrawable){
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    public boolean cancelPotentialTask(int data, ImageView imageView){
        BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
        int bitmapData = bitmapWorkerTask.getData();
        if(bitmapData == 0 || bitmapData != data){
            //Cancel previous task.
            bitmapWorkerTask.cancel(true);
        }else{
            //The same work is already in progress.
            return false;
        }

        //No task associated with the imageView or an existed task was canceled.
        return true;
    }
}
