package com.school.xiaoshidai.bean;

/**
 * Created by hjs on 2016/3/16.
 */
public class MyselfBean {
    private String textView;
    private int leftImage;
    private int rightImage;

    public MyselfBean(String textView,int leftImage,int rightImage){
        this.leftImage=leftImage;
        this.textView=textView;
        this.rightImage=rightImage;
    }
    public int getRightImage() {
        return rightImage;
    }

    public void setRightImage(int rightImage) {
        this.rightImage = rightImage;
    }

    public int getLeftImage() {
        return leftImage;
    }

    public void setLeftImage(int leftImage) {
        this.leftImage = leftImage;
    }

    public String getTextView() {
        return textView;
    }

    public void setTextView(String textView) {
        this.textView = textView;
    }



}
