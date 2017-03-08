package com.project.my.secmarket.market;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.my.secmarket.R;
import com.project.my.secmarket.ninegridimage.CustomImageView;
import com.project.my.secmarket.ninegridimage.Image;
import com.project.my.secmarket.ninegridimage.NineGridlayout;
import com.project.my.secmarket.ninegridimage.ScreenTools;

import java.util.ArrayList;

/**
 * Created by Tiaqang on 2016/12/21.
 */
public class Fragment1_adapter extends RecyclerView.Adapter<Fragment1_adapter.ItemViewHolder> {

    private Context mcontext;
    private ArrayList<market_info_obj> list;

    public Fragment1_adapter(Context mcontext,ArrayList<market_info_obj> list) {
        this.mcontext=mcontext;
        this.list=list;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mcontext)
                .inflate(R.layout.market_info, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int position) {
        market_info_obj obj=list.get(position);
        itemViewHolder.username.setText(obj.getUsername());
        itemViewHolder.text.setText(obj.getText());
        itemViewHolder.time.setText(obj.getTime());
        itemViewHolder.price.setText("¥"+obj.getPrice());
        itemViewHolder.telnumber.setText(obj.getTelnumber());
        //itemViewHolder.zan.setText(obj.getZan());
        //itemViewHolder.pinglun.setText(obj.getPinglun());
        //itemViewHolder.zhuanfa.setText(obj.getZhuanfa());

        itemViewHolder.head_image.setImageResource(obj.getHead_image());

        if (obj.getImage().isEmpty() || obj.getImage().isEmpty()) {
            itemViewHolder.ivMore.setVisibility(View.GONE);
            itemViewHolder.ivOne.setVisibility(View.GONE);
        } else if (obj.getImage().size() == 1) {
            itemViewHolder.ivMore.setVisibility(View.GONE);
            itemViewHolder.ivOne.setVisibility(View.VISIBLE);

            handlerOneImage(itemViewHolder, obj.getImage().get(0));
        } else {
            itemViewHolder.ivMore.setVisibility(View.VISIBLE);
            itemViewHolder.ivOne.setVisibility(View.GONE);

            itemViewHolder.ivMore.setImagesData(obj.getImage());
        }


        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = itemViewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(itemViewHolder.itemView, pos);
                }
            });

            itemViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = itemViewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(itemViewHolder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView username,time,text,price,telnumber,zan,pinglun,zhuanfa;
        ImageView head_image,image1,image2;
        NineGridlayout ivMore;
        CustomImageView ivOne;


        public ItemViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            text = (TextView) itemView.findViewById(R.id.text);
            price = (TextView) itemView.findViewById(R.id.price);
            time = (TextView) itemView.findViewById(R.id.time);
            telnumber = (TextView) itemView.findViewById(R.id.telnumber);
            //zan = (TextView) itemView.findViewById(R.id.zan);
            //pinglun = (TextView) itemView.findViewById(R.id.pinglun);
            //zhuanfa = (TextView) itemView.findViewById(R.id.zhuanfa);

            head_image= (ImageView) itemView.findViewById(R.id.head_image);
            //image1= (ImageView) itemView.findViewById(R.id.image1);
            //image2= (ImageView) itemView.findViewById(R.id.image2);

            ivMore = (NineGridlayout) itemView.findViewById(R.id.iv_ngrid_layout);
            ivOne = (CustomImageView) itemView.findViewById(R.id.iv_oneimage);
        }
    }

    private void handlerOneImage(ItemViewHolder viewHolder, Image image) {
        int totalWidth;
        int imageWidth;
        int imageHeight;
        ScreenTools screentools = ScreenTools.instance(mcontext);
        totalWidth = screentools.getScreenWidth() - screentools.dip2px(80);
        imageWidth = screentools.dip2px(image.getWidth());
        imageHeight = screentools.dip2px(image.getHeight());
        if (image.getWidth() <= image.getHeight()) {
            if (imageHeight > totalWidth) {
                imageHeight = totalWidth;
                imageWidth = (imageHeight * image.getWidth()) / image.getHeight();
            }
        } else {
            if (imageWidth > totalWidth) {
                imageWidth = totalWidth;
                imageHeight = (imageWidth * image.getHeight()) / image.getWidth();
            }
        }
        ViewGroup.LayoutParams layoutparams = viewHolder.ivOne.getLayoutParams();
        layoutparams.height = imageHeight;
        layoutparams.width = imageWidth;
        viewHolder.ivOne.setLayoutParams(layoutparams);
        viewHolder.ivOne.setClickable(true);
        viewHolder.ivOne.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
        viewHolder.ivOne.setImageUrl(image.getUrl());

    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickListener = mOnItemClickLitener;
    }
}
