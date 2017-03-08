package com.project.my.secmarket.imagehandler;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzhk on 2016/6/8.
 */
public class FocusImageAdapter extends PagerAdapter implements View.OnClickListener {

    private Context mContext;
    private List<ImageView> mImageViews = new ArrayList<>();

    public FocusImageAdapter( Context context,List<ImageView> mImageViews) {
        this.mContext = context;
        this.mImageViews=mImageViews;
        /*mImageViews.add(loadImage(R.mipmap.p1));
        mImageViews.add(loadImage(R.mipmap.p2));
        mImageViews.add(loadImage(R.mipmap.p3));
        mImageViews.add(loadImage(R.mipmap.p4));
        mImageViews.add(loadImage(R.mipmap.p5));
        mImageViews.add(loadImage(R.mipmap.p6));
        mImageViews.add(loadImage(R.mipmap.p7));
        mImageViews.add(loadImage(R.mipmap.p8));
        mImageViews.add(loadImage(R.mipmap.p9));
        mImageViews.add(loadImage(R.mipmap.p10));*/


    }

  /*  private ImageView loadImage(int id) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(id);
        return imageView;
    }*/

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container,  int position) {

        if(position>=mImageViews.size()){
            position = position%mImageViews.size();
        }
        ImageView imageView = mImageViews.get(position);
        if (imageView != null) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            ViewParent parent = imageView.getParent();

            if (parent != null) {
                ViewGroup viewGroup = (ViewGroup) parent;

                viewGroup.removeView(imageView);
            }
            imageView.setTag(position);
            imageView.setOnClickListener(this);
            container.addView(imageView);
            return imageView;
        } else {
            return null;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();

        Toast.makeText(mContext,"您点击了： "+position,Toast.LENGTH_LONG).show();
    }
}
