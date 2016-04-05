package com.school.xiaoshidai.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by hjs on 2016/4/3.
 */
public class IsVailable {
    /**
     * 判断微信是否安装
     */
    public static boolean isWeiXinAvilble(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        if(packageInfoList!=null){
            for (int i=0;i<packageInfoList.size();i++){
                String packageName=packageInfoList.get(i).packageName;
                if(packageName.equals("com.tencent.mm")){
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * 判断微信是否安装
     */
    public static boolean isQQAvilble(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        if(packageInfoList!=null){
            for (int i=0;i<packageInfoList.size();i++){
                String packageName=packageInfoList.get(i).packageName;
                if(packageName.equals("com.tencent.mobileqq")){
                    return true;
                }
            }
        }
        return false;
    }
}
