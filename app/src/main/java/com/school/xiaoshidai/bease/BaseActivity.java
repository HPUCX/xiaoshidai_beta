package com.school.xiaoshidai.bease;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import cn.bmob.v3.Bmob;

/**
 * 用于初始化Bomb，初始化一些资源
 * Created by hjs on 2015/11/7.
 */
public class BaseActivity extends Activity{
    public static String APPID = "3b72404152f4513f21733475e8c71a7c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);

        Bmob.initialize(this, APPID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
