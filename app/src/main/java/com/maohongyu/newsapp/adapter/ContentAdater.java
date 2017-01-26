package com.maohongyu.newsapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.maohongyu.newsapp.model.ResponseBean;

import java.util.List;

/**
 * Created by hongyu on 2017/1/25 下午9:05.
 * 作用：带实现
 */

public class ContentAdater extends RecyclerView.Adapter<ContentAdater.ContentVH> {

    private List<ResponseBean.ResultBean.DataBean> datas;


    @Override
    public ContentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ContentVH holder, int position) {

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ContentVH extends RecyclerView.ViewHolder{

        public ContentVH(View itemView) {
            super(itemView);

        }
    }

}
