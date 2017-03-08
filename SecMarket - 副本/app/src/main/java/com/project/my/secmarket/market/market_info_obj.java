package com.project.my.secmarket.market;

import com.project.my.secmarket.ninegridimage.Image;

import java.util.List;

/**
 * Created by Tiaqang on 2016/12/21.
 */
public class market_info_obj {
    String username;
    String time;
    String text;
    String price;
    String telnumber;
    String zan;
    String pinglun;
    String zhuanfa;
    List<Image> image;


    int head_image;

    public market_info_obj(String username, String time, String text, String price, String telnumber, int head_image,List<Image> image ) {
        this.username = username;
        this.time = time;
        this.text = text;
        this.price = price;
        this.telnumber = telnumber;
        this.head_image = head_image;
        this.image=image;
    }
    public int getHead_image() {
        return head_image;
    }

    public void setHead_image(int head_image) {
        this.head_image = head_image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTelnumber() {
        return telnumber;
    }

    public void setTelnumber(String telnumber) {
        this.telnumber = telnumber;
    }

    public String getZan() {
        return zan;
    }

    public void setZan(String zan) {
        this.zan = zan;
    }

    public String getPinglun() {
        return pinglun;
    }

    public void setPinglun(String pinglun) {
        this.pinglun = pinglun;
    }

    public String getZhuanfa() {
        return zhuanfa;
    }

    public void setZhuanfa(String zhuanfa) {
        this.zhuanfa = zhuanfa;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }
}
