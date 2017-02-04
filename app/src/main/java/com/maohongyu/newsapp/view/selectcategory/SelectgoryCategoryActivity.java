package com.maohongyu.newsapp.view.selectcategory;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.maohongyu.newsapp.R;
import com.maohongyu.newsapp.model.CategoryBean;
import com.maohongyu.newsapp.presenter.selectcategory.ISelectComl;
import com.maohongyu.newsapp.until.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by hongyu on 2017/1/31 上午11:32.
 * 作用：
 */

public class SelectgoryCategoryActivity extends BaseActivity implements ISelectCategory {

    private static final String TAG = "SelectCategoryActivity";

    @BindView(R.id.select_category_item_gv)
    GridView select_category_item_gv;

    @BindView(R.id.selected_category_item_gv)
    GridView selected_category_item_gv;

    @BindView(R.id.progress_rv)
    RelativeLayout progress_rv;

    private SimpleAdapter selectedAdapter,unSelectedAdapter;

    private List<Map<String,String>> selectedCategorys;

    private List<Map<String, String>> unSelectedCategorys;

    private ISelectComl iSelectComl;


    @Override
    protected void beforeInitView() {
        iSelectComl = new ISelectComl(this,this);
        selectedCategorys = new ArrayList<Map<String,String>>();
        unSelectedCategorys = new ArrayList<Map<String,String>>();
        selectedAdapter = new SimpleAdapter(this,selectedCategorys, R.layout.select_category_item,
                new String[]{"name"},new int[]{R.id.addCategory_tv});
        unSelectedAdapter = new SimpleAdapter(this,unSelectedCategorys,R.layout.select_category_item,
                new String[]{"name"},new int[]{R.id.addCategory_tv});
    }

    @Override
    protected void setView() {
        setContentView(R.layout.activity_add_category);
        ButterKnife.bind(this);
    }

    @Override
    protected void afterInitView() {
        select_category_item_gv.setAdapter(unSelectedAdapter);
        selected_category_item_gv.setAdapter(selectedAdapter);
        iSelectComl.ShowCategory();
    }

    @Override
    public void showProgress() {
        progress_rv.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress_rv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void refreshView(List<Map<String, String>> selectedData, List<Map<String, String>> unSelectedData) {
        this.selectedCategorys.addAll(selectedData);
        selected_category_item_gv.invalidateViews();
        this.unSelectedCategorys.addAll(unSelectedData);
        select_category_item_gv.invalidateViews();
        hideProgress();
    }

    @OnClick(R.id.back_im)
    public void onClickBack(){
        ArrayList<CategoryBean.Category> categories = (ArrayList<CategoryBean.Category>) getCategoryArriy(selectedCategorys);
        Intent intent = new Intent();
        intent.putExtra("ctg",categories);
        setResult(RESULT_OK,intent);
        finish();
    }

    private List<CategoryBean.Category> getCategoryArriy(List<Map<String, String>> categorys) {
        List<CategoryBean.Category> list = new ArrayList<CategoryBean.Category>();
        if (categorys != null) {
            for (int i = 0; i < categorys.size(); i++) {
                Map<String, String> stringStringMap = categorys.get(i);
                CategoryBean.Category category = new CategoryBean.Category(stringStringMap.get("name"),stringStringMap.get("type"));
                list.add(category);
            }
        }
        return list;
    }

    @OnItemClick(R.id.select_category_item_gv)
    public void onUnSelectItemClick(int position){
        if (selectedCategorys.size()<4) {
            Map<String, String> item = unSelectedCategorys.get(position);
            selectedCategorys.add(selectedCategorys.size(),item);
            unSelectedCategorys.remove(item);
            selected_category_item_gv.invalidateViews();
            select_category_item_gv.invalidateViews();
        }else {
            Toast.makeText(this, "最多只能订阅4个新闻！", Toast.LENGTH_SHORT).show();
        }
    }

    @OnItemClick(R.id.selected_category_item_gv)
    public void onSelectItemClick(int position){
        if (selectedCategorys.size()<2) {
            Toast.makeText(this, "至少订阅1个新闻", Toast.LENGTH_SHORT).show();
        }else {
            Map<String, String> item = selectedCategorys.get(position);
            unSelectedCategorys.add(unSelectedCategorys.size(),item);
            selectedCategorys.remove(item);
            selected_category_item_gv.invalidateViews();
            select_category_item_gv.invalidateViews();
        }
    }

    @Override
    protected void onDestroy() {
        iSelectComl.savaData(selectedCategorys);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            ArrayList<CategoryBean.Category> categories = (ArrayList<CategoryBean.Category>) getCategoryArriy(selectedCategorys);
            Intent intent = new Intent();
            intent.putExtra("ctg",categories);
            setResult(RESULT_OK,intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
