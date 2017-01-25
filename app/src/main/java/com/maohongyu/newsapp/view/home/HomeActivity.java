package com.maohongyu.newsapp.view.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.maohongyu.newsapp.R;
import com.maohongyu.newsapp.presenter.home.IHomePresenter;
import com.maohongyu.newsapp.presenter.home.IHomePresenterComl;

import butterknife.ButterKnife;

/**
 * Created by maohongyu on 17/1/5.
 */
public class HomeActivity extends Activity {

    private long oldTime;

    private IHomePresenter iHomePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ButterKnife.bind(this);
        iHomePresenter = new IHomePresenterComl(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long currentTime = System.currentTimeMillis();
        if (KeyEvent.KEYCODE_BACK==keyCode) {
            if ((currentTime - oldTime) < 3000) {
                System.exit(0);
                return false;
            }
            oldTime = currentTime;
            Toast.makeText(this, "再点击一次退出", Toast.LENGTH_SHORT).show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
