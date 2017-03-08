package com.project.my.secmarket.imagehandler;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by hzhk on 2016/7/15.
 */
public class ImageHandler extends Handler {

//    请求更新imageView
    public static final int IMAGE_UPDATE = 0;

//    请求轮播暂停
    public static final int IMAGE_PAUSE = 1;

//    请求记录用户手动滑动时最新页号，imageView改变了，记录最新的位置
//    这样下一次轮播时，位置不会混
    public static final int IMAGE_PAGE = 2;

//    记录轮播时间
    public static final int IMAGE_DURATION = 3000;

    private WeakReference<MainActivity_image> mWeakReference;

//    记录最新页号
    private int currentPosition = Integer.MAX_VALUE/2;


    public ImageHandler(WeakReference<MainActivity_image> wk){
        this.mWeakReference = wk;
    }



    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        MainActivity_image activity = mWeakReference.get();
        if (activity == null) {
            return;
        }

//        当手指滑动时，消除自动轮播影响
        if(activity.mImageHandler.hasMessages(IMAGE_UPDATE)){
            Log.d("flag","--------------------->remove");
            activity.mImageHandler.removeMessages(IMAGE_UPDATE);
        }
        switch (msg.what){
            case IMAGE_UPDATE:
                currentPosition++;
                Log.d("flag","---------------->image_update: "+currentPosition);
                activity.mViewPager.setCurrentItem(currentPosition);
                break;
            case IMAGE_PAUSE://暂停什么都不做
                Log.d("flag","---------------->image_pause");
                break;
            case IMAGE_PAGE://记录用户手动滑动的位置
                Log.d("flag","----------------->image_page: "+msg.arg1);
                currentPosition = msg.arg1;
//                准备下一次自动轮播
                activity.mImageHandler.sendEmptyMessageDelayed(IMAGE_UPDATE,IMAGE_DURATION);
                break;

        }
    }
}
