package com.maohongyu.newsapp.presenter.selectcategory;

import java.util.List;
import java.util.Map;

/**
 * Created by hongyu on 2017/2/3 上午1:53.
 * 作用：
 */

public interface ISelectCategoryPresenter {

    void ShowCategory();

    void savaData(List<Map<String,String>> selectedCategorys);

}
