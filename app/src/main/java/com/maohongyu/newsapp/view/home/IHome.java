package com.maohongyu.newsapp.view.home;

import com.maohongyu.newsapp.model.CategoryBean;
import com.maohongyu.newsapp.model.ResponseBean;

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
    void addCategoryView(List<CategoryBean.Category> data);

    /**
     * 将xi
     * @param newsData
     */
    void setNewsToView(List<ResponseBean.ResultBean.DataBean> newsData);

    /**
     * 隐藏进度
     */
    void hideProgress();


}
