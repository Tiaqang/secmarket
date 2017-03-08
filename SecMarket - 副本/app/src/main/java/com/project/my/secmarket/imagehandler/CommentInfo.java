package com.project.my.secmarket.imagehandler;

/**
 * Created by Tiaqang on 2016/12/22.
 */
public class CommentInfo {
    String name;
    String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommentInfo(String name, String content) {
        this.name = name;
        this.content = content;
    }


}
