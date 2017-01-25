package com.maohongyu.newsapp.presenter.logo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.maohongyu.newsapp.view.home.HomeActivity;
import com.maohongyu.newsapp.view.logo.MainActivity;

/**
 * Created by maohongyu on 17/1/5.
 */

public class ILogoCompl implements ILogo {

    private Context mContext;


    @Override
    public void autoToHome() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(mContext,HomeActivity.class);
                mContext.startActivity(intent);
                MainActivity mContext1 = (MainActivity)mContext;
                mContext1.finish();
            }
        },3000);

    }

    public ILogoCompl(Context mContext) {
        this.mContext = mContext;
    }
}
