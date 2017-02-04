package com.maohongyu.newsapp.presenter.home;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.maohongyu.newsapp.R;
import com.maohongyu.newsapp.model.CategoryBean;
import com.maohongyu.newsapp.model.IHttpService;
import com.maohongyu.newsapp.model.ResponseBean;
import com.maohongyu.newsapp.until.FileUtil;
import com.maohongyu.newsapp.until.http.RetrofitUntil;
import com.maohongyu.newsapp.view.home.IHome;

import java.net.UnknownHostException;

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

    private Call<ResponseBean> currentCall;

    @Override
    public void getCategoryFromFile() {
        Log.d(TAG, "getCategoryFromFile() called");
        SharedPreferences preferences = mContext.getSharedPreferences("categorys", Context.MODE_PRIVATE);
        String ctg = preferences.getString("CTG", null);
        if (ctg != null) {
            dealString(ctg);
        }else {
            FileUtil.getStringFromFile((Activity) mContext, "category.txt", new FileUtil.Callback() {
                @Override
                public void getStringFinish(String result) {
                    dealString(result);
                }
            });
        }

    }

    private void dealString(String result) {
        Gson gson = new Gson();
        final CategoryBean categoryBean = gson.fromJson(result, CategoryBean.class);
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                iHome.addCategoryView(categoryBean.getCategories());
                getInfoFromNet(null);
            }
        });
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
        Call<ResponseBean> news = null;
        if (category != null) {
            news = service.getNews(category.getType());
        } else {
            news = service.getNews("");
        }
        currentCall = news;
        news.enqueue(new Callback<ResponseBean>() {
            @Override
            public void onResponse(Call<ResponseBean> call, Response<ResponseBean> response) {
                ResponseBean responseBean = response.body();
                Log.i(TAG, "onResponse: " + responseBean.toString());
                if (responseBean != null) {
                    if (responseBean.getError_code() == 0) {
                        iHome.setNewsToView(responseBean.getResult().getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBean> call, Throwable t) {
//                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                if (t instanceof UnknownHostException) {
                    Toast.makeText(mContext, R.string.error_net, Toast.LENGTH_SHORT).show();
                    iHome.hideProgress();
                }
            }

        });
        return;
    }

    @Override
    public void cancelCurrentNetwork() {
        currentCall.cancel();
    }


    public IHomePresenterComl(Context mContext, IHome iHome) {
        this.mContext = mContext;
        this.iHome = iHome;
    }
}
