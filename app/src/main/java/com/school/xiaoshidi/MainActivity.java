package com.school.xiaoshidi;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.RadioGroup;

import com.school.xiaoshidai.fragement.Find;
import com.school.xiaoshidai.fragement.MySelf;
import com.school.xiaoshidai.fragement.ShouYe;
import com.school.xiaoshidai.fragement.Sort;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.bmob.v3.Bmob;

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
