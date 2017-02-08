package com.maohongyu.newsapp.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.maohongyu.newsapp.R;
import com.maohongyu.newsapp.model.CategoryBean;
import com.maohongyu.newsapp.presenter.home.IHomePresenter;
import com.maohongyu.newsapp.presenter.home.IHomePresenterComl;
import com.maohongyu.newsapp.view.home.fragment.ContentsFragment;
import com.maohongyu.newsapp.view.selectcategory.SelectgoryCategoryActivity;
import com.maohongyu.viewpagerindicator.ViewpagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.maohongyu.newsapp.R.id.category_ll;


/**
 * Created by maohongyu on 17/1/5.
 */
public class HomeActivity extends FragmentActivity implements IHome {

    private static final String TAG = "HomeActivity";

    private final int POSITION_FIRST = 0;
    private final int HOMEACTIVITYCODE = 1009;

    private long oldTime;

    private IHomePresenter iHomePresenter;

    @BindView(category_ll)
    ViewpagerIndicator indicator;

    @BindView(R.id.content_vp)
    ViewPager content_vp;

    @BindView(R.id.progress_rv)
    RelativeLayout progress_rv;

    private FragmentPagerAdapter adapter;

    private List<ContentsFragment> fragments;

    private ArrayList<CategoryBean.Category> selectedCategory;

    private final String HASCATEGORY_KEY = "hasCategory";

    private boolean hasCategory;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ButterKnife.bind(this);
        fragments = new ArrayList<ContentsFragment>();
        Intent i = getIntent();
        if (i.hasExtra(HASCATEGORY_KEY)) {
            ArrayList<CategoryBean.Category> parcelable = i.getParcelableArrayListExtra(HASCATEGORY_KEY);
            selectedCategory = (ArrayList<CategoryBean.Category>) parcelable;
            hasCategory = true;
        }
        attachPresenter();
        setViewPagerAdapter();
    }

    protected void attachPresenter() {
        iHomePresenter = new IHomePresenterComl(this, this);
    }


    private void setViewPagerAdapter() {
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        content_vp.setAdapter(adapter);
        indicator.setListener(new ViewpagerIndicator.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                iHomePresenter.getInfoFromNet(selectedCategory.get(position), position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (!hasCategory) {
            iHomePresenter.getCategoryFromFile();
        }else {
            generateTab(selectedCategory);
            generateContentsFragment(selectedCategory);
        }
    }


    @Override
    public void generateTab(List<CategoryBean.Category> data) {
        if (data != null && data.size() > 0) {
            selectedCategory = (ArrayList<CategoryBean.Category>) data;
            createTab(data);
        } else if (hasCategory && selectedCategory != null) {
            createTab(selectedCategory);
        }
        indicator.setViewPeger(content_vp, 0);
    }

    private void createTab(List<CategoryBean.Category> data) {
        List<String> titles = getTitles(data);
        indicator.setTitleItem(titles);
    }

    @Override
    public void generateContentsFragment(List<CategoryBean.Category> data) {
        for (int i = 0; i < data.size(); i++) {
            ContentsFragment fragment = ContentsFragment.newInstance();
            iHomePresenter.setIContentView(fragment);
            fragments.add(fragment);
        }
        adapter.notifyDataSetChanged();
        Log.d(TAG, "generateContentsFragment() called with: data --------------generateContentsFragment ");
        iHomePresenter.getInfoFromNet(data.get(0), 0);
    }


    private List<String> getTitles(List<CategoryBean.Category> data) {
        List<String> list = new ArrayList<String>();
        for (CategoryBean.Category category : data) {
            Log.d(TAG, "getTitles() called with: data = [" + category + "]");
            list.add(category.getName());
        }
        return list;
    }


    @Override
    public void showProgress() {
        progress_rv.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress_rv.setVisibility(View.INVISIBLE);
    }


    @OnClick(R.id.addCategory_iv)
    public void toSelectActivity() {
        content_vp.setCurrentItem(POSITION_FIRST);
        Intent intent = new Intent(this, SelectgoryCategoryActivity.class);
        intent.putExtra("categorys", selectedCategory);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long currentTime = System.currentTimeMillis();
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if ((currentTime - oldTime) < 3000) {
                System.exit(POSITION_FIRST);
                return false;
            }
            oldTime = currentTime;
            Toast.makeText(this, "再点击一次退出", Toast.LENGTH_SHORT).show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
