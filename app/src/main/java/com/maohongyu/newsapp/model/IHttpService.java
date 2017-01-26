package com.maohongyu.newsapp.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hongyu on 2017/1/24 上午10:14.
 * 作用：
 */

public interface IHttpService {

    final String BASE_URL = "http://v.juhe.cn/toutiao/";

    @GET("index? key=c35a046db429251d1177da4abedde91a")
    Call<ResponseBean> getNews(@Query("type") String type);

}
