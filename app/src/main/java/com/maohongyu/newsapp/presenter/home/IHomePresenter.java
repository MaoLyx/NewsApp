package com.maohongyu.newsapp.presenter.home;

import com.maohongyu.newsapp.model.CategoryBean;

/**
 * Created by isle on 2017/1/10.
 */

public interface IHomePresenter {

    /**
     * 从文件中拿到类型数据
     * @return 拿到类型数据
     */
    String getCategoryFromFeil();

    /**
     * 从文件中拿到类型的对象
     * @return 从文件中拿到类型的对象
     */
    CategoryBean getCategoryObject();

    /**
     * 设置当前选择类型的对象
     * @param category
     */
    void setCurrentCategory(CategoryBean.Category category);

    /**
     * 获取网络信息
     * @param category
     * @return 网络信息
     */
    void getInfoFromNet(CategoryBean.Category category);

}
