package com.gzah.zssz_screen.utils;

import com.gzah.zssz_screen.listener.ITimerListener;

import java.util.TimerTask;

/**
 * Created by 林佳荣 on 2018/4/19.
 * Github：https://github.com/ljrRookie
 * Function ：
 */
public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    @Override
    public void run() {
        if (mITimerListener != null) {
            mITimerListener.onTimer();
        }
    }

}
