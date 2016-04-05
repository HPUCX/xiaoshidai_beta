package com.school.xiaoshidi;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.school.xiaoshidai.bease.BaseActivity;
import com.school.xiaoshidai.bean.Feedback;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by hjs on 2016/3/16.
 */
public class SendFeedBack extends BaseActivity implements View.OnClickListener {
    private EditText information_edt;
    private EditText content_edt;
    static String msg1;
    static String msg2;
    private TextView actionbar_SchoolHistory_Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);
        View actionBar_layout = LayoutInflater.from(this).inflate(R.layout.actionbar_layout, null);
        getActionBar().setCustomView(actionBar_layout);
        actionbar_SchoolHistory_Text = (TextView) findViewById(R.id.actionbar_Text);
        actionbar_SchoolHistory_Text.setText("我要吐槽");
        information_edt = (EditText) findViewById(R.id.information_edt);
        content_edt = (EditText) findViewById(R.id.content_edt);

    }

    @Override
    public void onClick(View view) {
        String information_String = information_edt.getText().toString();
        String content_String = content_edt.getText().toString();
//        if (TextUtils.isEmpty(information_String)) {
//            Toast.makeText(this, "请输入你的联系方式", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(content_String)) {
            Toast.makeText(this, "请输入您的建议", Toast.LENGTH_SHORT).show();
        } else {
            if (information_String.equals(msg1) && content_String.equals(msg2)) {
                Toast.makeText(this, "请勿重复提交反馈", Toast.LENGTH_SHORT).show();
            } else {
                msg1 = information_String;
                msg2 = content_String;
                //发送信息给服务器
                saveFeedbackMsg(msg1, msg2);
                Toast.makeText(this, "您的信息已经发送，谢谢您的参与", Toast.LENGTH_SHORT).show();
            }
        }
    }
    /*
    反馈信息发送给服务器
     */
    private void saveFeedbackMsg(String message1, String message2) {
        Feedback feedback = new Feedback();
        feedback.setContent(message2);
        feedback.setContacts(message1);
        feedback.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.e("feedback.save", "success");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("feedback.save", "failure");
            }
        });
    }

}
