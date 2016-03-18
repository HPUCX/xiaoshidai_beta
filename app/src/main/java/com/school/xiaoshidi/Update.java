package com.school.xiaoshidi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.school.xiaoshidai.bease.BaseActivity;

import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * Created by hjs on 2016/3/16.
 */
public class Update extends BaseActivity {
    private Button update_button;
    private TextView actionbar_Update_Text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setTitle("返回");
        View actionBar_layout = LayoutInflater.from(this).inflate(R.layout.actionbar_layout, null);
        getActionBar().setCustomView(actionBar_layout);
        actionbar_Update_Text = (TextView) findViewById(R.id.actionbar_Text);
        actionbar_Update_Text.setText("检查更新");

        update_button=(Button)findViewById(R.id.update_button);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUpdateAgent.forceUpdate(Update.this);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //如果点击返回键，后退
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
