package com.maohongyu.newsapp.view.selectcategory;

import java.util.List;
import java.util.Map;

/**
 * Created by hongyu on 2017/1/31 下午10:19.
 * 作用：
 */

public interface ISelectCategory {

    /**
     * 显示进度条
     */
    void showProgress();

    /**
     * 隐藏进度条
     */
    void hideProgress();

    /**
     * 刷新界面
     */
    void refreshView(List<Map<String,String>> selectedData,List<Map<String,String>> unSelectedData);
}
