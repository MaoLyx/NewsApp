package com.maohongyu.newsapp.presenter.selectcategory;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.maohongyu.newsapp.model.CategoryBean;
import com.maohongyu.newsapp.model.selectcategory.ISelectModle;
import com.maohongyu.newsapp.until.FileUtil;
import com.maohongyu.newsapp.view.selectcategory.ISelectCategory;
import com.maohongyu.newsapp.view.selectcategory.SelectgoryCategoryActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongyu on 2017/2/3 上午2:06.
 * 作用：
 */

public class ISelectComl implements ISelectCategoryPresenter, ISelectModle {

    private static final String TAG = "ISelectComl";

    private Context context;

    private ISelectCategory iSelectCategory;

    private final ArrayList<CategoryBean.Category> selected;

    public ISelectComl(Context context, ISelectCategory iSelectCategory) {
        this.context = context;
        this.iSelectCategory = iSelectCategory;
        SelectgoryCategoryActivity activity = (SelectgoryCategoryActivity) this.context;
        selected = activity.getIntent().getParcelableArrayListExtra("categorys");
    }

    @Override
    public List<Map<String, String>> getSelectedCategory() {
        return getMap(selected);
    }

    @Override
    public List<Map<String, String>> getUnSelectCategory() {
        Gson gson = new Gson();
        CategoryBean categoryBean = gson.fromJson(FileUtil.getStringFromFile((Activity) context, "AllCatagorys.txt"), CategoryBean.class);
        ArrayList<CategoryBean.Category> categories = (ArrayList<CategoryBean.Category>) categoryBean.getCategories();
        categories.removeAll(selected);
        return getMap(categories);
    }


    private List<Map<String, String>> getMap(ArrayList<CategoryBean.Category> categories) {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        if (categories != null) {
            for (int i = 0; i < categories.size(); i++) {
                HashMap<String, String> date = new HashMap<String, String>();
                Log.i(TAG, "getMap: " + categories.get(i).getName());
                date.put("name", categories.get(i).getName());
                date.put("type", categories.get(i).getType());
                data.add(date);
            }
        }
        return data;
    }

    @Override
    public void ShowCategory() {
        iSelectCategory.showProgress();
        iSelectCategory.refreshView(getSelectedCategory(), getUnSelectCategory());
    }

    @Override
    public void savaData(final List<Map<String, String>> selectedCategorys) {
        String json = toGson(selectedCategorys);
        SharedPreferences categorys = context.getSharedPreferences("categorys", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = categorys.edit();
        editor.putString("CTG", json);
        editor.commit();
    }

    private String toGson(List<Map<String, String>> selectedCategorys) {
        Gson gson = new Gson();
        List<CategoryBean.Category> selected = new ArrayList<>();
        for (int i = 0; i < selectedCategorys.size(); i++) {
            Map<String, String> stringStringMap = selectedCategorys.get(i);
            CategoryBean.Category category = new CategoryBean.Category(stringStringMap.get("name"), stringStringMap.get("type"));
            selected.add(category);
        }
        CategoryBean bean = new CategoryBean(selected);
        String json = gson.toJson(bean);
        return json;
    }
}
