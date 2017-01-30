package com.maohongyu.newsapp;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by hongyu on 2017/1/11 下午3:53.
 * 作用：
 */

public class NewsApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
