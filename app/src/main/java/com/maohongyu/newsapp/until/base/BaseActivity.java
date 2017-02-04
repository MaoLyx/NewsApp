package com.maohongyu.newsapp.until.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by hongyu on 2017/1/31 上午11:42.
 * 作用：
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInitView();
        setView();
        afterInitView();
    }

    protected abstract void beforeInitView();

    protected abstract void setView();

    protected abstract void afterInitView();

}
