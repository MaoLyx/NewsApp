package com.maohongyu.newsapp.model.selectcategory;

import java.util.List;
import java.util.Map;

/**
 * Created by hongyu on 2017/2/3 上午1:57.
 * 作用：
 */

public interface ISelectModle {

    /**
     * 获得当前的类型
     * @return 当前类型
     */
    List<Map<String, String>> getSelectedCategory();

    /**
     * 获得没有选择的类型
     * @return 没有选择类型
     */
    List<Map<String, String>> getUnSelectCategory();
}
