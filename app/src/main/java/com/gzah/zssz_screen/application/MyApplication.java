package com.gzah.zssz_screen.application;

import android.app.Application;
import android.content.Context;


import com.gzah.zssz_screen.utils.CrashHandle;
import com.vondear.rxtools.RxTool;

/**
 * Created by 林佳荣 on 2018/4/17.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class MyApplication extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        RxTool.init(mContext);
        CrashHandle.getInstance().init(mContext);

    }
}
