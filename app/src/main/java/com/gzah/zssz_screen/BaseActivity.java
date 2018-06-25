package com.gzah.zssz_screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 林佳荣 on 2018/6/1.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class BaseActivity extends AppCompatActivity {

    // 都是static声明的变量，避免被实例化多次；因为整个app只需要一个计时任务就可以了。
    private static Timer mTimer; // 计时器，每1秒执行一次任务
    private static MyTimerTask mTimerTask; // 计时任务，判断是否未操作时间到达5s
    private static long mLastActionTime; // 上一次操作时间
    private static Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
    }

    // 每当用户接触了屏幕，都会执行此方法
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mLastActionTime = System.currentTimeMillis();
        Log.e("wanghang", "user action");
        return super.dispatchTouchEvent(ev);
    }

    private static class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            Log.e("wanghang", "check time");
            // 5s未操作
            if (System.currentTimeMillis() - mLastActionTime > 70000) {

                exit();
                stopTimer();
            }
        }
    }

    // 退出登录
    public static void exit() {
        Intent intent = new Intent(mActivity, LauncherActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
        mActivity.overridePendingTransition(R.anim.action_alpha_in, R.anim.action_alpha_out);

    }

    // 登录成功，开始计时
    protected static void startTimer() {
        mTimer = new Timer();
        mTimerTask = new MyTimerTask();
        // 初始化上次操作时间为登录成功的时间
        mLastActionTime = System.currentTimeMillis();
        // 每过1s检查一次
        mTimer.schedule(mTimerTask, 0, 1000);
        Log.e("wanghang", "start timer");
    }

public static  void stopTimer(){
    if(mTimer!=null){
        mTimer.cancel();
        mTimer =null;
    }
}
}
