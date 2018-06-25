package com.gzah.zssz_screen.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;


import com.gzah.zssz_screen.LauncherActivity;

import com.vondear.rxtools.view.dialog.RxDialog;

/**
 * Created by 林佳荣 on 2018/5/14.
 * Github：https://github.com/ljrRookie
 * Function ：异常捕获类
 */

public class CrashHandle implements Thread.UncaughtExceptionHandler {
    private Context mContext;
    private RxDialog mDialog;
    private static CrashHandle mInstance;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandle() {
    }

    public static CrashHandle getInstance() {
        if (mInstance == null) {
            synchronized (CrashHandle.class) {
                if (mInstance == null) {
                    mInstance = new CrashHandle();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        if (!handleException(e)) {
            //未处理异常，调用系统 默认的处理器处理
            if (mDefaultHandler != null) {
                mDefaultHandler.uncaughtException(t, e);
            }
        } else {
            //人为处理异常


          /*  //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);*/

            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    Intent intent = new Intent(mContext, LauncherActivity.class);
                    PendingIntent restartIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                    mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10, restartIntent);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);

                    Looper.loop();
                }
            }.start();

        }
    }

    /**
     * 人为处理异常
     *
     * @param e
     * @return true： 已经处理异常
     */
    private boolean handleException(Throwable e) {
        if (e == null) {
            return false;
        }

        return true;
    }
}
