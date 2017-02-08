package com.maohongyu.newsapp.view.home;

import com.maohongyu.newsapp.model.CategoryBean;

import java.util.List;

/**
 * Created by hongyu on 2017/1/25 下午2:36.
 * 作用：
 */

public interface IHome {

    /**
     * 添加类型标签
     * @param data 数据
     */
    void generateTab(List<CategoryBean.Category> data);


    void generateContentsFragment(List<CategoryBean.Category> data);

    void showProgress();

    /**
     * 隐藏进度
     */
    void hideProgress();


}
