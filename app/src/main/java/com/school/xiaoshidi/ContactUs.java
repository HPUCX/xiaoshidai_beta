package com.school.xiaoshidi;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.school.xiaoshidai.bease.BaseActivity;

/**
 * Created by hjs on 2016/3/16.
 */
public class ContactUs extends BaseActivity {
    private TextView contact_textView;
    //用于显示
    private TextView actionbar_contact_Text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactus);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);
        View actionBar_layout = LayoutInflater.from(this).inflate(R.layout.actionbar_layout, null);
        getActionBar().setCustomView(actionBar_layout);

        initView();

        actionbar_contact_Text.setText("关于我们");

        //创建一个 SpannableString对象
        SpannableString sp=new SpannableString("扫一扫二维码，关注我的新浪微博(A_Frog_In_A_Well)");
        //最后一个参数的含义是前后都不包括
        sp.setSpan(new URLSpanNoUnderLine("http://m.weibo.cn/u/5288231404"), 16, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        contact_textView.setText(sp);
        contact_textView.setMovementMethod(LinkMovementMethod.getInstance());

    }

    void initView(){
        actionbar_contact_Text = (TextView) findViewById(R.id.actionbar_Text);
        contact_textView=(TextView)findViewById(R.id.contact_textView);
    }
}



/**
 * 自定义一个URLSpan
 * TextView里超链接文本去掉下划线的方法
 */
 class URLSpanNoUnderLine extends URLSpan{
     URLSpanNoUnderLine(String url){
          super(url);
     }

     @Override
     public void updateDrawState(TextPaint ds) {
         super.updateDrawState(ds);
         ds.setUnderlineText(false);
     }
 }