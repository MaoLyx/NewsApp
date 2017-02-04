package com.maohongyu.newsapp.view.home;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.maohongyu.newsapp.until.base.BaseActivity;
import com.maohongyu.newsapp.view.detial.DetialActivity;
import com.maohongyu.newsapp.view.selectcategory.SelectgoryCategoryActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by maohongyu on 17/1/5.
 */
public class HomeActivity extends BaseActivity implements IHome {

    private static final String TAG = "HomeActivity";

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
    protected void beforeInitView() {

    }

    @Override
    protected void setView() {
        setContentView(R.layout.home);
        ButterKnife.bind(this);
    }

    @Override
    protected void afterInitView() {
        iHomePresenter = new IHomePresenterComl(this, this);
        iHomePresenter.getCategoryFromFile();
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
    public void addCategoryView(List<CategoryBean.Category> data) {
        this.categories = (ArrayList<CategoryBean.Category>) data;
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
                if (position>getCount()) {
                    return;
                }
                try{
                    if (newsViews.get(position).getParent() != null) {
                        container.removeView(newsViews.get(position));
                    }
                }catch (Exception e){

                }
            }
        });
    }

    @Override
    public void setNewsToView(List<ResponseBean.ResultBean.DataBean> newsData) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getName().equals(newsData.get(i).getCategory())) {
                initListview(newsData, i);
            }else if (null==newsData.get(i).getCategory()){
                initListview(newsData, i);
            }
            category_ll.getChildAt(i).setClickable(true);
        }
        progress_rv.setVisibility(View.INVISIBLE);
    }

    private void initListview(List<ResponseBean.ResultBean.DataBean> newsData, int i) {
        ListView news_lv = (ListView) newsViews.get(i);
        ContentAdapter adapter = (ContentAdapter) newsAdapters.get(i);
        adapter.reSetNewsData(newsData, categories.get(i));
        news_lv.setAdapter(adapter);
        news_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContentAdapter adapter = (ContentAdapter) parent.getAdapter();
                ResponseBean.ResultBean.DataBean item = adapter.getItem(position);
                Intent intent =new Intent(HomeActivity.this, DetialActivity.class);
                intent.putExtra("url",item.getUrl());
                intent.putExtra("img",item.getThumbnail_pic_s());
                HomeActivity.this.startActivity(intent);
            }
        });
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
        Log.i(TAG, "getNewsInfoAndChangeState: " + categories.get(position).getType());
    }

    private void clearState() {
        for (int i = 0; i < category_ll.getChildCount(); i++) {
            View child = category_ll.getChildAt(i);
            child.setBackgroundResource(android.R.color.transparent);
        }
    }

    @OnClick(R.id.addCategory_iv)
    public void toSelectActivity() {
        content_vp.setCurrentItem(0);
        Intent intent = new Intent(this, SelectgoryCategoryActivity.class);
        intent.putExtra("categorys", categories);
        startActivityForResult(intent, 1009);
//
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        if (requestCode == 1009) {
            if (resultCode == RESULT_OK ) {
                if (data != null) {
                    if (data.hasExtra("ctg")) {
                        category_ll.removeAllViews();
                        newsViews.clear();
                        newsAdapters.clear();
                        ArrayList<CategoryBean.Category> ctg = data.getParcelableArrayListExtra("ctg");
                        addCategoryView(ctg);
                        iHomePresenter.getInfoFromNet(ctg.get(0));
                    }
                }
            }
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
