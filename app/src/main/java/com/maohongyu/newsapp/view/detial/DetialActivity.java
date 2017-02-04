package com.maohongyu.newsapp.view.detial;

import android.content.Intent;
import android.webkit.WebView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.maohongyu.newsapp.R;
import com.maohongyu.newsapp.until.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongyu on 2017/2/3 下午6:08.
 * 作用：
 */

public class DetialActivity extends BaseActivity {

    @BindView(R.id.newImage_sv)
    SimpleDraweeView newImage_sv;

    @BindView(R.id.new_wv)
    WebView new_wv;

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void setView() {
        setContentView(R.layout.activity_detial);
        ButterKnife.bind(this);
    }

    @Override
    protected void afterInitView() {
        Intent intent = getIntent();
        if (intent.hasExtra("url")) {
            String url = intent.getStringExtra("url");
            new_wv.loadUrl(url);
        }
        if (intent.hasExtra("img")) {
            String img = intent.getStringExtra("img");
            newImage_sv.setImageURI(img);
        }
    }

    @OnClick(R.id.back_im)
    public void back()
    {
        finish();
    }

}
