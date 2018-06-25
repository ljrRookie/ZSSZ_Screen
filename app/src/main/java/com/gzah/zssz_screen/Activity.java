package com.gzah.zssz_screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import butterknife.ButterKnife;

/**
 * Created by 林佳荣 on 2018/6/11.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class Activity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity);
        ButterKnife.bind(this);
       // initWebView();
    }




}
