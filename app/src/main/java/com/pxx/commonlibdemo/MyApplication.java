package com.pxx.commonlibdemo;

import android.app.Application;

import com.vondear.rxtool.RxTool;

/**
 * Description:
 * Created by pxx on 2018/8/24 14:00
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化RxTool
        RxTool.init(this);
    }
}
