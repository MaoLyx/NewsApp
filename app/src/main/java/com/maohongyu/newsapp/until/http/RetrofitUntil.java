package com.maohongyu.newsapp.until.http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.maohongyu.newsapp.model.IHttpService.BASE_URL;

/**
 * Created by hongyu on 2017/1/25 下午11:48.
 * 作用：
 */

public class RetrofitUntil {

    private static Retrofit retrofit =new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
