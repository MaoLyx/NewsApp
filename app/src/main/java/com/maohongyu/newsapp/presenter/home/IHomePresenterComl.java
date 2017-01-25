package com.maohongyu.newsapp.presenter.home;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.maohongyu.newsapp.model.CategoryBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by isle on 2017/1/11.
 */

public class IHomePresenterComl implements IHomePresenter {

    private static final String TAG = "IHomePresenterComl";

    private Context mContext;

    @Override
    public String getCategoryFromFeil() {
        Activity activity = (Activity) this.mContext;
        StringBuffer sb = new StringBuffer();
        try {
            InputStream is = activity.getAssets().open("category.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public CategoryBean getCategoryObject() {
        Gson gson = new Gson();
        CategoryBean categoryBean = gson.fromJson(getCategoryFromFeil(),CategoryBean.class);
        return categoryBean;
    }


    public IHomePresenterComl(Context mContext) {
        this.mContext = mContext;
    }
}
