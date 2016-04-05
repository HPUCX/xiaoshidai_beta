package com.school.xiaoshidai.third;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.school.xiaoshidai.bease.BaseActivity;
import com.school.xiaoshidi.MainActivity;
import com.school.xiaoshidi.R;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import cn.bmob.v3.BmobUser;

/**
 * Created by hjs on 2016/3/16.
 */
public class UserManger extends BaseActivity implements View.OnClickListener {

    private Button add_count;
    private Button login_count;
    private ImageView userImage;
    private TextView user_text;
    private String json = "";
    private String from = "";
    private TextView actionbar_userTextView = null;
    private IUiListener userInfoListener;
    private UserInfo userInfo;
    private Tencent mTencent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermanger);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setTitle("返回");
        View actionBar_layout = LayoutInflater.from(this).inflate(R.layout.actionbar_layout, null);
        getActionBar().setCustomView(actionBar_layout);
        actionbar_userTextView = (TextView) findViewById(R.id.actionbar_Text);
        actionbar_userTextView.setText("账户管理");

        initView();
        initDate();
        judgmentActivity();
        getUserInfo();
    }
    void initDate() {
        mTencent = Tencent.createInstance(Constants.QQ_APP_ID, UserManger.this);
        userInfoListener = new IUiListener() {
            @Override
            public void onComplete(Object arg0) {
                if (arg0 == null) {
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) arg0;
                    int ret = jo.getInt("ret");
                    if (ret == 100030) {
                        //权限不够，需要增量授权
                        Runnable r = new Runnable() {
                            public void run() {
                                mTencent.reAuth(UserManger.this, "all", new IUiListener() {

                                    @Override
                                    public void onError(UiError arg0) {

                                    }

                                    @Override
                                    public void onComplete(Object arg0) {
                                        // TODO Auto-generated method stub

                                    }

                                    @Override
                                    public void onCancel() {
                                        // TODO Auto-generated method stub

                                    }
                                });
                            }
                        };

                        UserManger.this.runOnUiThread(r);
                    } else {
                        String nickName = jo.getString("nickname");
                        Toast.makeText(UserManger.this, "你好，" + nickName, Toast.LENGTH_LONG).show();
                        Message message=Message.obtain();
                        message.what=0;
                        message.obj=nickName;
                        acquireQQHandler.sendMessage(message);
                    }

                } catch (Exception e) {

                }


            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        };
    }

    private Handler acquireQQHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String result = (String) msg.obj;
                    if (result != null) {
                        user_text.setText((String) msg.obj);
                        add_count.setVisibility(View.GONE);
                    } else {
                        user_text.setText("暂无个人信息");
                    }
                    break;
            }

        }
    };

    private void judgmentActivity() {
        json = getIntent().getStringExtra("json");
        from = getIntent().getStringExtra("from");
        if (json != null && from != null) {
            startActivity(new Intent(UserManger.this, MainActivity.class));
        }
    }


    private void initView() {
        add_count = (Button) findViewById(R.id.add_count);
        add_count.setOnClickListener(this);
        login_count = (Button) findViewById(R.id.login_count);
        login_count.setOnClickListener(this);
        userImage = (ImageView) findViewById(R.id.user_image);
        user_text = (TextView) findViewById(R.id.user_text);
    }

    void getUserInfo(){
        userInfo = new UserInfo(UserManger.this, mTencent.getQQToken());
        userInfo.getUserInfo(userInfoListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_count:
                startActivity(new Intent(UserManger.this, LoginActivity.class));
                break;
            case R.id.login_count:
                loginoutActivity(view);
                break;
            default:
                break;
        }

    }

    //退出当前用户
    private void loginoutActivity(View view) {
        switch (view.getId()) {
            case R.id.login_count:
                dialogHandler.sendEmptyMessage(0);
                break;
            default:
                break;
        }
    }
    private Handler dialogHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(UserManger.this)
                            .setTitle("退出确认")
                            .setMessage("您确定要退出吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    BmobUser.logOut(UserManger.this);
                                    startActivity(new Intent(UserManger.this,
                                            LoginActivity.class));
                                    dialogInterface.dismiss();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    builder.create().show();
                    break;
            }
        }
    };
}
