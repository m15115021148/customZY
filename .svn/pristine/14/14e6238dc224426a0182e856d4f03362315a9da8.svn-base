package com.bluemobi.zhongyou.application;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;

import com.bluemobi.zhongyou.log.LogUtil;
import com.sunmi.printerhelper.utils.AidlUtil;

/**
 * Created by ${chenM} on ${2017}.
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    public int screenWidth = 0;
    public int screenHeight = 0;
    private boolean isAidl = true;//sunmi printer

    @Override
    public void onCreate() {
        super.onCreate();
        getScreenSize();
        AidlUtil.getInstance().connectPrinterService(this);
    }


    public static MyApplication getInstance(){
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        instance = this;
    }

    /**
     * 获取屏幕尺寸
     */
    private void getScreenSize() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
        LogUtil.w("height:"+screenHeight+"\nwidth:"+screenWidth);
    }

    /**
     * get version name
     *
     * @return name
     */
    public String getVersionName() {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get App versionCode
     * @return code
     */
    public String getVersionCode(){
        PackageManager packageManager=getApplicationContext().getPackageManager();
        PackageInfo packageInfo;
        String versionCode="";
        try {
            packageInfo=packageManager.getPackageInfo(getApplicationContext().getPackageName(),0);
            versionCode=packageInfo.versionCode+"";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public boolean isAidl() {
        return isAidl;
    }

    public void setAidl(boolean aidl) {
        isAidl = aidl;
    }

}
