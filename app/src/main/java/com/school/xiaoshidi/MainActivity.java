package com.school.xiaoshidi;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.school.xiaoshidai.bean.MyBmobInstallation;
import com.school.xiaoshidai.fragement.Find;
import com.school.xiaoshidai.fragement.MySelf;
import com.school.xiaoshidai.fragement.ShouYe;
import com.school.xiaoshidai.fragement.Sort;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.update.BmobUpdateAgent;

public class MainActivity extends FragmentActivity {

    private RadioGroup myTabRg;
    private ShouYe mShouYe;
    private Sort mSort;
    private Find mFind;
    private MySelf mMyself;


    public static String APPID = "3b72404152f4513f21733475e8c71a7c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setHomeButtonEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
        //让overflow显示出来
        setOverflowShowingAlways();

        Bmob.initialize(this, APPID);
        //initAppVersion方法适合开发者调试自动更新功能时使用，
        // 一旦AppVersion表在后台创建成功，建议屏蔽或删除此方法，否则会生成多行记录。
        //BmobUpdateAgent.initAppVersion(this);
        //自动更新
        BmobUpdateAgent.update(this);

        //获取设备信息
        acquireDevice();
        //初始化view
        initView();
    }

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


    /**
     * overflow上面的各个功能
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update:

                break;
            case R.id.contact:
                break;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //让overflow中的选项显示图标
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //由于手机的不同，ActionBar最右边的overflow按钮有时候显示，有时候不显示，解决办法
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration configuration = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(configuration, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
