package com.maohongyu.newsapp.view.detial;

import android.content.Intent;
import android.webkit.WebView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.maohongyu.newsapp.R;
import com.maohongyu.newsapp.until.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;
import cn.sharesdk.tencent.qzone.QZone;

/**
 * Created by hongyu on 2017/2/3 下午6:08.
 * 作用：
 */

public class DetialActivity extends BaseActivity {

    @BindView(R.id.newImage_sv)
    SimpleDraweeView newImage_sv;

    @BindView(R.id.new_wv)
    WebView new_wv;

    private final String APPKEY ="1b1910b7a83bb";

    private String url;

    private String title;
    private String author;
    private String img;

    @Override
    protected void beforeInitView() {
        ShareSDK.initSDK(this,APPKEY);
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
            url = intent.getStringExtra("url");
            new_wv.loadUrl(url);
        }
        if (intent.hasExtra("img")) {
            img = intent.getStringExtra("img");
            newImage_sv.setImageURI(img);
        }
        if (intent.hasExtra("title")) {
            title = intent.getStringExtra("title");
        }
        if (intent.hasExtra("author")) {
            author = intent.getStringExtra("author");
        }
    }

    @OnClick(R.id.back_im)
    public void back()
    {
        finish();
    }

    @OnClick(R.id.share_img)
    public  void showShare() {
        OnekeyShare oks = new OnekeyShare();
        oks.setPlatform(QZone.NAME);
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        oks.setDialogMode();
        oks.disableSSOWhenAuthorize();
        if (author == null) {
            author = "";
        }
        oks.setTitle(author);
        if (title == null) {
            title = "";
        }
        oks.setText(title);
        if (url == null) {
            url = "";
        }
        oks.setUrl(url); //微信不绕过审核分享链接
        oks.setTitleUrl(url);
        oks.setImageUrl(img);
        oks.setSiteUrl(url);//QZone分享参数
        oks.show(this);
    }

}
