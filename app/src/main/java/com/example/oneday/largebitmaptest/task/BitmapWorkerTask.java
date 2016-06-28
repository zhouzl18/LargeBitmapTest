package com.example.oneday.largebitmaptest.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.oneday.largebitmaptest.util.BitmapUtil;

import java.lang.ref.WeakReference;

/**
 * Created by One Day on 2016/6/28.
 */

public class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {

    private Context context;
    private WeakReference<ImageView> imageViewWeakReference;
    private int data;

    public BitmapWorkerTask(Context context, ImageView imageView){
        //Use WeakReference to ensure the ImageView can be garbage collected.
        imageViewWeakReference = new WeakReference<ImageView>(imageView);
        this.context = context;
    }

    //Decode image in background.
    @Override
    protected Bitmap doInBackground(Integer... integers) {
        data = integers[0];
        return BitmapUtil.decodeSimpleBitmapFromResource(context.getResources(), data, 100, 100);
    }


    //Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
//        super.onPostExecute(bitmap);
        if(isCancelled()){
            bitmap = null;
        }
        if(imageViewWeakReference != null && bitmap != null){
            final ImageView imageView = imageViewWeakReference.get();
//            final BitmapWorkerTask workerTask =
            if(imageView != null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    /**
     * Get id of resource of bitmap.
     * @return
     */
    public int getData(){
        return data;
    }
}
