package com.project.my.secmarket.imagehandler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.my.secmarket.R;

import java.util.List;

/**
 * Created by Tiaqang on 2016/12/22.
 */
public class CommentAdapter extends BaseAdapter{

    Context mcontext;
    List<CommentInfo> list;

    public CommentAdapter(Context mcontext, List<CommentInfo> list) {
        this.mcontext = mcontext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView =LayoutInflater.from(mcontext).inflate(R.layout.list_detail_info,parent,false);
            holder.comment_name= (TextView) convertView.findViewById(R.id.comment_name);
            holder.comment_content= (TextView) convertView.findViewById(R.id.comment_content);

            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }
        CommentInfo info=list.get(position);
        holder.comment_content.setText(info.getContent());
        holder.comment_name.setText(info.getName());
        return convertView;

    }

    public static class ViewHolder{
        TextView comment_name;
        TextView comment_content;
    }
}
