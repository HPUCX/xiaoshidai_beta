package com.school.xiaoshidai.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by hjs on 2016/4/5.
 */
public class _User extends BmobObject {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

}
