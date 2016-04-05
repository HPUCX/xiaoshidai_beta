package com.school.xiaoshidai.third;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.school.xiaoshidai.bean._User;
import com.school.xiaoshidai.bease.BaseActivity;
import com.school.xiaoshidai.util.IsVailable;
import com.school.xiaoshidi.MainActivity;
import com.school.xiaoshidi.R;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by hjs on 2016/3/31.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_qq;
    private IUiListener loginIUiListener;
    private IUiListener userInfoListener;
    private _User _user =new _User();
    public static Tencent mTencent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        //初始化BmobSDK
        Bmob.initialize(this, Constants.BMOB_APPID);
        initView();
        initDate();

        if (mTencent == null) {
            mTencent = Tencent.createInstance(Constants.QQ_APP_ID, getApplicationContext());
        }

        BmobUser user = BmobUser.getCurrentUser(this);
        if (user != null) {
            Log.d("user1111",user.getUsername());
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }
    void initDate() {
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
                                mTencent.reAuth(LoginActivity.this, "all", new IUiListener() {

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

                        LoginActivity.this.runOnUiThread(r);
                    } else {
                        String nickName = jo.getString("nickname");
                        Log.d("nickname",nickName);
                        Toast.makeText(LoginActivity.this, "你好，" + nickName, Toast.LENGTH_LONG).show();
                        Message message=Message.obtain();
                        message.what=0;
                        message.obj=nickName;
                        sendNickNameHandler.sendMessage(message);
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

    private Handler sendNickNameHandler =new Handler(){
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    _user.setUsername((String) msg.obj);
                    _user.update(LoginActivity.this, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            toast("nickname");
                            Log.d("nickname",(String)msg.obj);
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                    break;
            }
        }
    };
    public void initView() {
        btn_qq = (Button) findViewById(R.id.btn_qq);
        btn_qq.setOnClickListener(this);
        loginIUiListener = new IUiListener() {
            @Override
            public void onComplete(Object arg0) {
                if (arg0 != null) {
                    JSONObject jsonObject = (JSONObject) arg0;
                    try {
                        String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
                        String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
                        String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
                        mTencent.setOpenId(openId);
                        mTencent.setAccessToken(token, expires);
                        BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ, token, expires, openId);
                        loginWithAuth(authInfo);
                    } catch (JSONException e) {
                    }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_qq:
                //QQ授权登录
                //判断用户有没有安装qq
                if (IsVailable.isQQAvilble(LoginActivity.this)) {
                    qqAuthorize();
                } else {
                    toast("对不起，您没有安装手机qq");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }


    /**
     * @param authInfo
     * @return void
     * @throws
     * @method loginWithAuth
     */
    public void loginWithAuth(final BmobUser.BmobThirdUserAuth authInfo) {
        BmobUser.loginWithAuthData(LoginActivity.this, authInfo, new OtherLoginListener() {
            @Override
            public void onSuccess(JSONObject userAuth) {
                Intent intent = new Intent(LoginActivity.this, UserManger.class);
                intent.putExtra("json", userAuth.toString());
                intent.putExtra("from", authInfo.getSnsType());
                startActivity(intent);
            }

            @Override
            public void onFailure(int code, String msg) {
                toast("第三方登陆失败：" + msg);
            }

        });
    }



    //qq登陆
    private void qqAuthorize() {
        mTencent.login(this, "all", loginIUiListener);
    }

    /**
     * 一定要注意添加这个方法，
     * 此方法的作用是把从腾讯服务器上得到的tokenid返回给腾讯
     * 用以验证身份，否则会提示
     * {"ret":-1,"msg":"client request's parameters are invalid, invalid openid"}
     * 这个错误。
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_API) {
            if (resultCode == com.tencent.connect.common.Constants.RESULT_LOGIN) {
                Tencent.handleResultData(data, loginIUiListener);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void toast(String msg) {
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

