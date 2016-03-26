package com.school.xiaoshidai.bean;

/**
 * Created by hjs on 2016/3/26.
 */
public class ShouyeListViewBean {
    private String topTextView;
    private String bottomTextView;

    public int getShouyeListImage() {
        return shouyeListImage;
    }

    public void setShouyeListImage(int shouyeListImage) {
        this.shouyeListImage = shouyeListImage;
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

    private int shouyeListImage;

    public ShouyeListViewBean(String topTextView,String bottomTextView,int shouyeListImage){
        this.bottomTextView=bottomTextView;
        this.topTextView=topTextView;
        this.shouyeListImage=shouyeListImage;
    }

}
