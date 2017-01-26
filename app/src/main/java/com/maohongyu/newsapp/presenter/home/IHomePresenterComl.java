package com.maohongyu.newsapp.presenter.home;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.maohongyu.newsapp.R;
import com.maohongyu.newsapp.model.CategoryBean;
import com.maohongyu.newsapp.model.IHttpService;
import com.maohongyu.newsapp.model.ResponseBean;
import com.maohongyu.newsapp.until.RetrofitUntil;
import com.maohongyu.newsapp.view.home.IHome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by isle on 2017/1/11.
 */

public class IHomePresenterComl implements IHomePresenter {

    private static final String TAG = "IHomePresenterComl";

    private CategoryBean.Category category;

    private Context mContext;

    private IHome iHome;

    private ArrayList<CategoryBean.Category> categories;

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
        Log.i(TAG, "getCategoryFromFeil: "+sb.toString());
        return sb.toString();
    }

    @Override
    public CategoryBean getCategoryObject() {
        Gson gson =  new Gson();
        CategoryBean categoryBean =  gson.fromJson(getCategoryFromFeil(),CategoryBean.class);
        Log.i(TAG, "getCategoryObject: "+categoryBean.toString());
        return categoryBean;
    }

    @Override
    public void setCurrentCategory(CategoryBean.Category category) {
        this.category = category;
    }

    @Override
    public void getInfoFromNet(CategoryBean.Category category) {
        setCurrentCategory(category);
        Retrofit retrofit = RetrofitUntil.getRetrofit();
        IHttpService service = retrofit.create(IHttpService.class);
        Call<ResponseBean> news = service.getNews(category.getType());
        news.enqueue(new Callback<ResponseBean>() {
            @Override
            public void onResponse(Call<ResponseBean> call, Response<ResponseBean> response) {
                Log.i(TAG, "onResponse: "+response.body().toString());

            }

            @Override
            public void onFailure(Call<ResponseBean> call, Throwable t) {
                Toast.makeText(mContext, R.string.error_net, Toast.LENGTH_SHORT).show();
            }
        });
        return ;
    }


    public IHomePresenterComl(Context mContext,IHome iHome) {
        this.mContext = mContext;
        this.iHome    = iHome;
    }
}
