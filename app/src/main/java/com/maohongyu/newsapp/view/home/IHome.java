package com.maohongyu.newsapp.view.home;

import com.maohongyu.newsapp.adapter.ContentAdater;
import com.maohongyu.newsapp.model.CategoryBean;

/**
 * Created by hongyu on 2017/1/25 下午2:36.
 * 作用：
 */

public interface IHome {

    /**
     * 主要内容的Adapter
     * @param contentAdater
     */
    void setContentDatas(ContentAdater contentAdater);

    /**
     * 添加类型标签
     * @param data 数据
     */
    void addCategoryView(CategoryBean data);
}
