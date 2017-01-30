package com.maohongyu.newsapp.view.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maohongyu.newsapp.R;
import com.maohongyu.newsapp.adapter.ContentAdapter;
import com.maohongyu.newsapp.model.CategoryBean;
import com.maohongyu.newsapp.model.ResponseBean;
import com.maohongyu.newsapp.presenter.home.IHomePresenter;
import com.maohongyu.newsapp.presenter.home.IHomePresenterComl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by maohongyu on 17/1/5.
 */
public class HomeActivity extends Activity implements IHome {

    private long oldTime;

    private IHomePresenter iHomePresenter;

    @BindView(R.id.category_ll)
    LinearLayout category_ll;

    @BindView(R.id.content_vp)
    ViewPager content_vp;

    @BindView(R.id.progress_rv)
    RelativeLayout progress_rv;

    private List<View> newsViews = new ArrayList<View>();

    private ArrayList<CategoryBean.Category> categories;

    private List<ContentAdapter> newsAdapters = new ArrayList<ContentAdapter>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ButterKnife.bind(this);
        initial();

    }

    private void initial() {
        iHomePresenter = new IHomePresenterComl(this, this);
        addCategoryView(iHomePresenter.getCategoryObject());
        iHomePresenter.getInfoFromNet(null);
        content_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                iHomePresenter.cancelCurrentNetwork();
                getNewsInfoAndChangeState(category_ll.getChildAt(position), position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void addCategoryView(CategoryBean data) {
        categories = data.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            View view = View.inflate(this, R.layout.category_tv, null);
            View news = View.inflate(this, R.layout.news_list, null);
            ContentAdapter adapter = new ContentAdapter(null, null, this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
            TextView textView = (TextView) view.findViewById(R.id.addCategory_tv);
            textView.setText(categories.get(i).getName());
            if (i == 0) {
                view.setBackgroundResource(R.color.grey);
            }
            final int position = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    content_vp.setCurrentItem(position);
                    getNewsInfoAndChangeState(v, position);

                }
            });
            category_ll.addView(view);
            newsViews.add(news);
            newsAdapters.add(adapter);
        }
        content_vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return newsViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(newsViews.get(position));
                return newsViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                if (newsViews.get(position).getParent() != null) {
                    container.removeView(newsViews.get(position));
                }
            }
        });
    }

    @Override
    public void setNewsToView(List<ResponseBean.ResultBean.DataBean> newsData) {
        for (int i = 0; i < categories.size(); i++) {
            if (newsData.get(0).getCategory().equals(categories.get(i).getName())) {
                ListView news_lv = (ListView) newsViews.get(i);
                ContentAdapter adapter = (ContentAdapter) newsAdapters.get(i);
                adapter.reSetNewsData(newsData, categories.get(i));
                news_lv.setAdapter(adapter);
                category_ll.getChildAt(i).setClickable(true);
            }
        }
        progress_rv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progress_rv.setVisibility(View.INVISIBLE);
    }

    private void getNewsInfoAndChangeState(View v, int position) {
        progress_rv.setVisibility(View.VISIBLE);
        v.setClickable(false);
        clearState();
        v.setBackgroundResource(R.color.grey);
        iHomePresenter.getInfoFromNet(categories.get(position));
    }

    private void clearState() {
        for (int i = 0; i < category_ll.getChildCount(); i++) {
            View child = category_ll.getChildAt(i);
            child.setBackgroundResource(android.R.color.transparent);
        }
    }

    

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long currentTime = System.currentTimeMillis();
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if ((currentTime - oldTime) < 3000) {
                System.exit(0);
                return false;
            }
            oldTime = currentTime;
            Toast.makeText(this, "再点击一次退出", Toast.LENGTH_SHORT).show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
