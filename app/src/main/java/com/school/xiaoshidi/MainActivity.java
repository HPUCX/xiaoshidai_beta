package com.school.xiaoshidi;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.school.xiaoshidai.bean.MyBmobInstallation;
import com.school.xiaoshidai.fragement.Find;
import com.school.xiaoshidai.fragement.MySelf;
import com.school.xiaoshidai.fragement.ShouYe;
import com.school.xiaoshidai.fragement.Sort;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

public class MainActivity extends FragmentActivity {

    private RadioGroup myTabRg;
    private ShouYe mShouYe;
    private Sort mSort;
    private Find mFind;
    private MySelf mMyself;
    //用于处理用户登陆
//    String json = "";
//    String from = "";

    public static String APPID = "3b72404152f4513f21733475e8c71a7c";

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.statusbar_bg);
        getActionBar().setHomeButtonEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);

        Bmob.initialize(this, APPID);
        //initAppVersion方法适合开发者调试自动更新功能时使用，
        // 一旦AppVersion表在后台创建成功，建议屏蔽或删除此方法，否则会生成多行记录。
        //BmobUpdateAgent.initAppVersion(this);

       // recieveLogin();
        //对更新进行监听
        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateResponse) {
                if (updateStatus == UpdateStatus.Yes) {
                    Toast.makeText(MainActivity.this, "亲，有新版本更新了", Toast.LENGTH_SHORT).show();
                } else if (updateStatus == UpdateStatus.No) {
                    Toast.makeText(MainActivity.this, "已经是最新版了", Toast.LENGTH_SHORT).show();
                } else if (updateStatus == UpdateStatus.IGNORED) {
                    Toast.makeText(MainActivity.this, "您已忽略改版本", Toast.LENGTH_SHORT).show();
                } else if (updateStatus == UpdateStatus.TimeOut) {
                    Toast.makeText(MainActivity.this, "您的网络好慢啊,请稍后再试", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //自动更新
        BmobUpdateAgent.update(this);
        //获取设备信息
        acquireDevice();
        //初始化view
        initView();
    }
//    void recieveLogin(){
//        json = getIntent().getStringExtra("json");
//        from = getIntent().getStringExtra("from");
//    }
    public void initView() {
        mShouYe = new ShouYe();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mShouYe).commit();
        myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
        myTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.shouye:
                        mShouYe = new ShouYe();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_content, mShouYe)
                                .commit();
                        break;
                    case R.id.sort:
                        mSort = new Sort();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_content, mSort)
                                .commit();
                        break;
                    case R.id.find:
                        mFind = new Find();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_content, mFind)
                                .commit();
                        break;
                    case R.id.myself:
                        mMyself = new MySelf();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_content, mMyself)
                                .commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    //获取手机信息
    private void acquireDevice() {
        MyBmobInstallation.getCurrentInstallation(this);
        MyBmobInstallation myBmobInstallation = new MyBmobInstallation(this);
        myBmobInstallation.setDevice(Build.MODEL);
        myBmobInstallation.save(this, new SaveListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    //再按一次退出
    // 双击退出
    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再次点击退出",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                // 将应用程序在后台运行
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
