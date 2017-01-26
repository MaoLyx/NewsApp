package com.maohongyu.newsapp.view.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maohongyu.newsapp.R;
import com.maohongyu.newsapp.adapter.ContentAdater;
import com.maohongyu.newsapp.model.CategoryBean;
import com.maohongyu.newsapp.presenter.home.IHomePresenter;
import com.maohongyu.newsapp.presenter.home.IHomePresenterComl;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maohongyu on 17/1/5.
 */
public class HomeActivity extends Activity implements IHome {

    private long oldTime;

    private IHomePresenter iHomePresenter;

    @BindView(R.id.category_ll) LinearLayout category_ll;

//    @BindView(R.id.content_rv) RecyclerView content_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ButterKnife.bind(this);
        iHomePresenter = new IHomePresenterComl(this,this);
        addCategoryView(iHomePresenter.getCategoryObject());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long currentTime = System.currentTimeMillis();
        if (KeyEvent.KEYCODE_BACK==keyCode) {
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



    @Override
    public void setContentDatas(ContentAdater contentAdater) {
        //TODO  RecycleView设置layoutmanager 等

//        content_rv.setAdapter(contentAdater);
    }

    @Override
    public void addCategoryView(CategoryBean data) {
        final ArrayList<CategoryBean.Category> categories = data.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            View view = View.inflate(this,R.layout.category_tv,null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);
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
                    clearState();
                    v.setBackgroundResource(R.color.grey);
                    iHomePresenter.getInfoFromNet(categories.get(position));

                }
            });
            category_ll.addView(view);
        }
    }

    private void clearState() {
        for (int i = 0; i < category_ll.getChildCount(); i++) {
            View child = category_ll.getChildAt(i);
            child.setBackgroundResource(android.R.color.transparent);
        }
    }

}
