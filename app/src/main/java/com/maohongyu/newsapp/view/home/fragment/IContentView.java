package com.maohongyu.newsapp.view.home.fragment;

import com.maohongyu.newsapp.model.ResponseBean;

import java.util.List;

/**
 * Created by hongyu on 2017/2/7 下午4:23.
 * 作用：
 */

public interface IContentView {

    void addNewsContent(List<ResponseBean.ResultBean.DataBean> data);

    boolean isLoad();

    void setLoad(boolean load);
}
