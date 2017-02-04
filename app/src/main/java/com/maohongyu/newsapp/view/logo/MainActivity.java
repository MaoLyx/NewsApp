package com.maohongyu.newsapp.view.logo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.maohongyu.newsapp.R;
import com.maohongyu.newsapp.presenter.logo.ILogoCompl;

public class MainActivity extends AppCompatActivity {

    private ILogoCompl iLogoCompl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        iLogoCompl = new ILogoCompl(this);
        setContentView(R.layout.activity_main);
        iLogoCompl.autoToHome();
    }
}
