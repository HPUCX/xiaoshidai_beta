package com.school.xiaoshidai.bean;

/**
 * Created by hjs on 2016/3/17.
 */
public class ShouyeGridBean {
    private String topTextView;
    private String bottomTextView;

    public ShouyeGridBean(String topTextView, String bottomTextView, int shouyeImage) {
        this.topTextView = topTextView;
        this.bottomTextView = bottomTextView;
        this.shouyeImage = shouyeImage;
    }

    public String getTopTextView() {
        return topTextView;
    }

    public void setTopTextView(String topTextView) {
        this.topTextView = topTextView;
    }

    public String getBottomTextView() {
        return bottomTextView;
    }

    public void setBottomTextView(String bottomTextView) {
        this.bottomTextView = bottomTextView;
    }

    public int getShouyeImage() {
        return shouyeImage;
    }

    public void setShouyeImage(int shouyeImage) {
        this.shouyeImage = shouyeImage;
    }

    private int shouyeImage;
}
